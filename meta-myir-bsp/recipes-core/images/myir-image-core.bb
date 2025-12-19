SUMMARY = "MYIR SDK base image with test tools"

DESCRIPTION = "MYIR SDK base image suitable for initramfs containing\
 comprehensive test tools."

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

IMAGE_FSTYPES += "cpio.xz"

# Set default timezone to Shanghai
DEFAULT_TIMEZONE = "Asia/Shanghai"

MYIR_BASE_IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', 'ti-test', '', d)} \
    kernel-modules \
    weston \
    weston-init \
    weston-examples \
    memtester \
    evtest \
    mmc-utils \
    alsa-utils \
    wpa-supplicant \
    hostapd \
    e2fsprogs \
    e2fsprogs-resize2fs \
    bluez-tools \
    bluez5 \
    libdrm \
    bc \
    iw \
    watchdog \
    k3conf \
    lsof \
    tslib \
    tslib-tests \
    ntp \
    gdb \
    zlib \
    libyaml \
    libubootenv \
    u-boot-fw-utils \
    u-boot-tools \
    mtd-utils \
    ethtool \
    net-tools \
    iptables \
    iproute2 \
    busybox \
    bridge-utils \
    tcpdump \
    i2c-tools \
    wpa-supplicant \
    can-utils \
    microcom \
    serialcheck \
    util-linux \
    e2fsprogs \
    tar \
    gzip \
    bzip2 \
    bash \
    coreutils \
    ncurses \
    readline \
    grep \
    sed \
    gawk \
    vim \
    libdrm \
    fbset \
    trace-cmd \
    valgrind \
    rt-tests \
    stress-ng \
    sqlite3 \
    python3-pip \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \ 
    tzdata \
    procps \
    ppp \
    fgl297-fw \
    ppp-quectel \
    quectel-cm \
    fw-env-emmc \
    myir-tool \
    auto-run \
    timezone-setup \
    psplash \
    pulseaudio \
    lvgl-demo \
    measy-listen-dev \
    wpa-supplicant \
    ${MYIR_BASE_IMAGE_EXTRA_INSTALL} \
"

export IMAGE_BASENAME = "myir-image-core${ARAGO_IMAGE_SUFFIX}"
