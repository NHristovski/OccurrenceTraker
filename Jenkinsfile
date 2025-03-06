#!groovy

library identifier: 'JenkinsLibrary@main',
        retriever: modernSCM(
                [$class       : 'GitSCMSource',
                 remote       : 'http://gitea:3000/DevOps_Master_studies/JenkinsLibrary.git',
                 credentialsId: 'LocalGiteaAdmin'])

DevOpsMasterStudiesPipeline(
        'application': 'OccurrenceTraker',
)