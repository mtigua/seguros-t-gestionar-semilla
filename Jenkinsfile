#!/usr/bin/env groovy

pipeline{ 
    agent any    
    environment {
      repository = 'seguros-ti-gestionar-semilla'
      k8sNamespace = 'backend'
    }
    stages {
        stage('Preparando espacio') {
            steps {
              sh "cp ${env.scriptsDir}/* $WORKSPACE"             
            }
        }
        stage('Clonando') {
            steps {
                git branch: "${env.BRANCH_NAME}",
                credentialsId: "${env.repositoryCredential}",
                url: "https://repo.tools.rfsc.cl/scm/rub/${repository}.git"
            }
        } 
        stage('Pruebas unitarias') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Entrega') {
            steps {
              script{                
                def imageTag = sh returnStdout: true, script: "./getImageTag.sh ${env.BRANCH_NAME}"
                if (!imageTag.trim().isEmpty()){
                	def truststoreKafka = sh returnStdout: true, script: "./getTruststoreKafkaJks.sh ${env.BRANCH_NAME}"
                    sh("./gradlew buildDocker -PdockerTag=" + imageTag.trim() + " -PkafkaTruststoreJks=" + truststoreKafka.trim())    
                	sh("docker rmi ${env.registry}/$repository:" + imageTag)
                }
              }              
            }
        }
        stage('Despliegue') {
            steps {
              script{                
                sh returnStdout: true, script: "./applyDeployment.sh ${env.helmClient} ${repository} ${k8sNamespace} ${env.BRANCH_NAME}"
              }              
            }
        }
    }
}
