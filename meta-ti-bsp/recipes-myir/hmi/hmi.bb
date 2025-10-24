SUMMARY = "MYIR HMI Demo Experience"
DESCRIPTION = "Launcher for MYIR HMI Demo"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/git"

SRC_URI= "git://github.com/MYiR-Dev/mxapp.git;protocol=https;branch=MXAPP-QT6 \
        file://usr/share/fonts/ttf/msyh.ttc \
        file://usr/bin/run_hmi.sh \
        file://run_hmi.service \
"

SRCREV = "c61e58655402003292b05edbf8c2415eff4420ba"

inherit qt6-qmake
inherit systemd

DEPENDS += "qtdeclarative qtconnectivity qtgraphs qtsvg qtmultimedia "
#RDEPENDS_${PN} += "bash qtsvg-plugins"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -d ${D}${sbindir}
    install -d ${D}/usr/share/fonts/ttf/
    install -d ${D}/usr/share/myir/

    install -m 755 ${S}/mxapp2 ${D}${sbindir}/mxapp2
    install -m 755 ${WORKDIR}/usr/share/fonts/ttf/msyh.ttc ${D}/usr/share/fonts/ttf/msyh.ttc
    install -m 755 ${WORKDIR}${bindir}/run_hmi.sh ${D}${bindir}/run_hmi.sh
    install -m 644 ${WORKDIR}/run_hmi.service ${D}${systemd_system_unitdir}/run_hmi.service
    install -m 644 ${S}/ecg/ecg.dat ${D}/usr/share/myir/
    install -m 644 ${S}/ecg/resp.text ${D}/usr/share/myir/
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "run_hmi.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} = "${systemd_system_unitdir} \
                /usr/share/fonts/ttf/ \
                ${bindir} \
                ${sbindir} \
                /usr/share/myir \
"
INSANE_SKIP:${PN} = "file-rdeps"
