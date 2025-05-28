pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Select the deployment environment')
        string(name: 'PORT', defaultValue: '8082', description: 'Application port to run the service')
    }

    environment {
        JAR_NAME = "Jenkins-Demo-${params.ENV}.jar"
        IMAGE_NAME = "kamalahmad/jenkins-demo"
        MYSQL_PASSWORD = credentials('MYSQL_PASSWORD')
    }

    tools {
        maven 'M3'
    }

    stages {

        // 1️⃣ Checkout Code from GitHub
        stage('🧾 Checkout') {
            steps {
                git 'https://github.com/KamalAhmad07/Jenkins-Demo.git'
            }
        }

        // 2️⃣ Build the project using Maven
        stage('🏗️ Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        // 3️⃣ Run JUnit Tests
        stage('🧪 Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        // 4️⃣ Rename the jar file based on environment
        stage('🗃️ Rename Jar') {
            steps {
                bat "copy target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar target\\${env.JAR_NAME}"
            }
        }

        // 5️⃣ Archive the built JAR as an artifact
        stage('📦 Archive Artifact') {
            steps {
                archiveArtifacts artifacts: "target/${env.JAR_NAME}", fingerprint: true
            }
        }

        // 6️⃣ Kill any process running on the same port
        stage('❌ Kill Previous App on Port') {
            steps {
                bat """
                    echo Checking for any process using port ${params.PORT}...
                    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :${params.PORT}') do (
                        echo Killing PID %%a
                        taskkill /F /PID %%a || exit /B 0
                    )
                    exit /B 0
                """
            }
        }

        // 7️⃣ Clean up old unused Docker images
       stage('🧹 Docker Image Prune') {
       steps {
         script {
          try {
            bat 'docker image prune -f'
           } catch (Exception e) {
            echo "⚠️ Skipping image prune due to error: ${e.message}"
            }
          }
         }
       }

        // 8️⃣ Build Docker Image & Tag with Build Number
        stage('🐳 Docker Build & Tag') {
            steps {
                bat "docker build -t ${env.IMAGE_NAME}:${env.BUILD_NUMBER} ."
            }
        }

        // 9️⃣ Login to DockerHub and Push the image
        stage('🔐 Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo Logging into DockerHub...
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker tag ${env.IMAGE_NAME}:${env.BUILD_NUMBER} ${env.IMAGE_NAME}:latest
                        docker push ${env.IMAGE_NAME}:${env.BUILD_NUMBER}
                        docker push ${env.IMAGE_NAME}:latest
                    """
                }
            }
        }

        // 🔟 Run the App in Container (instead of java -jar)
        stage('🚀 Run App via Docker-Compose') {
            steps {
               bat """
                     echo 🔧 Running docker-compose to start services...
                     docker-compose up --build -d
                   """
            }
        }

        // 🔁 Final confirmation
        stage('🛰️ Deploy') {
            steps {
                echo "✅ Deployed '${env.JAR_NAME}' via Docker on port ${params.PORT}"
            }
        }
    }

    post {
        success {
            echo "✅ Build & Deployment successful on port ${params.PORT}"
            mail to: 'kamalahmaddhaka2002@gmail.com',
                 cc: 'jointokamal9@gmail.com',
                 subject: "✅ SUCCESS: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
                 body: """Hey Kamal! 🎉

✅ Your app was built, Docker image was pushed, and deployed successfully via container on port ${params.PORT}.

🖼️ Docker Image: ${env.IMAGE_NAME}:${env.BUILD_NUMBER}
🔗 Build: ${env.BUILD_URL}
📦 Job: ${env.JOB_NAME}
🔢 Build #: ${env.BUILD_NUMBER}

Jenkins Pipeline Bot 🤖
"""
        }

        failure {
            echo "❌ Build or Deployment failed"
            mail to: 'kamalahmaddhaka2002@gmail.com',
                 subject: "❌ FAILED: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
                 body: """Hey Kamal,

❌ Something broke in your build or Docker process.

🌐 Job: ${env.JOB_NAME}
🔗 Logs: ${env.BUILD_URL}

Jenkins Pipeline Bot 🤖
"""
        }

        always {
            echo "📦 Jenkins job completed"
        }
    }
}
