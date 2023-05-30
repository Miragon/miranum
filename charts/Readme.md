# Kubernetes Charts

## Local Setup
There are various local kubernets setups, like minicube, kind or k3s. k3s is a lightweight wrapper to run k3s (Rancher Lab’s minimal Kubernetes distribution) in Docker and is fully compliant with “full” Kubernetes. Get started reading [this guide](https://medium.com/@munza/local-kubernetes-with-k3d-helm-dashboard-6510d906431b).

To run this setup locally, make sure, you have Docker, k3d, kubectl and helm installed. Use `brew` on mac and `choco` on windows.

### Useful commands
``` bash
helm create engine # create new helm chart
k3d cluster create local-k8s # create local node
k3d cluster delete local-k8s # delete local node
kubectl cluster-info # cluster info
```
