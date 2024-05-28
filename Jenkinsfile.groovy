pipeline {
    agent any

    environment {
        GITHUB_CREDENTIALS_ID = 'github-creds'
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-creds'
        DOCKER_IMAGE = 'rohanm0702/jenkinslab'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: "${GITHUB_CREDENTIALS_ID}", url: 'https://github.com/Rohanm0702/Jenkins.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKERHUB_CREDENTIALS_ID}") {
                        docker.image("${DOCKER_IMAGE}").push('latest')
                    }
                }
            }
        }
    }
}
