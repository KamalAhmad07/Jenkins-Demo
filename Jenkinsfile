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

        stage('Run App') {
            steps {
                bat """
                           echo Running Spring Boot app for '${params.ENV}' environment on port ${params.PORT}
                           java -DSPRING_PROFILES_ACTIVE=${params.ENV} -DSERVER_PORT=${params.PORT} -DMYSQL_PASSWORD=${env.MYSQL_PASSWORD} -jar target\\${env.JAR_NAME}
                    """
            }
        }

        stage('Deploy') {
            steps {
                echo "🚀 Deploying to ${params.ENV} environment... (simulated)"
                // Add real deployment steps here in the future
            }
        }
    }

    post {
        success {
            echo "✅ Build & Deployment successful for environment: ${params.ENV}"
        }
        failure {
            echo "❌ Build or Deployment failed"
        }
        always {
            echo "📦 Jenkins job completed"
        }
    }
}
