pipeline {
    agent any

    environment {
        REGISTRY = "docker.io"   // Replace with your registry URL (e.g., Docker Hub or private registry)
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
                    dockerImage = docker.build("${REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}") // Builds image with tag as build number
                }
            }
        }

         stage('Push to Docker Registry') {
            steps {
                  script {
                      docker.withRegistry("https://${REGISTRY}", "${DOCKER_CREDENTIALS_ID}") {
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
