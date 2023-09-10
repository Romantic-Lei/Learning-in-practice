### Secret 存在意义

Secret 解决了密码、token、密钥等敏感数据的配置问题，而不需要把这些敏感数据暴露到镜像或者 Pod Spec 中。Secret 可以以 Volume 或者环境变量的方式使用

### Secret 有三种类型

- Service Account :用来访问 Kubernetes API,由 Kubernetes 自动创建，并且会自动挂载到 Pod 的 /run/secrets/kubernetes.io/serviceaccount 目录中

- Opaque : base64 编码格式的 Secret,用来存储密码、密钥等
- kubernetes.io/dockerconfigjson ：用来存储私有 docker registry 的认证信息

### Service Account

Service Account 用来访问 Kubernetes API,甶 Kubernetes 自动创建，并且会自动挂载到Pod 的/run/secrets/kubernetes.io/serviceaccount 目录中

```
$ kubectl run nginx --image nginx
deployment "nginx" created

$ kubectl get pods
NAME 					READY 	STATUS 	RESTARTS	AGE
nginx-3137573019-md1u2 	1/1 	Running 0 			13s

$ kubectl exec nginx-3137573019-md1u2 ls /run/secrets/kubernetes.io/serviceaccount

ca.crt
namespace
token
```





















