# Spring-for-SWC3-Tech2

## Problem description 
At our university, each semester students are assigned to courses (mandatory or electives). At the moment the process of presenting and assigning to these courses is not digital. You are to create a web-app that will digitalize this system.

## Install the system, for Linux only! (in progress)
1. Install git
2. Clone the repository
3. **CHANGE THE URL OF THE DATABASE steps** *in progress*
4. Install maven
5. Change to the root directory of the project
6. Run **mvn clean** and then **mvn package**
7. Locate the *war* file under the */target* directory. You can rename if you want
8. Copy the file to the following folders: */var/lib/tomcat8/webapps/* and */usr/share/tomcat8/webapps*
9. Start or restart your *Tomcat* application.
10. Access the system by going to the following URL: "http://<YOUR_IP>:8080/<NAME_OF_WAR>" (exclude the *.war* extension of the file name from the URL)
(E.g. 52.52.52.52:8080/demo)


## Using the application
Assuming you already have a computer on which the system will run and a database to which it can connect:
1. Start the application, it will create all required database tables and will populate them with minimum needed data
2. Access the application by going to the IP of the server, on port 8080
3. Most of the functionality requires to be logged in. For this, following accounts can be used: *tech1@kea.dk*, *tech2@kea.dk*, *admin@kea.dk*, *stud1@kea.dk*, *stud2@kea.dk*. The password for all accounts is *1234*.
The first 2 accounts are for teachers, 3rd is for administrator and last 2 are for students. Each type of accounts has its own rights and actions it can do.
4. Use the system. :)

## Authors

* **Cristian Betivu** - *System Development* - [Krismix1](https://github.com/Krismix1)

## Deployment

This system was deployed on AWS. One of the main purposes was to make the system secure.
In order to achieve this, the following architecture was designed:

1. Create a *VPC* in order to work in a clean environment
2. Create and associate an *Internet Gateway* to allow communication between instances in the *VPC* and the internet
3. Create 2 *Route Tables*, of which one has the *IGW* attached to it, thus allowing communication on internet and second one without, thus allowing creation of instances that are not available from internet
4. Create a *Network Access Control List* to configure more inbound and outbound rules
5. Create 2 *subnets* which use the *RT* without the *IGW*, so that the *subnets* remain private (2 because AWS Launch Wizard complained)
6. Create a *subnet* which uses the *RT* with the *IGW*, so that the *subnet* is public
7. Create a *RDS* instance in the *private subnet*, with *MySQL engine* on which the database will be saved, with inbound traffic on port 22 (for *SSH*) and 3306 (for *MySQL*) and outbound everything
8. Create an *EC2* instance in the *public subnet* on which the application will be, with inbound traffic on port 8080 (for *Tomcat*) and port 22 (for *SSH*) and outbound everything
9. Install *Tomcat* on the *EC2*
10. Deploy the **war** file of the system to *Tomcat* by using the manager GUI