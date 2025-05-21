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
                    FOR /F "tokens=5" %%P IN ('netstat -aon ^| findstr :8081') DO (
                        echo Killing PID %%P
                        taskkill /F /PID %%P
                    )
                    exit /B 0
                '''
            }
        }

        stage('Run Spring Boot in background') {
            steps {
                bat '''
                            echo Launching Spring Boot app in background using PowerShell...
                            powershell -Command "Start-Process java -ArgumentList '-jar target/Jenkins-Demo-0.0.1-SNAPSHOT.jar' -WindowStyle Hidden"
                            ping 127.0.0.1 -n 6 > nul
                        '''
                    }
            }
        }
    }

    post {
        success {
            echo '✅ Build and App Launched Successfully!'
        }
        failure {
            echo '❌ Build or App Launch Failed!'
        }
    }
}
