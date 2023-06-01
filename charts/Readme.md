# Kubernetes Charts

## Local Setup
To run this setup locally, make sure, you have Docker, kubectl and helm installed. Use `brew` on mac and `choco` on windows. 
Activate Docker Desktops Kubernetes feature and point your kubectl to the local cluster. 

```bash
kubectl config use-context docker-desktop
kubectl cluster-info
```

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

## Working with helm charts

```bash
helm create engine # create new helm chart
```

