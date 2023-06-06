# Kubernetes Charts

## Local Setup
To run this setup locally, make sure, you have Docker, kubectl and helm installed. Use `brew` on mac and `choco` on windows. 
Activate Docker Desktops Kubernetes feature and point your kubectl to the local cluster. 

```bash
# configure kubectl to use local cluster
kubectl config use-context docker-desktop
kubectl cluster-info
```
Kubernetes master is running at https://kubernetes.docker.internal:6443

```bash
# create a proxy server between your machine and Kubernetes API server
kubectl proxy 
```

```bash
# install nginx as global ingress controller
helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace
```

http://localhost:8001/api/v1/namespaces/process-platform/services/keycloak:9090/proxy/

### Local Dashboard Setup using helm

```bash
# install kubernetes dashboard
helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard
helm install dashboard kubernetes-dashboard/kubernetes-dashboard
export POD_NAME=$(kubectl get pods -n default -l "app.kubernetes.io/name=dashboard,app.kubernetes.io/instance=kubernetes-dashboard” -o jsonpath=”{.items[0].metadata.name}")
kubectl -n default port-forward $POD_NAME 8443:8443 # open browser at https://127.0.0.1:8443

# login via token
kubectl -n kube-system create serviceaccount admin-sa
kubectl create clusterrolebinding admin-crb --clusterrole=cluster-admin --serviceaccount=kube-system:admin-sa
export TOKENNAME=$(kubectl -n kube-system get serviceaccount/admin-sa -o jsonpath='{.secrets[0].name}')\n
kubectl -n kube-system get secret $TOKENNAME -o jsonpath='{.data.token}' | base64 --decode # copy this token in the dashboard login
```

