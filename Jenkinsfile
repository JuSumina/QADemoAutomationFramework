pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.0'  // This name will be configured in Jenkins
        jdk 'JDK-19'         // This name will be configured in Jenkins
    }
    
    environment {
        // Environment variables for the pipeline
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=512m'
        BROWSER = 'chrome'
        HEADLESS = 'true'
        PROJECT_NAME = 'QA Demo Automation Framework'
    }
    
    parameters {
        choice(
            name: 'BROWSER_TYPE',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select browser for test execution'
        )
        choice(
            name: 'TAG_NAME', 
            choices: ['smoke', 'loginregression', 'all'],
            description: 'Select test tags to execute'
        )
        booleanParam(
            name: 'HEADLESS_MODE',
            defaultValue: true,
            description: 'Run tests in headless mode (recommended for CI/CD)'
        )
        string(
            name: 'TEST_URL',
            defaultValue: '',
            description: 'Override application URL (leave empty to use config default)'
        )
    }
    
    stages {
        stage('🔍 Checkout Code') {
            steps {
                echo "🚀 Starting ${env.PROJECT_NAME} Pipeline - Build #${env.BUILD_NUMBER}"
                echo "📂 Checking out source code from Git..."
                checkout scm
                
                // Display build information
                script {
                    echo "=== BUILD INFORMATION ==="
                    echo "Job Name: ${env.JOB_NAME}"
                    echo "Build Number: ${env.BUILD_NUMBER}"
                    echo "Git Branch: ${env.GIT_BRANCH}"
                    echo "Git Commit: ${env.GIT_COMMIT}"
                    echo "Selected Browser: ${params.BROWSER_TYPE}"
                    echo "Selected Tags: ${params.TAG_NAME}"
                    echo "Headless Mode: ${params.HEADLESS_MODE}"
                    echo "========================="
                }
            }
        }
        
        stage('🔧 Build & Compile') {
            steps {
                echo '🔨 Building the project and compiling test classes...'
                script {
                    try {
                        bat 'mvn clean compile test-compile'
                        echo '✅ Build successful!'
                    } catch (Exception e) {
                        echo "❌ Build failed: ${e.getMessage()}"
                        throw e
                    }
                }
            }
        }
        
        stage('🧪 Run Automated Tests') {
            steps {
                echo '🎯 Executing automated tests...'
                script {
                    // Build Maven command with parameters
                    def mavenCommand = 'mvn test'
                    
                    // Add tag filter if not running all tests
                    if (params.TAG_NAME != 'all') {
                        mavenCommand += " -Dcucumber.filter.tags=\"@${params.TAG_NAME}\""
                    }
                    
                    // Add browser parameter
                    mavenCommand += " -Dbrowser=${params.BROWSER_TYPE}"
                    
                    // Add headless parameter
                    mavenCommand += " -Dheadless=${params.HEADLESS_MODE}"
                    
                    // Add custom URL if provided
                    if (params.TEST_URL?.trim()) {
                        mavenCommand += " -Durl=${params.TEST_URL}"
                    }
                    
                    // Continue build even if tests fail (to generate reports)
                    mavenCommand += ' -Dmaven.test.failure.ignore=true'
                    
                    echo "🚀 Executing: ${mavenCommand}"
                    
                    try {
                        bat mavenCommand
                        echo '✅ Test execution completed!'
                    } catch (Exception e) {
                        echo "⚠️ Some tests may have failed, but continuing to generate reports..."
                    }
                }
            }
        }
        
        stage('📊 Generate & Publish Reports') {
            steps {
                echo '📈 Generating and publishing test reports...'
                
                // Publish HTML reports
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'html-report.html',
                    reportName: '📋 Cucumber HTML Report',
                    reportTitles: 'Test Execution Report'
                ])
                
                // Archive test artifacts
                script {
                    try {
                        // Archive screenshots
                        archiveArtifacts artifacts: 'target/screenshots/**/*.png', 
                                       fingerprint: true, 
                                       allowEmptyArchive: true
                        echo '📸 Screenshots archived successfully'
                    } catch (Exception e) {
                        echo '⚠️ No screenshots to archive (tests may have passed)'
                    }
                    
                    try {
                        // Archive logs
                        archiveArtifacts artifacts: 'target/logs/**/*.log', 
                                       fingerprint: true, 
                                       allowEmptyArchive: true
                        echo '📄 Logs archived successfully'
                    } catch (Exception e) {
                        echo '⚠️ No logs to archive'
                    }
                    
                    try {
                        // Archive JSON reports for further processing
                        archiveArtifacts artifacts: 'target/cucumber-reports/*.json', 
                                       fingerprint: true, 
                                       allowEmptyArchive: true
                        echo '📊 JSON reports archived successfully'
                    } catch (Exception e) {
                        echo '⚠️ No JSON reports to archive'
                    }
                }
            }
        }
        
        stage('📊 Test Results Analysis') {
            steps {
                echo '🔍 Analyzing test results...'
                script {
                    // Parse test results if available
                    try {
                        def testResults = readFile('target/cucumber-reports/cucumber.json')
                        echo '✅ Test results parsed successfully'
                        
                        // You can add custom logic here to parse results
                        // and set build status based on test outcomes
                    } catch (Exception e) {
                        echo '⚠️ Could not parse test results, but continuing...'
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo '🧹 Cleaning up workspace and finalizing build...'
            
            // Clean up large files but keep reports
            script {
                try {
                    bat 'rmdir /s /q target\\classes 2>nul || echo "No classes directory to clean"'
                    bat 'rmdir /s /q target\\test-classes 2>nul || echo "No test-classes directory to clean"'
                    echo '✅ Workspace cleaned successfully'
                } catch (Exception e) {
                    echo '⚠️ Workspace cleanup had issues, but continuing...'
                }
            }
            
            // Always generate build summary
            script {
                def buildStatus = currentBuild.result ?: 'SUCCESS'
                def buildDuration = currentBuild.durationString
                
                echo "=== BUILD SUMMARY ==="
                echo "Status: ${buildStatus}"
                echo "Duration: ${buildDuration}"
                echo "Browser: ${params.BROWSER_TYPE}"
                echo "Tags: ${params.TAG_NAME}"
                echo "Headless: ${params.HEADLESS_MODE}"
                echo "Reports: Available in Jenkins"
                echo "====================="
            }
        }
        
        success {
            echo '🎉 Pipeline executed successfully!'
            
            // Send success notification
            script {
                try {
                    emailext (
                        subject: "✅ Test Execution Successful - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                        body: """
                            <div style='font-family: Arial, sans-serif;'>
                                <h2 style='color: green;'>🎉 Test Execution Completed Successfully!</h2>
                                
                                <h3>📋 Build Information:</h3>
                                <ul>
                                    <li><strong>Job:</strong> ${env.JOB_NAME}</li>
                                    <li><strong>Build Number:</strong> ${env.BUILD_NUMBER}</li>
                                    <li><strong>Duration:</strong> ${currentBuild.durationString}</li>
                                    <li><strong>Browser:</strong> ${params.BROWSER_TYPE}</li>
                                    <li><strong>Test Tags:</strong> ${params.TAG_NAME}</li>
                                    <li><strong>Headless Mode:</strong> ${params.HEADLESS_MODE}</li>
                                </ul>
                                
                                <h3>🔗 Links:</h3>
                                <ul>
                                    <li><a href="${env.BUILD_URL}">📊 Build Details</a></li>
                                    <li><a href="${env.BUILD_URL}Cucumber_20HTML_20Report/">📋 Test Report</a></li>
                                    <li><a href="${env.BUILD_URL}console">📄 Console Output</a></li>
                                </ul>
                                
                                <p style='color: green;'><strong>✅ All tests executed successfully!</strong></p>
                            </div>
                        """,
                        to: 'yuliyasumina@gmail.com',
                        mimeType: 'text/html'
                    )
                } catch (Exception e) {
                    echo "⚠️ Could not send email notification: ${e.getMessage()}"
                }
            }
        }
        
        failure {
            echo '❌ Pipeline failed!'
            
            // Send failure notification
            script {
                try {
                    emailext (
                        subject: "❌ Test Execution Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                        body: """
                            <div style='font-family: Arial, sans-serif;'>
                                <h2 style='color: red;'>❌ Test Execution Failed</h2>
                                
                                <h3>📋 Build Information:</h3>
                                <ul>
                                    <li><strong>Job:</strong> ${env.JOB_NAME}</li>
                                    <li><strong>Build Number:</strong> ${env.BUILD_NUMBER}</li>
                                    <li><strong>Duration:</strong> ${currentBuild.durationString}</li>
                                    <li><strong>Browser:</strong> ${params.BROWSER_TYPE}</li>
                                    <li><strong>Test Tags:</strong> ${params.TAG_NAME}</li>
                                    <li><strong>Headless Mode:</strong> ${params.HEADLESS_MODE}</li>
                                </ul>
                                
                                <h3>🔗 Debug Links:</h3>
                                <ul>
                                    <li><a href="${env.BUILD_URL}">📊 Build Details</a></li>
                                    <li><a href="${env.BUILD_URL}console">📄 Console Output (Check for errors)</a></li>
                                    <li><a href="${env.BUILD_URL}artifact/">📁 Build Artifacts</a></li>
                                </ul>
                                
                                <p style='color: red;'><strong>⚠️ Please check the console logs and fix any issues.</strong></p>
                            </div>
                        """,
                        to: 'yuliyasumina@gmail.com',
                        mimeType: 'text/html'
                    )
                } catch (Exception e) {
                    echo "⚠️ Could not send email notification: ${e.getMessage()}"
                }
            }
        }
        
        unstable {
            echo '⚠️ Build is unstable (some tests failed)'
            // You can add specific actions for unstable builds
        }
    }
}