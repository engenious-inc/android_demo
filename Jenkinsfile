pipeline {
    agent none
    stages {
    stage('Run Test') {
        parallel {
            stage('Run ktlint and Unit tests') {
            agent {
                docker {
                    image 'javiersantos/android-ci:28.0.3'
                }
            }
            steps {
                        sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                        sh './gradlew clean ktlint'
                    }
            }
            stage('Unit tests') {
                    agent {
                        docker {
                            image 'javiersantos/android-ci:28.0.3'
                        }
                    }
                    steps {
                        sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                        sh './gradlew clean testDebugUnitTest'
                    }
             }
            stage('Espresso') {
                    agent {
                        docker {
                            image 'javiersantos/android-ci:28.0.3'
                        }
                    }
                    steps {
                        sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                        sh './gradlew clean forkDebugAndroidTest'
                    }
             }
        }
        }
    }
}
