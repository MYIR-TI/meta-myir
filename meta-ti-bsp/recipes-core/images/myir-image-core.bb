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
    rt-tests \
    sqlite3 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \ 
    tzdata \
    procps \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-emmc',  'fw-env-emmc',  '', d)} \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-nand',  'fw-env-nand',  '', d)} \
    ap6256-firmware \
    auto-run \
    myir-tool \
    lvgl \
"
export IMAGE_BASENAME = "myir-image-core${ARAGO_IMAGE_SUFFIX}"
