pipeline {
    agent any

    tools {
        maven 'M3'  // This must match the Maven name you configured in Jenkins tools
    }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/KamalAhmad07/Jenkins-Demo.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }
    }

    post {
        success {
            echo 'Build and Test passed!'
        }
        failure {
            echo 'Build or Test failed.'
        }
    }
}
