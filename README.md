<div id="top"></div>

<!-- PROJECT SHIELDS -->

<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="#">
    <img src="/images/logo.png" alt="Logo" height="50">
  </a>

<h3 align="center">Miranum</h3>

  <p align="center">
    <i>One connector to rule them all</i>
    <br /><a href="https://github.com/flowsquad/miranum/issues">Report Bug</a>
    ·
    <a href="https://github.com/flowsquad/miranum/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->

## About The Project

```mermaid
  graph LR;
      C7-in-->W{{Worker}};
      C8-in-->W;
      W-->C7-out;
      W-->C8-out;
 
```

```mermaid
  graph LR;
      Rest-->M{{Message}};
      Spring-Cloud-Stream-->M;
      M-->C7-out;
      M-->C8-out;
```

```mermaid
  graph LR;
      nativ-->P{{Process}};
      Rest-->P;
      Kafka-in-->P;
      P-->C7-out;
      P-->C8-out;
      P-->Kafka-out;
```

```mermaid
  graph LR;
      J{{Job}};
```
<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

The project is built with technologies:

* Spring Boot

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

*if you have a ROADMAP for your project add this here*

See the [open issues](#) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting started

*how can I start and fly this project*

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
