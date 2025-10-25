SUMMARY = "Arago TI SDK base image with test tools"

DESCRIPTION = "Arago SDK base image suitable for initramfs containing\
 comprehensive test tools."

require myir-image.inc

IMAGE_FSTYPES += "cpio.xz wic"

ARAGO_BASE_IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', 'ti-test', '', d)} \
    ${ARAGO_BASE_IMAGE_EXTRA_INSTALL} \
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
    tzdata \
    procps \
    ap6256-firmware \
    fac-burn-rt-nand-core \
"

export IMAGE_BASENAME = "myir-image-burn-rt-nand${ARAGO_IMAGE_SUFFIX}"
