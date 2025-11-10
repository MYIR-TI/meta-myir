SUMMARY = "MEasyAgingTest"
DESCRIPTION = "measyagingtest app"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file:///workdir/MEasyAgingTest \
           file://measyagingtest.service \
           file://start.sh \
           file://update_sn.sh \
           "

S = "${WORKDIR}/MEasyAgingTest"

MEASYAGINGTEST_APP="MEasyTest-DEV"

## Specify the corresponding myir_core.c based on different boards
BOARD_MYIRCODE="myir_code.c"
MEASYAGINGTEST_CONFIG_AG_master = "MYD-YM62X-V10_agingconfig-maseter.json"
MEASYAGINGTEST_CONFIG_AG_slave = "MYD-YM62X-V10_agingconfig-slave.json"
MEASYAGINGTEST_CONFIG_POWRER  = "MYD-YM62X-V10_postconfig.json"

do_compile () {
	cp ${S}/myir_code/${BOARD_MYIRCODE}  ${S}/myir_code/myir_code.c  -rf

	make
}

SYSTEMD_AUTO_ENABLE_${PN} = "enable"

do_install (){
	install -m 0755 -d ${D}/usr/share/myir_testunit/
	install -m 0755 -d ${D}/usr/lib/

	install -m 0755 ${S}/${MEASYAGINGTEST_APP} ${D}/usr/share/myir_testunit/
	install -m 0755 ${S}/product_config/${MEASYAGINGTEST_CONFIG_AG_master} ${D}/usr/share/myir_testunit/
	install -m 0755 ${S}/product_config/${MEASYAGINGTEST_CONFIG_AG_slave} ${D}/usr/share/myir_testunit/
	install -m 0755 ${S}/product_config/${MEASYAGINGTEST_CONFIG_POWRER} ${D}/usr/share/myir_testunit/
	install -m 0755 ${S}/lib/libmyir_code.so ${D}/usr/lib/

	install -m 0755 ${WORKDIR}/start.sh ${D}/usr/share/myir_testunit/
	install -m 0755 ${WORKDIR}/update_sn.sh ${D}/usr/share/myir_testunit/
}

do_install:append() {
    install -d ${D}${bindir}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/measyagingtest.service ${D}${systemd_system_unitdir}/
}


FILES:${PN} = " ${sysconfdir}/ \
				${systemd_system_unitdir} \
				/usr/bin/ \
				/usr/lib/* \
			    /usr/share/myir_testunit/ \
              "

ALLOW_EMPTY:${PN} = "1"

#For dev packages only
INSANE_SKIP:${PN}-dev = "ldflags"
INSANE_SKIP:${PN} = "${ERROR_QA} ${WARN_QA}"

INSANE_SKIP_${PN} += "libdir"
INSANE_SKIP_${PN}-dbg += "libdir"
PACKAGE_DEBUG_SPLIT_STYLE = "debug-with-srcpkg"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
