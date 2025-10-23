SUMMARY = "A full-featured image for MYIR platforms with development tools and multimedia support."

IMAGE_FEATURES += "splash"

LICENSE = "MIT"

inherit core-image

# Add packages similar to tisdk-default-image
IMAGE_INSTALL:append = " \
    packagegroup-arago-gst-sdk-target \
    resize-rootfs \
"

# Platform-specific packages for MYIR boards
IMAGE_INSTALL:append:am62lxx = " mosquitto libmosquitto1 libmosquittopp1 mosquitto-clients mosquitto-dev"
IMAGE_INSTALL:append:am62xx = " ti-gst-plugins-source ti-gst-plugins-dev ti-gst-utils nxp-wlan-sdk"
IMAGE_INSTALL:append:am62pxx = " ti-gst-plugins-source ti-gst-plugins-dev ti-gst-utils"
IMAGE_INSTALL:remove:am62dxx = " packagegroup-arago-tisdk-graphics"
