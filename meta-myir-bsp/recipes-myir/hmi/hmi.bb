SUMMARY = "MYIR HMI Demo Experience"
DESCRIPTION = "Launcher for MYIR HMI Demo"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/git"





SRC_URI = "git://github.com/MYiR-Dev/mxapp.git;protocol=https;branch=hmi2.0-am62x-qt6 \
           file://usr/share/fonts/ttf/msyh.ttc \
           file://usr/bin/run_hmi.sh \
           file://run_hmi.service \
"

SRCREV = "2a45cf4308e01fb665d56ab08bd794e244be1fdd"
BRANCH = "hmi2.0-am62x-qt6"

inherit qt6-qmake
inherit systemd

DEPENDS += "qtdeclarative qtconnectivity qtgraphs qtsvg qtmultimedia "


RDEPENDS:${PN} += " \
    qtbase \
    qtdeclarative \
    qtconnectivity \
    qtgraphs \
    qtsvg \
    qtmultimedia \
    qtvirtualkeyboard \
    qtsvg-plugins \
"


do_install:append() {

    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -d ${D}${sbindir}
    install -d ${D}/usr/share/fonts/ttf/


    install -m 644 ${WORKDIR}/usr/share/fonts/ttf/msyh.ttc ${D}/usr/share/fonts/ttf/msyh.ttc
    

    if [ -f ${WORKDIR}/usr/bin/run_hmi.sh ]; then
        install -m 755 ${WORKDIR}/usr/bin/run_hmi.sh ${D}${bindir}/run_hmi.sh
    else
        bbwarn "run_hmi.sh not found in ${WORKDIR}/usr/bin/"
    fi


    install -m 644 ${WORKDIR}/run_hmi.service ${D}${systemd_system_unitdir}/run_hmi.service
    

    if [ -f ${D}/opt/mxapp2/bin/mxapp2 ]; then

        install -m 755 ${D}/opt/mxapp2/bin/mxapp2 ${D}${sbindir}/mxapp2
        bbnote "Copied mxapp2 from ${D}/opt/mxapp2/bin/mxapp2 to ${D}${sbindir}/mxapp2"

        rm -rf ${D}/opt/mxapp2

        if [ -d ${D}/opt ] && [ -z "$(ls -A ${D}/opt 2>/dev/null)" ]; then
            rmdir ${D}/opt 2>/dev/null || true
        fi
    elif [ -f ${B}/mxapp2 ]; then

        install -m 755 ${B}/mxapp2 ${D}${sbindir}/mxapp2
        bbnote "Installed mxapp2 from build directory ${B}"
    elif [ -f ${S}/mxapp2 ]; then

        install -m 755 ${S}/mxapp2 ${D}${sbindir}/mxapp2
        bbnote "Installed mxapp2 from source directory ${S}"
    else

        MXAPP2_FILE=$(find ${WORKDIR} -name "mxapp2" -type f 2>/dev/null | head -1)
        if [ -n "${MXAPP2_FILE}" ]; then
            install -m 755 ${MXAPP2_FILE} ${D}${sbindir}/mxapp2
            bbnote "Installed mxapp2 from ${MXAPP2_FILE}"
        else
            bbwarn "mxapp2 executable not found after build!"
        fi
    fi
}
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "run_hmi.service"
SYSTEMD_AUTO_ENABLE = "enable"



FILES:${PN} = "${systemd_system_unitdir} \
              /usr/share/fonts/ttf/ \
              ${bindir} \
              ${sbindir} \
"
INSANE_SKIP:${PN} = "file-rdeps"