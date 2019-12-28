pipeline {

agent any
  tools {
        maven 'Maven-3.6'
    }

  environment {
    registry = "smartlizzard/smartbank"
    registryCredential = 'DockerHub'
    dockerImage = ''
  }
  
  stages {
  stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
    /*
    stage('Cloning Git') {
      steps {
        git url:'https://github.com/smartlizzard/SmartBank.git'
      }
    }
    */
    stage('Maven Build') {
      steps{
        script {
          sh 'mvn -B -DskipTests clean install'
        }
      }
    }
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":latest"
        }
      }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:latest"
      }
    }
  }
}
