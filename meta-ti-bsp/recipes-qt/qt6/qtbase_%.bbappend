PACKAGECONFIG_GL = "gles2 eglfs linuxfb"

PACKAGECONFIG += " \
    cups \
    fontconfig \
    getentropy \
    gif \
    glib \
    harfbuzz \
    ico \
    icu \
    libinput \
    sql-sqlite \
    tslib \
    xkbcommon \
    gbm \
    kms \
    examples \
    accessibility \
    linuxfb \
    "

QT_QPA_DEFAULT_PLATFORM = "linuxfb"
