pipeline {
    agent any

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

        stage('Kill Previous App') {
            steps {
                script {
                    // Kill any app running on port 8081
                    bat 'for /f "tokens=5" %%a in (\'netstat -aon ^| findstr :8081\') do taskkill /F /PID %%a || exit 0'
                }
            }
        }

        stage('Run New Jar') {
            steps {
                bat 'start "" java -jar target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar'
            }
        }
    }

    post {
        success {
            echo '✅ Build and Deployment Successful!'
        }
        failure {
            echo '❌ Build or Deployment Failed!'
        }
    }
}
