### Namespace 概述

Namespace 在很多情况下用于实现多用户的资源隔离，通过将集群内部的资源对象分配到不同的 Namespace 中， 形成逻辑上的分组，便于不同的分组在共享使用整个集群的资源同时还能被分别管理。Kubernetes 集群在启动后，会创建一个名为"default"的 Namespace，如果不特别指明 Namespace,则用户创建的 Pod，RC，Service 都将 被系统 创建到这个默认的名为 default 的 Namespace 中。

### Namespace 创建

```yaml
apiVersion: v1 
kind: Namespace 
metadata:
	name: development
---------------------
apiVersion: v1 
kind: Pod 
metadata:
	name: busybox 
	namespace: development
spec:
    containers:
    - image: busybox 
      command:
        - sleep
        - -"3600"
name: busybox
```

### Namespace 查看

```
kubectl get pods --namespace=development
```



