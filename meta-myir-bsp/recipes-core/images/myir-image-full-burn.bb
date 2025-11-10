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

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    kernel-modules \
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
    packagegroup-tools-bluetooth \
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
    can-utils \
    microcom \
    serialcheck \
    util-linux \
    tar \
    gzip \
    bzip2 \
    bash \
    libinput \
    coreutils \
    ncurses \
    readline \
    grep \
    sed \
    gawk \
    vim \
    fbset \
    trace-cmd \
    sqlite3 \
    python3-pip \
    libmodbus \
    rt-tests \
    v4l-utils \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
    resize-rootfs \
    tzdata \
    procps \
    ppp \
    fgl297-fw \
    ppp-quectel \
    quectel-cm \
    fw-env-emmc \
    auto-run \
    weston \
    weston-init \
    weston-examples \
    qtbase \
    qtsvg \
    qtdeclarative \
    qtconnectivity \
    qtgraphs \
    qtmultimedia \
    qtvirtualkeyboard \
    lvgl-demo \
    hmi \
    myir-tool \
    timezone-setup \
    fac-burn-emmc-full \
    wpa-supplicant \
    ${MYIR_DEFAULT_IMAGE_EXTRA_INSTALL} \
"

export IMAGE_BASENAME = "myir-image-full-burn${ARAGO_IMAGE_SUFFIX}"

# Disable ubi/ubifs as the filesystem requires more space than is
# available on the HW.
IMAGE_FSTYPES:remove:omapl138 = "ubifs ubi"



IMAGE_INSTALL:remove = "docker"

