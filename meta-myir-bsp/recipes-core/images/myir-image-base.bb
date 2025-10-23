SUMMARY = "A console-only image that fully supports the target device hardware for MYIR platforms."

IMAGE_FEATURES += "splash"

LICENSE = "MIT"

inherit core-image

# Add resize-rootfs functionality like tisdk-base-image
IMAGE_INSTALL:append = " resize-rootfs"
