pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Select the deployment environment')
        string(name: 'PORT', defaultValue: '8081', description: 'Application port')
    }

    environment {
        JAR_NAME = "Jenkins-Demo-${params.ENV}.jar"
        APP_PORT = "${params.PORT}"
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

        stage('Kill Previous App') {
            steps {
                bat """
                    echo Checking for any process on port ${params.PORT}...
                    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :${params.PORT}') do (
                        echo 🔪 Killing PID %%a
                        taskkill /F /PID %%a || exit /B 0
                    )
                    exit /B 0
                """
            }
        }

        stage('Run App in Background') {
            steps {
                bat """
                    echo 🚀 Starting Spring Boot app in background on port ${params.PORT}...
                    powershell -Command "Start-Process java -ArgumentList '-DSPRING_PROFILES_ACTIVE=${params.ENV}', '-DSERVER_PORT=${params.PORT}', '-DMYSQL_PASSWORD=${env.MYSQL_PASSWORD}', '-jar', 'target\\\\${env.JAR_NAME}' -NoNewWindow -WindowStyle Hidden"
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

✅ The Jenkins build and deployment for environment '${params.ENV}' completed successfully.

🌐 View Build: ${env.BUILD_URL}
🛠️ Job: ${env.JOB_NAME}
🔢 Build #: ${env.BUILD_NUMBER}

- Jenkins Pipeline Bot 🤖
"""
        }

        failure {
            echo "❌ Build or Deployment failed"
            mail to: 'kamalahmaddhaka2002@gmail.com',
                 subject: "❌ FAILED: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
                 body: """Hey Kamal,

❌ The Jenkins build for environment '${params.ENV}' failed.

📎 Check Console Logs: ${env.BUILD_URL}

Fix it fast — or blame the intern 😅

- Jenkins Pipeline Bot 🤖
"""
        }

        always {
            echo "📦 Jenkins job completed"
        }
    }
}
