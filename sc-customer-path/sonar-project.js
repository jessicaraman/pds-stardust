const JENKINS_REPORTER_ENABLE_SONAR = true
const sonarqubeScanner = require('sonarqube-scanner');
    sonarqubeScanner({
        serverUrl: 'http://172.31.249.114:9000',
        options : {
        'sonar.sources': '.',
        'sonar.inclusions': 'src/**',
        'sonar.projectKey': 'sc-customer-path',
        'sonar.projectName': 'Customer Path Microservice',
        'sonar.language': 'js',
        'sonar.profile': 'node',
        //'sonar.testExecutionReportPaths' : ''
        }
    }, () => {});

