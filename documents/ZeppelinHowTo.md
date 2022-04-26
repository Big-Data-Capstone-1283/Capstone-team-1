# Zeppelin
#### Reminder to do this when starting Ubuntu:
- sudo service ssh restart

## Download and unzip Zeppelin
#### Check https://zeppelin.apache.org/download.html for latest version
- wget https://dlcdn.apache.org/zeppelin/zeppelin-0.10.1/zeppelin-0.10.1-bin-all.tgz
- tar --extract -f zeppelin-0.10.1-bin-all.tgz

## Add zeppelin home directory and path to .bashrc
- vim ~/.bashrc
#### Add these lines:
- #Zeppelin home directory
- export ZEPPELIN_HOME=/home/user/zeppelin-0.10.1-bin-all
- export PATH=$PATH:$ZEPPELIN_HOME/bin
#### Save and quit
- :wq
#### Source .bashrc
- source ~/.bashrc

## How to start/stop Zeppelin (similar to spark)
- zeppelin-daemon.sh start
- zeppelin-daemon.sh stop
#### Try starting it and going to http://localhost:8080/
#### If it doesn't work, see Problem 1, 2

## Problem 1
### We have to set up some configuration files manually

#### Change configuration files here:
- cd  ~/zeppelin-0.10.1-bin-all/conf/
#### Copy zeppelin-site.xml.template and remove .template
- cp zeppelin-site.xml.template zeppelin-site.xml

## (Optional)
#### You can set up users with shiro.ini file
- cd  ~/zeppelin-0.10.1-bin-all/conf/
- cp shiro.ini.template shiro.ini
- vim shiro.ini
	
#### Scroll down and you can set usernames and password
#### Uncomment admin user if you want that
- :wq

## Problem 2
### Because we already set up Spark to use 8080, I was having trouble using Zeppelin on 8080 (Change to port 8090)
#### !! Make sure daemon is stopped before doing this (zeppelin-daemon.sh stop)

#### Change port here:
- vim ~/zeppelin-0.10.1-bin-all/conf/zeppelin-site.xml
#### At line 30, change from port 8080 to 8090 (Right below: zeppelin.server.port)	
- :wq
#### Allow ssh - port 8090 (you will get connection refused if you don't) 
#### !! Make sure to change to your username and @
- zeppelin-daemon.sh start
- ssh -L 8090:localhost:8090 username@DESKTOP-ABC123U

### Use browser to navigate here:
- http://localhost:8090/	

## Problem 3
### "channel 2: open failed: connect failed: Connection refused"
#### I got this when stopping zeppelin-daemon
#### Just restart ssh
- Ctrl + C
- sudo service ssh restart
