pipeline {
    agent {
        docker {
            image 'javiersantos/android-ci:28.0.3'
        }
    }
    stages {
        stage('Run tests') {
        steps {
            parallel(
            ktlint: {
                sh 'yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses && $ANDROID_HOME/tools/bin/sdkmanager --update'
                sh './gradlew clean ktlint'
            },
            Unit: {
              sh './gradlew clean testDebugUnitTest'
            },
      Espresso: {
            sh '$ANDROID_HOME/platform-tools/adb connect ${EMULATOR}:5555'
            sh './gradlew --stop'
            sh './gradlew clean forkDebugAndroidTest'
      }
      )
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
}
