<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
    <a href="#">
        <img src="/images/miranum_logo.png" alt="Logo" height="180">
    </a>
    <h3 ><a href="https://miranum.com/">Miranum Connect</a> <i>by <a href="https://miragon.io/">Miragon</a></i></h3>
    <p>
        <i>Simplifying the complexity of process automation.</i>
        <br />
        <a href="https://github.com/FlowSquad/miranum-connect/issues">Report Bug</a>
        ·
        <a href="https://github.com/FlowSquad/miranum-connect/pulls">Request Feature</a>
    </p>
</div>

## About The Project

Miranum-Connect is a library which enables developers to build reusable and technology neutral connectors and integrations between products.
Please find our official docs <a href="https://miranum.com/docs/components/miranum-connect/intro-miranum-connect">here</a>.
<p align="right">(<a href="#top">back to top</a>)</p>

## Get Started

Miranum-Connect is currently available as [snapshot in maven-central](https://s01.oss.sonatype.org/content/repositories/snapshots/io/miragon/miranum/).
You can import all java moduls as dependencies. For Example:

```xml
<dependency>
    <groupId>io.miragon.miranum</groupId>
    <artifactId>json-api</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

To pull dependencies from there, maven snapshot repo needs to be part of the settings.xml at your user profile:

<details>
  <summary><code>~/.m2/settings.xml</code></summary>

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
                  <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
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
</details>

<p align="right">(<a href="#top">back to top</a>)</p>

## Roadmap

*if you have a ROADMAP for your project add this here*

See the [open issues](#) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please open an issue with the tag "enhancement", fork the repo and
create a pull request. You can also simply open an issue with the tag "enhancement". Don't forget to give the project a
star! Thanks again!

1. Open an issue with the tag "enhancement"
2. Fork the Project
3. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
4. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the Branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

More about this in the [CODE_OF_CONDUCT](/CODE_OF_CONDUCT.md) file.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` file for more information.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/FlowSquad/miranum.svg?style=for-the-badge

[contributors-url]: https://github.com/FlowSquad/miranum/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/FlowSquad/miragon-process-ide.svg?style=for-the-badge

[forks-url]: https://github.com/FlowSquad/miranum/network/members

[stars-shield]: https://img.shields.io/github/stars/FlowSquad/miragon.svg?style=for-the-badge

[stars-url]: https://github.com/FlowSquad/miranum/stargazers

[issues-shield]: https://img.shields.io/github/issues/FlowSquad/miranum.svg?style=for-the-badge

[issues-url]: https://github.com/FlowSquad/miranum/issues

[license-shield]: https://img.shields.io/github/license/FlowSquad/miranum.svg?style=for-the-badge

[license-url]: https://github.com/FlowSquad/miranum/blob/main/LICENSE
