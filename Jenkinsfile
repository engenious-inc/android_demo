pipeline {
    agent {
        docker {
            image 'javiersantos/android-ci:28.0.3'
        }
    }
    stages {
        stage('ktlint') {
            steps {
                sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                sh './gradlew clean ktlint'
            }
        }
        stage('build') {
            steps {
                sh './gradlew clean assembleDebug assembleAndroidTest'
            }
        }
        stage('Run UI tests') {
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                 sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR}:5555'
                 sh '$ANDROID_HOME/platform-tools/adb uninstall com.github.tarcv.orderme.app || true'
                 sh '$ANDROID_HOME/platform-tools/adb uninstall com.github.tarcv.orderme.app.test || true'
                 sh './gradlew --stop'
                 sh './gradlew clean forkDebugAndroidTest'}
                }
            }
    }
    post {
        always {
            archiveArtifacts artifacts: 'fork-report.*', fingerprint: true
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

