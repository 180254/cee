# Task: cache [ignite env, login-app, web-session-clustering]

### Content
| Path | Purpose | Comment |
| ------ | ------ | ------ |
| Vagrantfile | vagrant config file |
| **up.sh** | script to create and configure vms |
| **destroy.sh** | script to destroy vms |
| **app.sh** | script to compile, deploy and run login-app | |
| **app/** | login-app java application |
| shells/ | bash scripts to be provisioned to vms |
| files/ | other files to be provisioned to vms |
| files/archives/ | /var/cache/apt/archives/ backup | will speed up configuring, if is up to date |
| files/lists/ | /var/lib/apt/lists/ backup | will speed up configuring, if is up to date |
| files/resources/ | other cache resources, supported files: apache-ignite-fabric-1.9.0-bin.zip | will speed up configuring, if file is available |
| files/nginx/default | nginx load balancer configuration file |
| **app/src/main/resources/config-ignite.xml** | ignite node configuration file |

### Requirements
To be installed on host:
- JDK 1.8
- Vagrant (https://www.vagrantup.com/)
- VirtualBox (https://www.virtualbox.org)
- Vagrant plugin: vagrant-vbguest (vagrant plugin install vagrant-vbguest)

### VM boxes
hostname=ignite1
- 192.168.6.10:9000 - login-app instance
- 192.168.6.10:9001 - login-app instance

hostname=ignite2
- 192.168.6.11:9000 - login-app instance
- 192.168.6.11:none - ignite instance
- 192.168.6.11:80 - load balancer entry point

### Notepad
Useful notes:
- https://apacheignite-mix.readme.io/docs/web-session-clustering
- https://github.com/Normal/apache-ignite-testproject
- https://ignite.apache.org/features.html#datagrid
