SUMMARY = "Timezone configuration for MYIR boards"
DESCRIPTION = "Sets timezone to CST-8 (China Standard Time)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://timezone.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/timezone.sh ${D}${sysconfdir}/profile.d/
}

FILES:${PN} = "${sysconfdir}/profile.d/timezone.sh"

