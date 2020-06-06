pipeline {
    agent none
    stages {
    parallel {
        stage('Run ktlint and Unit tests') {
            agent {
                docker {
                    image 'javiersantos/android-ci:28.0.3'
                }
            }
            steps {
                        sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                        sh './gradlew ktlint'
                    }
            }
        stage('Unit tests')
                    agent {
                        docker {
                            image 'eng/android-ci:28.0.3'
                        }
                    }
                                steps {
                                            sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                                            sh './gradlew clean testDebugUnitTest'
                                        }
        }
    }
    post {
        always {
            junit 'app/build/reports/fork/debugAndroidTest/tests/**/*.xml'
            publishHTML(target: [
            allowMissing: false,
            alwaysLinkToLastBuild: false,
            keepAll: true,
            reportDir: 'app/build/reports/fork/debugAndroidTest/html',
            reportFiles: 'index.html',
            reportName: "HTML Report"
           ])
        }
    }
}
