pipeline {
    agent any  // Use any available Jenkins agent

    tools {
        maven 'M3'  // Name of your Maven installation in Jenkins (we’ll set this later)
    }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/KamalAhmad07/Jenkins-Demo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
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
