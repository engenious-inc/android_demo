pipeline {
    agent none
    stages {
        stage('Run Test') {
            parallel {
                stage('Run ktlint') {
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
                stage('Run unit tests') {
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
                stage('Run UI Espresso tests') {
                    agent {
                        docker {
                            image 'javiersantos/android-ci:28.0.3'
                        }
                    }
                    steps {
                        sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR}:5555'
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR2}:5555'
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR3}:5555'
                        sh './gradlew clean forkDebugAndroidTest'
                    }
                }
            }
        }
    }
}
