pipeline {
    agent any

    tools{
    maven 'tomcat-jenkins'
    }

    stages {
        stage("git") {
            steps {
                git credentialsId: 'id', url: 'git@github.com:mtifany/AndersenDbApp.git'
            }
        }
        stage("build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("deploy") {
            steps{
              deploy adapters: [tomcat9(credentialsId: 'cd761dad-fee9-415e-981f-7d4283b9ded9', path: '', url: 'http://10.211.55.3:8080/')], contextPath: 'TEST_WAR', war: '**/*.war'
            }
        }
    }
}