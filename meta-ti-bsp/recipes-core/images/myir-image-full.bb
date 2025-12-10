SUMMARY = "Arago TI SDK base image with test tools"

DESCRIPTION = "Arago SDK base image suitable for initramfs containing\
 comprehensive test tools."

require myir-image.inc

inherit populate_sdk_qt6

IMAGE_FSTYPES += "cpio.xz wic"

ARAGO_BASE_IMAGE_EXTRA_INSTALL ?= ""

#GLIBC_GENERATE_LOCALES = "zh_CN.UTF-8 en_GB.UTF-8 en_US.UTF-8"
#IMAGE_LINGUAS ?= "zh-cn"

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
    libinput \
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
    sqlite3 \
    python3-pip \
    libmodbus \
    v4l-utils \
    rt-tests \
    stress-ng \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-good \
    pulseaudio \
    pulseaudio-server \
    pulseaudio-misc \
    pulseaudio-module-bluez5-discover \
    pulseaudio-module-bluez5-device \
    pulseaudio-service \
    tzdata \
    procps \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-emmc',  'fw-env-emmc',  '', d)} \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-rt-emmc',  'fw-env-emmc',  '', d)} \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-nand',  'fw-env-nand',  '', d)} \
    ${@oe.utils.conditional('MACHINE', 'myd-ym62lx-rt-nand',  'fw-env-nand',  '', d)} \
    ap6256-firmware \
    auto-run \
    qtbase \
    qtsvg \
    qtdeclarative \
    qtmultimedia \
    qtvirtualkeyboard \
    measy-aging-test \
    measy-listen-dev \
    myir-tool \
    hmi \
"

export IMAGE_BASENAME = "myir-image-full${ARAGO_IMAGE_SUFFIX}"
