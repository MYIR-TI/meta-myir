SUMMARY = "MEasyListen-DEV"
DESCRIPTION = "measy-listen-dev app"

LICENSE = "LGPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/LGPL-2.0-only;md5=9427b8ccf5cf3df47c29110424c9641a"
PV = "0.1"
PR = "v1"

SRC_URI = "file://MEasyListen-DEV \
	   file://libmyir_code.so \
"

S = "${WORKDIR}"

MEASYAGINGTEST_APP="MEasyListen-DEV"

dirs755= "/usr \
          /usr/share \
      	  /usr/share/myir_testunit \
          /usr/lib \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done

	install -m 0755 ${S}/MEasyListen-DEV ${D}/usr/share/myir_testunit/
	install -m 0755 ${S}/libmyir_code.so ${D}/usr/lib/
}

FILES:${PN} = "\
		/usr \
		/usr/share \
		/usr/share/myir_testunit \
		/usr/share/myir_testunit/MEasyListen-DEV \
		/usr/lib \
		/usr/lib/libmyir_code.so \
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
