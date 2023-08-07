# How To : Discord API
Test project to get used to Java Discord API lib


## App - How to deploy and run
1. Clone the repo to your machine
2. Run Mavens ```install``` lifecycle
3. Go to Mavens ```target/externalConfigs``` folder and rename the ```default_discordSettings.properties```
file. Remove the leading ```default_``` string. This will let the application read the file for gathering all necessary 
data for connecting to discord.
4. Copy the following files from mavens ```target``` folder to your desired application folder:
``` Java
   target
   |
   +-- HowTo_JavaDiscordAPI-<versionNumber>.jar
       |
       +-- lib/
       |
       +-- externalConfigs/
```
5. Now go to your applications folder and type the following command, for running the 
application 
```java -jar HowTo_JavaDiscordAPI-<versionNumber>.jar```


## App - Features
### External .properties-file
Properties-Files placed inside the projects ```src/main/resources/externalConfigs``` folder
will be moved to ```externalConfigs``` subfolder of the installed JAR file as soon as mavens ```install```
lifecycle is getting started. The copy-mechanism is defined in the ```pom.xml``` file, 
using mavens _maven-resources-plugin_. Those properties files will not be included inside the JAR output file
of your application if you're going to run mavens ```install``` lifecycle. To exclude the folder from JAR
file, the _maven-jar-plugin_ is used inside the pom.xml.

Keep in mind that you can place your ```discordSettings.properties``` file with your discord 
token into the project folder. This file is already git-ignored.

### Deploy all dependencies into lib sub-folder
When you are going to use Mavens ```install``` lifecycle, all project related dependencies are going to be copied to
the ```/lib``` subfolder of the installed project by using _maven-dependency-plugin_ inside the ```pom.xml``` file.
Additionally, you have to point to the new lib folder inside the JAR's MANIFEST file. 
This is set by using the ```classPath``` value of Mavens _maven-jar-plugin_ inside the ```pom.xml``` file.

## Appendix - Application Settings
### log4j.properties
You can define the behaviour of the log4j-logger by changing the values inside the log4j.properties file.
A short description can be found on the Oracle
website [here](https://docs.oracle.com/cd/E29578_01/webhelp/cas_webcrawler/src/cwcg_config_log4j_file.html).

### discordSettings.properties
Here you have to put all the Discord related stuff into. The file is located at the ```externalConfigs``` subfolder
of the installed application or at the project folder ```src/main/java/resources/externalConfigs```
- **settings.discord.token=**</br>
  Put in the token for your discord-application (bot). Before this application can be run, you have to ensure that you
  have created a discord bot first. [Here](https://www.ionos.com/digitalguide/server/know-how/creating-discord-bot/)
  is a small HowTo on how to create your own discord bot for getting you started.

## Appendix - Application Dependencies

### Java Discord API
Discord API Wrapper for Java.</br>
- [JDA (Java Discord API)](https://github.com/discord-jda/JDA)

### slf4j - Logging API with log4j
JDA only uses the logging API slf4j. You can select
whatever logger lib you want that is compatible with slf4j.
So both dependencies are necessary. I decided to use log4j. This is included in the reload4j lib. </br>
- [slf4j-reload4j](https://mvnrepository.com/artifact/org.slf4j/slf4j-reload4j)</br>
- [slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)


## Appendix - Video Tutorial Links
Some links to video tutorials I used for tose examples in here

- [Embeds](https://www.youtube.com/watch?v=btuzgvDZhA8&list=PLfu_Bpi_zcDNcs3j5tR6VQA5fSVDCpNeC&index=5)
- [RestActions, e.g. queue() ](https://jda.wiki/using-jda/using-restaction/)