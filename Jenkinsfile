pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        IMAGE_NAME = 'mediexplain-ai'
        KUBE_CONTEXT = 'jenkins-deployer'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh '''
                    set -eu
                    docker build --pull=false \
                      -t "$IMAGE_NAME:$BUILD_NUMBER" \
                      -t "$IMAGE_NAME:latest" \
                      .
                '''
            }
        }

        stage('Verify Image') {
            steps {
                sh '''
                    set -eu
                    docker image inspect "$IMAGE_NAME:$BUILD_NUMBER" >/dev/null
                    echo "Docker image verified."
                '''
            }
        }

        stage('Load Image into Minikube') {
            steps {
                sh '''
                    set -eu

                    archive=$(mktemp /tmp/mediexplain-image.XXXXXX.tar)
                    trap 'rm -f "$archive"' EXIT

                    docker save \
                      "$IMAGE_NAME:$BUILD_NUMBER" \
                      "$IMAGE_NAME:latest" \
                      -o "$archive"

                    docker exec -i mediexplain docker load < "$archive"

                    docker exec mediexplain \
                      docker image inspect "$IMAGE_NAME:latest" >/dev/null

                    echo "Image loaded into Minikube's Docker runtime."
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([
                    file(
                        credentialsId: 'mediexplain-kubeconfig',
                        variable: 'KUBECONFIG'
                    )
                ]) {
                    sh '''
                        set -eu

                        kubectl --context="$KUBE_CONTEXT" \
                          apply -f k8s/mediexplain.yaml

                        kubectl --context="$KUBE_CONTEXT" \
                          set image deployment/mediexplain-ai \
                          mediexplain-ai="$IMAGE_NAME:$BUILD_NUMBER"

                        kubectl --context="$KUBE_CONTEXT" \
                          rollout status deployment/mediexplain-ai \
                          --timeout=180s

                        kubectl --context="$KUBE_CONTEXT" \
                          get pods -l app=mediexplain-ai
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: MediExplain AI built and deployed to Kubernetes.'
        }
        failure {
            echo 'FAILED: Inspect the first failing stage in Console Output.'
        }
    }
}
