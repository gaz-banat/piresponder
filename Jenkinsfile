// First attempt at Jenkinsfile
node {
    
    stage 'Checkout'
    
    git branch: 'master', url: 'https://github.com/gaz-banat/piresponder.git'
    
    // Get the maven tool
    
    // ** NOTE: This 'M3' maven tool must be confgured in the global configuration
    
    def mvnHome = tool 'M3'
    
    stage 'Build'
    
    sh "${mvnHome}/bin/mvn -f pom.xml clean install -DskipTests"
    
}
