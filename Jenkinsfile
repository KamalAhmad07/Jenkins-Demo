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

        // 2️⃣ Show the .env file
        stage('📂 Show .env File') {
            steps {
                bat 'type .env'
            }
        }

        // 3️⃣ Build the project using Maven
        stage('🏗️ Build') {
            steps {
                  bat 'mvn clean install -DskipTests'
            }
        }

        // 4️⃣ Run JUnit Tests
        stage('🧪 Run Tests') {
            steps {
                bat 'mvn clean test'
            }
        }

        // 5️⃣ Rename the jar file based on environment
        stage('🗃️ Rename Jar') {
            steps {
                bat "copy target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar target\\${env.JAR_NAME}"
            }
        }

        // 6️⃣ Archive the built JAR as an artifact
        stage('📦 Archive Artifact') {
            steps {
                archiveArtifacts artifacts: "target/${env.JAR_NAME}", fingerprint: true
            }
        }

        // 7️⃣ Kill any process running on the same port
        stage('❌ Docker Down the previous running Container') {
            steps {
                bat """
                    echo Stopping previous Docker containers...
                    docker-compose --env-file .env down || exit /B 0
                """
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

        // 🔟 Run the App via Docker-Compose
        stage('🚀 Run App via Docker-Compose') {
            steps {
                bat """
                    echo 🔧 Starting docker-compose with .env file...
                    docker-compose --env-file .env down || exit /B 0
                    docker-compose --env-file .env up -d --build
                """
            }
        }

        // 🕒 Wait for MySQL readiness
        stage('🕒 Wait for MySQL readiness') {
            steps {
                bat """
                    echo Waiting for MySQL to be ready...
                    docker-compose exec mysql sh -c "until mysql -u root -p%MYSQL_PASSWORD% -e \\"SHOW DATABASES\\"; do echo Waiting...; sleep 5; done"
                """
            }
        }

        // 🛰️ Final confirmation
        stage('🛰️ Deploy') {
            steps {
                echo "✅ Deployed '${env.JAR_NAME}' via Docker Compose on port ${params.PORT}"
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
