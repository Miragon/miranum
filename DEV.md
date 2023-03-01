# Getting Started

Miranum Connect is powered by maven. To set up the moduls run `mvn build`.

## Testing

Start the example projects.

## Publishing

1. Increase Maven version
    ``` bash
   mvn versions:set -DnewVersion="0.1.0"
   mvn versions:commit
   git commit -a -m "release 0.1.0"
   git push
   ```
2. Create a new pull request and get an approval
3. As a developer of Miragon GmbH, you can access the repos actions and publish a new release by clicking "Run Workflow" in the [release section](https://github.com/FlowSquad/miranum/actions/workflows/release.yaml). Please enter the new version number as release tag.

## Developing

Miranum-Connect is currently available as snapshot in [maven-central](https://s01.oss.sonatype.org/content/repositories/snapshots/io/miragon/miranum/).
To pull dependencies from there, maven snapshot repo needs to be part of your `~/.m2/settings.xml`:

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
   <localRepository />
   <interactiveMode />
   <offline />
   <pluginGroups />
   <servers />
   <mirrors />
   <proxies />
   <profiles>
      <profile>
         <id>allow-snapshots</id>
         <activation>
            <activeByDefault>true</activeByDefault>
         </activation>
         <repositories>
            <repository>
               <id>snapshots-repo</id>
               <url>https://oss.sonatype.org/content/repositories/snapshots</url>
               <releases>
                  <enabled>false</enabled>
               </releases>
               <snapshots>
                  <enabled>true</enabled>
               </snapshots>
            </repository>
         </repositories>
      </profile>
   </profiles>
   <activeProfiles />
</settings>
```
