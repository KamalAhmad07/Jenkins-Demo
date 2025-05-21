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
                bat '''
                    echo Checking if anything runs on 8081...
                    netstat -aon | findstr :8081

                    FOR /F "tokens=5" %%P IN ('netstat -aon ^| findstr :8081') DO (
                        echo Killing PID %%P
                        taskkill /F /PID %%P
                    )
                    exit /B 0
                '''
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
