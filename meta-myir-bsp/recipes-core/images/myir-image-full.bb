SUMMARY = "MYIR SDK full filesystem image"

DESCRIPTION = "Complete MYIR SDK filesystem image containing complete\
 applications and packages to entitle the SoC."

LICENSE = "MIT"

COMPATIBLE_MACHINE = "ti-soc"

IMAGE_FSTYPES += "cpio.xz wic"
IMAGE_FEATURES += "package-management splash"

# 4KB per 1 inode should be enough
EXTRA_IMAGECMD:ext2.gz += "-i 4096"

IMAGE_INSTALL = " \
	packagegroup-core-boot \
	${CORE_IMAGE_EXTRA_INSTALL} \
"

IMAGE_LINGUAS = ""

TOOLCHAIN_HOST_TASK:append = " nativesdk-buildtools-perl-dummy"
SDK_PACKAGE_ARCHS += "buildtools-dummy-${SDKPKGSUFFIX}"

BAD_RECOMMENDATIONS += " \
    libulm-dev \
    gdbserver-c6x-dev \
    coreutils \
    coreutils-dev \
"
BAD_RECOMMENDATIONS += "${@oe.utils.conditional("INIT_MANAGER", "sysvinit", "", "busybox-syslog", d)}"

inherit core-image remove-net-rules

# Set default timezone to Shanghai
DEFAULT_TIMEZONE = "Asia/Shanghai"

MYIR_DEFAULT_IMAGE_EXTRA_INSTALL ?= ""

# we're assuming some display manager is being installed with opengl
SYSTEMD_DEFAULT_TARGET = "${@bb.utils.contains('DISTRO_FEATURES','opengl','graphical.target','multi-user.target',d)}"

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    kernel-modules \
    ti-test \
    ti-test-extras \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-tisdk-graphics','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-tisdk-gtk','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opencl','packagegroup-arago-opencl','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','vulkan','packagegroup-arago-vulkan','',d)} \
    packagegroup-arago-tisdk-connectivity \
    packagegroup-arago-tisdk-crypto \
    packagegroup-arago-tisdk-multimedia \
    packagegroup-arago-tisdk-addons \
    packagegroup-arago-tisdk-addons-extra \
    packagegroup-arago-gst-sdk-target \
    resize-rootfs \
    tslib \
    tslib-tests \
    u-boot-fw-utils \
    bluez5 \
    packagegroup-tools-bluetooth \
    fgl297-fw \
    ppp-quectel \
    quectel-cm \
    fw-env-emmc \
    auto-run \
    timezone-setup \
    ${MYIR_DEFAULT_IMAGE_EXTRA_INSTALL} \
    packagegroup-arago-tisdk-sysrepo \
"

export IMAGE_BASENAME = "myir-image-full${ARAGO_IMAGE_SUFFIX}"

# Disable ubi/ubifs as the filesystem requires more space than is
# available on the HW.
IMAGE_FSTYPES:remove:omapl138 = "ubifs ubi"

DEVTOOLS = " \
    linux-libc-headers-dev \
    build-essential \
    packagegroup-core-tools-debug \
    git \
    dtc \
"

IMAGE_INSTALL += "\
    ${DEVTOOLS} \
    strace \
    ltrace \
    gdb \
    gdbserver \
    valgrind \
    iproute2 \
    evtest \
    memtester \
    ppp \
    fgl297-fw \
    docker \
"

# Platform-specific packages for MYIR boards
IMAGE_INSTALL:append:am62lxx = " mosquitto libmosquitto1 libmosquittopp1 mosquitto-clients mosquitto-dev"
IMAGE_INSTALL:append:am62xx = " ti-gst-plugins-source ti-gst-plugins-dev ti-gst-utils nxp-wlan-sdk"
IMAGE_INSTALL:append:am62pxx = " ti-gst-plugins-source ti-gst-plugins-dev ti-gst-utils"
IMAGE_INSTALL:remove:am62dxx = " packagegroup-arago-tisdk-graphics"
