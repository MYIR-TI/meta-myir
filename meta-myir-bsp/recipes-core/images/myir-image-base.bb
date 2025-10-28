SUMMARY = "MYIR SDK base image with test tools"

DESCRIPTION = "MYIR SDK base image suitable for initramfs containing\
 comprehensive test tools."

LICENSE = "MIT"

COMPATIBLE_MACHINE = "ti-soc"

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

IMAGE_FSTYPES += "cpio.xz"

# Set default timezone to Shanghai
DEFAULT_TIMEZONE = "Asia/Shanghai"

MYIR_BASE_IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    packagegroup-core-full-cmdline \
    kernel-modules \
    u-boot-fw-utils \
    bluez5 \
    packagegroup-tools-bluetooth \
    resize-rootfs \
    packagegroup-arago-gst \
    weston \
    weston-init \
    weston-examples \
    v4l-utils \
    serialcheck \
    ethtool \
    net-tools \
    iptables \
    iperf3 \
    iproute2 \
    busybox \
    bridge-utils \
    tcpdump \
    tslib \
    tslib-tests \
    strace \
    ltrace \
    gdb \
    gdbserver \
    e2fsprogs \
    valgrind \
    evtest \
    memtester \
    ppp \
    fgl297-fw \
    ppp-quectel \
    auto-run \
    timezone-setup \
    ${MYIR_BASE_IMAGE_EXTRA_INSTALL} \
"

export IMAGE_BASENAME = "myir-image-base${ARAGO_IMAGE_SUFFIX}"