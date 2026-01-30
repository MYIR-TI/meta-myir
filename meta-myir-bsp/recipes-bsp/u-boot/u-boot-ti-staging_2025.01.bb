require u-boot-ti.inc

include ${@ 'recipes-bsp/u-boot/ti-extras.inc' if d.getVar('TI_EXTRAS') else ''}

PR = "r0"

# Use MYIR u-boot repo/branch, override defaults from meta-ti u-boot-ti.inc
UBOOT_GIT_URI = "git://github.com/MYIR-TI/myir-ti-uboot.git"
UBOOT_GIT_PROTOCOL = "https"

BRANCH = "develop-ym62x-u-boot-2025.01_V11.01.05.03"

SRCREV = "18100bc4ee84fa4a323eab6916b6abd394a0e38b"
