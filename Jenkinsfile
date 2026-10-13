pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Building MediExplain AI and running Maven tests'
                sh '''
                    docker build --pull=false \
                      -t mediexplain-ai:${BUILD_NUMBER} \
                      -t mediexplain-ai:latest \
                      .
                '''
            }
        }

        stage('Verify Docker Image') {
            steps {
                sh 'docker image inspect mediexplain-ai:${BUILD_NUMBER} >/dev/null'
                echo 'Docker image built successfully'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: MediExplain AI passed the build pipeline.'
        }
        failure {
            echo 'FAILED: Check the stage logs for the error.'
        }
    }
}
