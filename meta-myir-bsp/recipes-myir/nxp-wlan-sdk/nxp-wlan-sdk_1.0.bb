require nxp-wlan-sdk.inc

SUMMARY = "NXP Wi-Fi SDK"

TARGET_CC_ARCH += "${LDFLAGS}"
INHIBIT_PACKAGE_STRIP = "1"

SRC_URI += " \
    file://wifi_mod_para.conf \
"

do_install () {
   install -d ${D}${datadir}/nxp
   install -d ${D}/lib/firmware/nxp
   install -m 066 ${WORKDIR}/wifi_mod_para.conf ${D}/lib/firmware/nxp/
   install -m 066 ${S}/script/load ${D}${datadir}/nxp/
   install -m 066 ${S}/script/unload ${D}${datadir}/nxp/
   install -m 066 ${S}/README_MLAN ${D}${datadir}/nxp/
   install -m 066 ${S}/mlan.ko ${D}${datadir}/nxp/
   install -m 066 ${S}/moal.ko ${D}${datadir}/nxp/
   
   #cp -rf ${WORKDIR}/wifi_mod_para.conf ${D}/lib/firmware/nxp/
   #cp -rf script/load ${D}${datadir}/nxp
   #cp -rf script/unload ${D}${datadir}/nxp
   #cp -rf README_MLAN ${D}${datadir}/nxp
   #cp *.ko ${D}${datadir}/nxp
}



FILES:${PN} = "${datadir}/nxp \
		/lib/firmware/nxp \
	"
RPROVIDES:${PN} += "kernel-module-mlan"
RPROVIDES:${PN} += "kernel-module-moal"


