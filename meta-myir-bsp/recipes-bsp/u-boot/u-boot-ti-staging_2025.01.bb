require u-boot-ti.inc

include ${@ 'recipes-bsp/u-boot/ti-extras.inc' if d.getVar('TI_EXTRAS') else ''}

PR = "r0"

BRANCH = "myir-new"

SRCREV = "241cf10d3a3c6a126067adc27e0f797a25669203"
