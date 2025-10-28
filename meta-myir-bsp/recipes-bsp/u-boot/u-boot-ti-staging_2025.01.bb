require u-boot-ti.inc

include ${@ 'recipes-bsp/u-boot/ti-extras.inc' if d.getVar('TI_EXTRAS') else ''}

PR = "r0"

BRANCH = "myir-new"

SRCREV = "4fc1e6cf861d552361b1d38e4252d89c3da2c742"
