SUMMARY = "fac-burn-emmc-full configure files"
DESCRIPTION = "fac-burn-emmc-full files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd"
inherit systemd

SRC_URI = " \
		file://usr/lib/systemd/system/fac-burn-emmc-full.service \
		file://root/mfgimage/burn_emmc.sh \
		file://licenses/GPL-2 \
"

S = "${WORKDIR}"

dirs755= "/usr \
		  /usr/lib \
		  /usr/lib/systemd \
		  /usr/lib/systemd/system \
		  /root \
		  /root/mfgimage \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done

	install -m 0644 ${WORKDIR}/usr/lib/systemd/system/fac-burn-emmc-full.service ${D}/usr/lib/systemd/system 

	install -m 0755 ${WORKDIR}/root/mfgimage/burn_emmc.sh ${D}/root/mfgimage
	
	# Install boot files if they exist
	if [ -f ${DEPLOY_DIR_IMAGE}/tiboot3-am62x-gp-evm.bin ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/tiboot3-am62x-gp-evm.bin ${D}/root/mfgimage/tiboot3.bin
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/tispl.bin ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/tispl.bin ${D}/root/mfgimage
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/u-boot.img ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/u-boot.img ${D}/root/mfgimage
	fi
	
	# Install rootfs wic image from myd-am62x-emmc deploy directory
	if [ -f ${DEPLOY_DIR_IMAGE}/../myd-am62x-emmc/myir-image-full-myd-am62x-emmc.rootfs.wic ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/../myd-am62x-emmc/myir-image-full-myd-am62x-emmc.rootfs.wic ${D}/root/mfgimage/rootfs.wic
	else
		bbwarn "WIC image not found: ${DEPLOY_DIR_IMAGE}/../myd-am62x-emmc/myir-image-full-myd-am62x-emmc.rootfs.wic"
	fi
}

FILES:${PN} = "\
		/root \
		/root/mfgimage \
		/root/mfgimage/* \
		/usr \
		/usr/lib \
		/usr/lib/systemd \
		/usr/lib/systemd/system \
		/usr/lib/systemd/system/fac-burn-emmc-full.service \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "fac-burn-emmc-full.service"
SYSTEMD_AUTO_ENABLE = "enable"

