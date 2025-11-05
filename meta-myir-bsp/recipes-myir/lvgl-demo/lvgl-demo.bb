SUMMARY = "LVGL Demo Application"
DESCRIPTION = "LVGL Demo"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PV = "0.1"
PR = "v1"

SRC_URI = "git://bgithub.xyz/lvgl/lvgl.git;protocol=https;branch=release/v8.2;name=lvgl;destsuffix=lvgl \
		   git://bgithub.xyz/lvgl/lv_drivers.git;protocol=https;branch=release/v8.2;name=lvdrivers;destsuffix=lv_drivers \
		"

SRCREV_FORMAT = "lvgl_lvdrivers"

SRCREV_lvgl = "fc98bf7f3cceb5b08a176520c0699b391628fe47"
SRCREV_lvdrivers = "49c4b178494625efefb07891d1c8b9c13914edff"

SRC_URI += "file://usr/bin/run_lvgl.sh \
	        file://run_lvgl.service \
			file://main.c \
			file://main-dri.c \
			file://main-wayland.c \
			file://Makefile \
			file://Makefile-wayland \
			file://Makefile-dri \
			file://mouse_cursor_icon.c \
			file://lv_conf.h \
			file://lv_drv_conf.h \
		"

S = "${WORKDIR}"

do_compile() {
    make
}

inherit systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -d ${D}${sbindir}
    
    install -m 755 ${S}/lvgl_demo ${D}${sbindir}/lvgl_demo
    install -m 755 ${WORKDIR}${bindir}/run_lvgl.sh ${D}${bindir}/run_lvgl.sh

    install -m 644 ${WORKDIR}/run_lvgl.service ${D}${systemd_system_unitdir}/run_lvgl.service
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "run_lvgl.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} = "${systemd_system_unitdir} \
              ${bindir} \
              ${sbindir} \
"

INSANE_SKIP:${PN} += "buildpaths ldflags installed-vs-shipped"
