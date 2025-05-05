#!/bin/groovy

pipeline {
    agent any
    options { timestamps () }
    tools { 
        maven 'Maven 3.9.9' 
    }
    environment {
        DOCKER_CREDS = credentials('docker-cred')
    }
    stages {
        stage('Build') {
            steps {
                sh 'java --version'
                echo '===================================================================='
                sh 'mvn clean install jib:build \
                -Djib.from.auth.username=$DOCKER_CREDS_USR \
                -Djib.from.auth.password=$DOCKER_CREDS_PSW \
                -Djib.to.auth.username=$DOCKER_CREDS_USR \
                -Djib.to.auth.password=$DOCKER_CREDS_PSW'
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
                echo 'Testing..'
                sh 'timeout 10 java -Dserver.port=8888 -jar /var/jenkins_home/.m2/repository/com/cs/base-microservice/0.0.1-SNAPSHOT/base-microservice-0.0.1-SNAPSHOT.jar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}