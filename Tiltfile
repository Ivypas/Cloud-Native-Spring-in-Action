# The file will contain three main configurations:
#  1. How to build a container image (Cloud Native Buildpacks)
#  2. How to deploy the application (Kubernetes YAML manifests)
#  3. How to access the application (port forwarding)

# Build
custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # Command to build the container image
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    # Files to watch that trigger a new build
    deps = ['build.gradle', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])