pipeline {

agent any

parameters {
    choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: 'Select the deployment environment')
    string(name: 'PORT', defaultValue: '8082', description: 'Application port to run the service')
}

environment {
    JAR_NAME = "Jenkins-Demo-${params.ENV}.jar"
    MYSQL_PASSWORD = credentials('MYSQL_PASSWORD')
}

tools {
    maven 'M3'
}

stages {
    stage('🧾 Checkout') {
        steps {
            git 'https://github.com/KamalAhmad07/Jenkins-Demo.git'
        }
    }

    stage('🏗️ Build') {
        steps {
            bat 'mvn clean install'
        }
    }

    stage('🧪 Run Tests') {
        steps {
            bat 'mvn test'
        }
    }

    stage('🗃️ Rename Jar') {
        steps {
            bat "copy target\\Jenkins-Demo-0.0.1-SNAPSHOT.jar target\\${env.JAR_NAME}"
        }
    }

    stage('📦 Archive Artifact') {
        steps {
            archiveArtifacts artifacts: "target/${env.JAR_NAME}", fingerprint: true
        }
    }

    stage('❌ Kill Previous App on Port') {
        steps {
            bat """
                echo Checking for any process using port ${params.PORT}...
                for /f "tokens=5" %%a in ('netstat -aon ^| findstr :${params.PORT}') do (
                    echo Killing PID %%a
                    taskkill /F /PID %%a || exit /B 0
                )
                exit /B 0
            """
        }
    }

    stage('🚀 Run App on Fixed Port') {
        steps {
            bat """
                echo Starting Spring Boot app on port ${params.PORT}...
                del app.log >nul 2>&1

                        powershell -Command "Start-Process java -ArgumentList '-DSPRING_PROFILES_ACTIVE=${params.ENV}','-Dserver.port=${params.PORT}','-DMYSQL_PASSWORD=${env.MYSQL_PASSWORD}','-jar','target\\\\${env.JAR_NAME}' -RedirectStandardOutput app.log -NoNewWindow"

                        cmd /c timeout /T 10 >nul

                        echo Verifying if port ${params.PORT} is now in use...
                        netstat -aon | findstr :${params.PORT} || exit /B 0
                    """
                }
    }

    stage('🛰️ Deploy') {
        steps {
            echo "✅ Deploying '${env.JAR_NAME}' to environment: ${params.ENV} on port: ${params.PORT}"
        }
    }
}

post {
    success {
        echo "✅ Build & Deployment successful on port ${params.PORT}"
        mail to: 'kamalahmaddhaka2002@gmail.com',
             subject: "✅ SUCCESS: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
             body: """Hey Kamal! 🎉

✅ Your app was built and deployed successfully on port ${params.PORT} in the '${params.ENV}' environment.

🔗 Build: ${env.BUILD_URL}
📦 Job: ${env.JOB_NAME}
🔢 Build #: ${env.BUILD_NUMBER}

Jenkins Pipeline Bot 🤖
"""
}

  failure {
      echo "❌ Build or Deployment failed"
      mail to: 'kamalahmaddhaka2002@gmail.com',
           subject: "❌ FAILED: Jenkins Build #${env.BUILD_NUMBER} - ${params.ENV}",
           body: """Hey Kamal,

❌ Something broke in your build or deployment.

🌐 Job: ${env.JOB_NAME}
🔗 Logs: ${env.BUILD_URL}

Please fix it fast or just restart Jenkins 😅

Jenkins Pipeline Bot 🤖
"""
}

  always {
      echo "📦 Jenkins job completed"
  }

}
}