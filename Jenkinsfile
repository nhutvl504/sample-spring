pipeline {
    agent any

    environment {
      //  REGISTRY = "index.docker.io/v1"   // Replace with your registry URL (e.g., Docker Hub or private registry)
        IMAGE_NAME = "nhutlm1/backend" // Replace with your image name
        DOCKER_CREDENTIALS_ID = "docker-credentials" // Jenkins credential ID for Docker registry login
    }

    stages {
      //  stage('Clone Repository') {
      //      steps {
      //          git url: 'https://github.com/nhutvl504/sample-spring.git', branch: 'main' // Replace with your Git repository URL and branch
      //      }
      //  }

    
        stage('Build Docker Image') {
            steps {
                script {
                      dockerImage = docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}")
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
    }

    post {
        always {
            cleanWs() // Clean workspace after build
        }
    }
}
