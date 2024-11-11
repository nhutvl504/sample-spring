pipeline {
    agent any

    environment {
      //  REGISTRY = "index.docker.io/v1"   // Replace with your registry URL (e.g., Docker Hub or private registry)
        IMAGE_NAME = "nhutlm1/backend" // Replace with your image name
        DOCKER_CREDENTIALS_ID = "docker-credentials" // Jenkins credential ID for Docker registry login
        TAG = "${env.TAG ?: 'latest'}"  // Default tag to 'latest' if not specified
    }

    stages {
      //  stage('Clone Repository') {
      //      steps {
      //          git url: 'https://github.com/nhutvl504/sample-spring.git', branch: 'main' // Replace with your Git repository URL and branch
      //      }
      //  }
       stage('Get Git Commit Hash') {
            steps {
                script {
                    // Get the short commit hash
                    GIT_COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                }
            }
        }
    
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:${GIT_COMMIT_HASH}")
                }
            }
        }

     
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKER_CREDENTIALS_ID}") { // Empty string defaults to Docker Hub
                        dockerImage.push() // Pushes the image with the build number tag
                        dockerImage.push('latest') // Also tag and push as "latest"
                    }
                }
            }
        }
         stage('Update docker compose') {
            steps {
                sh "sed -i 's/\\${TAG}/${GIT_COMMIT_HASH}/g' docker-compose.yml"
                archiveArtifacts artifacts: 'docker-compose.yml', allowEmptyArchive: false
            }
        }
    }

    post {
        always {
            cleanWs() // Clean workspace after build
        }
    }
}
