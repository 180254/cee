# Task: prehadoop  
  
### Content
  
| Path | Purpose | Comment |
| ------ | ------ | ------ |
| Vagrantfile | main config file |
| up.sh | script to create and configure vms |
| destroy.sh | script to destroy vms |
| shells/ | bash scripts to be provisioned to vms |
| files/ | other files to be provisioned to vms |
| files/archives/ | /var/cache/apt/archives/ backup | will speed up configuring, if is up to date |
| files/lists/ | /var/lib/apt/lists/ backup | will speed up configuring, if is up to date |
| files/resources/ | other cache resources, supported files: hadoop-2.6.4.tar.gz | will speed up configuring, if file is available
| files/hadoop/ | hadoop configuration files |
| files/system/ | system configuration files |
| files/system/{id_rsa, id_rsa.pub} | ssh key used in vms | do NOT use this key anywhere, it is only for this educational task
  
### Requirements
To be installed on host:
- Vagrant (https://www.vagrantup.com/)
- VirtualBox (https://www.virtualbox.org)
- Vagrant plugin: vagrant-vbguest (vagrant plugin install vagrant-vbguest)
  
### Notepad
Useful notes:
https://hadoop.apache.org/docs/r2.6.4/hadoop-project-dist/hadoop-common/SingleCluster.html
https://www.aerospike.com/docs/connectors/ashadoop/handson/hadoopClusterSetup.html
