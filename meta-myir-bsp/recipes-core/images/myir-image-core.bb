SUMMARY = "Arago TI SDK base image with test tools"

DESCRIPTION = "Arago SDK base image suitable for initramfs containing\
 comprehensive test tools."

require myir-base-image.inc

IMAGE_FSTYPES += "cpio.xz"

ARAGO_BASE_IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += "\
    packagegroup-myir-base \
    packagegroup-myir-console \
    packagegroup-myir-base-tisdk \
    ${VIRTUAL-RUNTIME_initramfs} \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', 'ti-test', '', d)} \
    ${ARAGO_BASE_IMAGE_EXTRA_INSTALL} \
    auto-wifi \
    fgl297-fw \
    wifi-load \
   	nxp-wlan-sdk \
    ppp-quectel \
    ppp \
    quectel-cm \
    coreutils \
    util-linux \
    tslib \
    fbv \
    hwmac \
   proftpd \
"

export IMAGE_BASENAME = "myir-image-core"
