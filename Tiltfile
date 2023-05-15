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