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

        stage('Run New Jar in Background') {
            steps {
                bat '''
                    echo Starting Spring Boot App in background...
                    start "" java -jar target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar > spring.log 2>&1
                    timeout /T 5
                    echo ✅ App launched, Jenkins will now finish.
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and Background Deployment Successful!'
        }
        failure {
            echo '❌ Build or Deployment Failed!'
        }
    }
}
