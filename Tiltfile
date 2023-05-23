# The file will contain three main configurations:
#  1. How to build a container image (Cloud Native Buildpacks)
#  2. How to deploy the application (Kubernetes YAML manifests)
#  3. How to access the application (port forwarding)

# 0. OS Identification (https://stackoverflow.com/questions/75849867/spring-boot-tilt-build-for-local-kubernetes-envirnoment-tilt-throws-error)
gradlew = "./gradlew"
expected_ref = "$EXPECTED_REF"
if os.name == "nt":
  gradlew = "gradlew.bat"
  expected_ref = "%EXPECTED_REF%"

# 1. Build
custom_build(
    # Name of the container image
    # 1.0 在 k8s/deployment.yml 中写全容器全限定名 local/ivypas/catalog-service:chapter7
    # 1.1 在根目录手动构建镜像，执行 bootBuildImage --imageName local/ivypas/catalog-service:chapter7
    # 1.2 在宿主机构建好镜像后，执行 minikube image load 将镜像移进 minikube
    # 1.3 此时 Tiltfile 中的容器名若写成 catalog-service 就可以跳过 build 步骤，直接使用刚才挪进去的镜像，
    #     而不用在 minikube 内拉取 paketobuildpack，所以不要写 local/ivypas/catalog-service:chapter7
    ref = 'catalog-service',
    # Command to build the container image
    command = gradlew + ' bootBuildImage --imageName ' + expected_ref,
    # Files to watch that trigger a new build
    deps = ['build.gradle', 'src']
)

# 2. Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# 3. Manage
k8s_resource('catalog-service', port_forwards=['9001'])