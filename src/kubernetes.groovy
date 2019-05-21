import hudson.model.*
import jenkins.model.*
import org.csanchez.jenkins.plugins.kubernetes.*
import org.csanchez.jenkins.plugins.kubernetes.volumes.workspace.EmptyDirWorkspaceVolume
import org.csanchez.jenkins.plugins.kubernetes.volumes.HostPathVolume

//since kubernetes-1.0
//import org.csanchez.jenkins.plugins.kubernetes.model.KeyValueEnvVar
import org.csanchez.jenkins.plugins.kubernetes.PodEnvVar

//change after testing
ConfigObject conf = new ConfigSlurper().parse(new File(System.getenv("JENKINS_HOME") + '/jenkins_config/kubernetes.txt').text)

def kc
try {
    println("Configuring k8s")


    if (Jenkins.instance.clouds) {
        kc = Jenkins.instance.clouds.get(0)
        println "cloud found: ${Jenkins.instance.clouds}"
    } else {
        kc = new KubernetesCloud(conf.kubernetes.name)
        Jenkins.instance.clouds.add(kc)
        println "cloud added: ${Jenkins.instance.clouds}"
    }

    kc.setContainerCapStr(conf.kubernetes.containerCapStr)
    kc.setServerUrl(conf.kubernetes.serverUrl)
    kc.setSkipTlsVerify(conf.kubernetes.skipTlsVerify)
    kc.setNamespace(conf.kubernetes.namespace)
    kc.setJenkinsUrl(conf.kubernetes.jenkinsUrl)
    kc.setCredentialsId(conf.kubernetes.credentialsId)
    kc.setRetentionTimeout(conf.kubernetes.retentionTimeout)
    //since kubernetes-1.0
    kc.setConnectTimeout(conf.kubernetes.connectTimeout)
    kc.setReadTimeout(conf.kubernetes.readTimeout)
    //since kubernetes-1.0
//    kc.setMaxRequestsPerHostStr(conf.kubernetes.maxRequestsPerHostStr)

    println "set templates"
    kc.templates.clear()

    conf.kubernetes.podTemplates.each { podTemplateConfig ->

        def podTemplate = new PodTemplate()
        podTemplate.setLabel(podTemplateConfig.label)
        podTemplate.setName(podTemplateConfig.name)

        String fileContentsGithubToken = new File(podTemplateConfig.rawYamlFilePath).text
        podTemplate.setYaml(fileContentsGithubToken);

        if (podTemplateConfig.exclusive) {
            podTemplate.setNodeUsageMode(Node.Mode.EXCLUSIVE);
            println "I'm EXCLUSIVE node"
        } else {
            podTemplate.setNodeUsageMode(Node.Mode.NORMAL);
            println "I'm NORMAL node"
        }
        podTemplate.setNamespace(podTemplateConfig.nameSpace);
        podTemplate.setIdleMinutes(podTemplateConfig.idleMinutes);

        println "adding ${podTemplateConfig.name}"
        kc.templates << podTemplate

    }

    kc = null
    println("Configuring k8s completed")
}
finally {
    //if we don't null kc, jenkins will try to serialise k8s objects and that will fail, so we won't see actual error
    kc = null
}
