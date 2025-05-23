pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Select the deployment environment')
        string(name: 'PORT', defaultValue: '0', description: 'Set to 0 for dynamic port or use a fixed one')
    }

    environment {
        JAR_NAME = "Jenkins-Demo-${params.ENV}.jar"
        MYSQL_PASSWORD = credentials('MYSQL_PASSWORD')
    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/KamalAhmad07/Jenkins-Demo.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Rename Jar') {
            steps {
                bat "copy target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar target\\${env.JAR_NAME}"
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: "target/${env.JAR_NAME}", fingerprint: true
            }
        }

    stage('Run App in Background with Dynamic Port') {
        steps {
            bat """
                echo 🚀 Starting Spring Boot app on dynamic port...
                del app.log >nul 2>&1
                powershell -Command "Start-Process java -ArgumentList '-DSPRING_PROFILES_ACTIVE=${params.ENV}','-Dserver.port=0','-DMYSQL_PASSWORD=${env.MYSQL_PASSWORD}','-jar','target\\\\${env.JAR_NAME}' -RedirectStandardOutput app.log -NoNewWindow"

                timeout /T 10 >nul
                echo 🔍 Reading dynamic port from app.log...
                powershell -Command "\$port = Select-String 'Tomcat started on port\\(s\\): (\\d+)' -Path app.log | ForEach-Object { \$_.Matches[0].Groups[1].Value }; echo 🌐 Application started on dynamic port: \$port"
            """
        }
    }

        stage('Deploy') {
            steps {
                echo "📦 Deploying to ${params.ENV} environment... (simulated)"
            }
        }
    }

    post {
        success {
            echo "✅ Build & Deployment successful for environment: ${params.ENV}"
            mail to: 'kamalahmaddhaka2002@gmail.com',
                 subject: "✅ SUCCESS: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
                 body: """Great news Kamal! 🎉

✅ The Jenkins build and deployment for environment '${params.ENV}' completed successfully using dynamic port!

🌐 View Build: ${env.BUILD_URL}
🛠️ Job: ${env.JOB_NAME}
🔢 Build #: ${env.BUILD_NUMBER}

- Jenkins Pipeline Bot 🤖
"""
        }

        failure {
            echo "Build or Deployment failed"
            mail to: 'kamalahmaddhaka2002@gmail.com',
                 subject: "FAILED: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
                 body: """Hey Kamal,

 The Jenkins build for environment '${params.ENV}' failed.

Check Console Logs: ${env.BUILD_URL}

Fix it fast — or blame the intern

- Jenkins Pipeline Bot
"""
        }

        always {
            echo " Jenkins job completed"
        }
    }
}
