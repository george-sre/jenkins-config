pipeline {

    options { disableConcurrentBuilds() }

    environment {
        // TAG_NAME = "${env.BRANCH_NAME == 'master' ? 'stable' : 'test'}"
        TAG_NAME = 'latest'
        REGISTRY_CREDENTIALS_ID = 'docker-registry-id'
    }

    agent { label "do-not-use-master" }

    stages {

        stage('RAW CONFIG HANDLING') {
            steps {
                sh "ls -lrt && pwd"
                sh "mkdir -p ${JENKINS_HOME}/jenkins_config/"
                sh "cp -rf * ${JENKINS_HOME}/jenkins_config/"
            }
        }

        stage('CONSUL TEMPLATE') {
            steps { sh 'consul-template -vault-addr "$VAULT_ADDR" -config "jenkins_config.hcl" -once -vault-retry-attempts=1 -vault-renew-token=false' }
        }

        stage('RUN GROOVY on master') {
            steps {
                load("/var/jenkins_home/jenkins_config/src/kubernetes.groovy")
            }
        }

        stage('Build dockerfiles') {
            steps {
                script {
                    docker.withRegistry('', "${REGISTRY_CREDENTIALS_ID}") {
                        docker.build("georgesre/jnlp-node-team1:${TAG_NAME}", "-f dockerfiles/node-team1/Dockerfile dockerfiles/node-team1").push()
                    }
                    docker.withRegistry('', "${REGISTRY_CREDENTIALS_ID}") {
                        docker.build("georgesre/jnlp-node-team2:${TAG_NAME}", "-f dockerfiles/node-team2/Dockerfile dockerfiles/node-team2").push()
                    }
                    docker.withRegistry('', "${REGISTRY_CREDENTIALS_ID}") {
                        docker.build("georgesre/jnlp-node-team3:${TAG_NAME}", "-f dockerfiles/node-team3/Dockerfile dockerfiles/node-team3").push()
                    }
                }
            }
        }

        // stage('Run Parallel Tests') {
        //     parallel {
        //         stage('Test On node-team1') {
        //             agent { label "node-team1" }
        //             steps { sh "echo I am good on node-team1" }
        //         }
        //         stage('Test On node-team2') {
        //             agent { label "node-team2" }
        //             steps { sh "echo I am good on node-team2" }
        //         }
        //         stage('Test On node-team3') {
        //             agent { label "node-team3" }
        //             steps { sh "echo I am good on node-team3" }
        //         }
        //     }
        // }
    }
}
