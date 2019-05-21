{
  "vault": {
    "address": "https://vault.tooling.zone:8200"
  },
  
  "template": {
    "source": "/var/jenkins_home/jenkins_config/config/kubernetes.ctmpl",
    "destination": "/var/jenkins_home/jenkins_config/kubernetes.txt"
  },

  "template": {
    "source": "/var/jenkins_home/jenkins_config/config/node-team1.pod.yaml.ctmpl",
    "destination": "/var/jenkins_home/jenkins_config/config/node-team1.pod.yaml"
  },

  "template": {
    "source": "/var/jenkins_home/jenkins_config/config/node-team2.pod.yaml.ctmpl",
    "destination": "/var/jenkins_home/jenkins_config/config/node-team2.pod.yaml"
  },

  "template": {
    "source": "/var/jenkins_home/jenkins_config/config/node-team3.pod.yaml.ctmpl",
    "destination": "/var/jenkins_home/jenkins_config/config/node-team3.pod.yaml"
  },

  "template": {
    "source": "/var/jenkins_home/jenkins_config/config/node-team4.pod.yaml.ctmpl",
    "destination": "/var/jenkins_home/jenkins_config/config/node-team4.pod.yaml"
  }
}
