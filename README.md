🚀 Jenkins CI/CD Demo with GitHub Webhook Integration
This project demonstrates a complete Jenkins setup to build and run a Spring Boot application via:

✅ Freestyle Job

✅ Pipeline Job (with Jenkinsfile)

✅ GitHub Webhook Integration (via ngrok)

🔧 Prerequisites
Java & Maven installed

Jenkins installed (locally)

GitHub account

ngrok (for webhook testing)

Git plugin, GitHub plugin installed in Jenkins


📂 Project Structure

Jenkins-Demo/
├── src/
├── target/
├── pom.xml
└── Jenkinsfile (for Pipeline setup)


✅ Option 1: Jenkins Freestyle Job Setup
🔁 Step-by-Step

1. Open Jenkins → New Item → Freestyle project → Enter job name → OK

2. Go to ⬇️ Source Code Management:
     Select Git
     Repository URL: https://github.com/KamalAhmad07/Jenkins-Demo.git

3. ⬇️ Build Triggers:
     ✅ Check “GitHub hook trigger for GITScm polling”

4. ⬇️ Build Steps:
    Add build step → Execute Windows batch command: mvn clean install
    Optional: Run app after build: java -jar target\Jenkins-Demo-0.0.1-SNAPSHOT.jar

5. Save the job → Click “Build Now” to test


✅ Option 2: Jenkins Pipeline Job Setup (Recommended)
🛠 Step-by-Step
1. Jenkins → New Item → Pipeline → Enter job name → OK

2. ⬇️ Pipeline Definition:
      Definition: Pipeline script from SCM
      SCM: Git 
      Repo URL: https://github.com/your-username/Jenkins-Demo.git
      Script Path: Jenkinsfile

3. ⬇️ Build Triggers:
      ✅ Check “GitHub hook trigger for GITScm polling”

4. Save → Click “Build Now” to test

📦 Sample Jenkinsfile

Place this Jenkinsfile in root Dir:

code : 

pipeline {

agent any

    tools {
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/Jenkins-Demo.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Run App') {
            steps {
                bat 'mvn spring-boot:run'
            }
        }
    }

    post {
        success {
            echo '✅ Build & Run successful!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}

To BuildNow Automated we need to setUp ngrok if Jenkins is running  locally

🌐 GitHub Webhook Setup (via ngrok)

   Start ngrok:

     In the ngrok Dir open cmd and type : ngrok.exe http 8080(jenkins port)


Copy public URL (e.g., https://abcd.ngrok-free.app)

Go to GitHub repo → Settings → Webhooks → Add Webhook

Payload URL: https://abcd.ngrok-free.app/github-webhook/

Content type: application/json

✅ Trigger: Just the push event

Save

Back in Jenkins:

Make sure “GitHub hook trigger for GITScm polling” is enabled

Your job will now trigger automatically on git push!


👨‍💻 Author
Kamal Ahmad
🔗 GitHub: KamalAhmad07

