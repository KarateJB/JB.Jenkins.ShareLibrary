
def call(String extraMsg='') {

  echo "Notify by global function"

  def commitId = sh(returnStdout: true, script: 'git rev-parse --short HEAD') //Get short commit hash
  def buildStatus = currentBuild.currentResult //Get current build status

  // Default values
  def colorCode = '#FF0000'
  def msg = "${buildStatus} on #${env.BUILD_NUMBER} - ${STAGE_NAME} \n[${env.JOB_NAME}] - ${commitId} \n${env.BUILD_URL}"

  if (extraMsg?.trim()) {
      msg = "${msg} \n${extraMsg}"
  }

  if (buildStatus == 'SUCCESS') {
    colorCode = '#00FF00'
  }
  else if(buildStatus == 'ABORTED') {
    colorCode = '#FF0000'
  }
   else {
    colorCode = '#FF0000'
  }

  // Send notifications
  slackSend (color: colorCode, message: msg)
}

def isNotifySuccessBranch(String branch){
    
    def allowedBranches = env.NOTIFY_SUCCESS_BRANCHES.split(';')
    return allowedBranches.contains(env.BRANCH_NAME)
}