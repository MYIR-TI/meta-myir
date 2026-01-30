SUMMARY = "fac-burn-emmc-full-rt configure files"
DESCRIPTION = "fac-burn-emmc-full-rt files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd myir-image-full"
inherit systemd

# Always run do_install to pick up updated image files
do_install[nostamp] = "1"

SRC_URI = " \
		file://usr/lib/systemd/system/fac-burn-emmc-full-rt.service \
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

	install -m 0644 ${WORKDIR}/usr/lib/systemd/system/fac-burn-emmc-full-rt.service ${D}/usr/lib/systemd/system 

	install -m 0755 ${WORKDIR}/root/mfgimage/burn_emmc.sh ${D}/root/mfgimage
	
	# Install boot files from myd-ym62x-rt-emmc deploy directory if they exist
	if [ -f ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tiboot3-am62x-gp-evm.bin ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tiboot3-am62x-gp-evm.bin ${D}/root/mfgimage/tiboot3.bin
	else
		bbwarn "Boot file not found: ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tiboot3-am62x-gp-evm.bin"
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tispl.bin ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tispl.bin ${D}/root/mfgimage
	else
		bbwarn "Boot file not found: ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/tispl.bin"
	fi
	if [ -f ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/u-boot.img ]; then
		install -m 0755 ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/u-boot.img ${D}/root/mfgimage
	else
		bbwarn "Boot file not found: ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/u-boot.img"
	fi
	
	# Install rootfs wic image from myd-ym62x-rt-emmc deploy directory
	# Remove old file first to ensure it's updated
	if [ -f ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/myir-image-full-myd-ym62x-rt-emmc.rootfs.wic ]; then
		rm -f ${D}/root/mfgimage/rootfs.wic
		install -m 0755 ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/myir-image-full-myd-ym62x-rt-emmc.rootfs.wic ${D}/root/mfgimage/rootfs.wic
	else
		bbwarn "WIC image not found: ${DEPLOY_DIR_IMAGE}/../myd-ym62x-rt-emmc/myir-image-full-myd-ym62x-rt-emmc.rootfs.wic"
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
		/usr/lib/systemd/system/fac-burn-emmc-full-rt.service \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "fac-burn-emmc-full-rt.service"
SYSTEMD_AUTO_ENABLE = "enable"


