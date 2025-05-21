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
                    echo Killing app on 8081 if running...
                    FOR /F "tokens=5" %%P IN ('netstat -aon ^| findstr :8081') DO (
                        echo Killing PID %%P
                        taskkill /F /PID %%P
                    )
                    exit /B 0
                '''
            }
        }

        stage('Run in Foreground via spring-boot:run') {
            steps {
                bat '''
                    echo Running app using spring-boot:run...
                    mvn spring-boot:run
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and App Started via spring-boot:run!'
        }
        failure {
            echo '❌ Build or App Start Failed!'
        }
    }
}
