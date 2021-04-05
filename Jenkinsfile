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
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR}:5555 &'
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR2}:5555 &'
                        sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR3}:5555 &'
                        sh 'pushd ./gradlew installDist'
                        sh "sift/runner/build/install/sift/bin/sift orchestrator run --token zfaXctcWy.S~GSI8KR0cX_aA --test-plan 'smoke' --status 'enabled'"
                    }
                    post {
                        always {
                            junit 'siftReport/tests/**/*.xml'
                            publishHTML(target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'siftReport/html',
                            reportFiles: 'index.html',
                            reportName: "HTML Report"
                           ])
                        }
                    }
                }
            }
        }
    }
}
