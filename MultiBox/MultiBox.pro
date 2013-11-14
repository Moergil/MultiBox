#-------------------------------------------------
#
# Project created by QtCreator 2013-11-04T18:25:58
#
#-------------------------------------------------

QT       += core
QT       += multimedia


TARGET = MultiBox
TEMPLATE = app

CONFIG += mobility
MOBILITY = multimedia

SOURCES += main.cpp \
    playlist.cpp \
    player.cpp \
    playlistitem.cpp \
    songfile.cpp \
    youtubeitem.cpp \
    library.cpp \
    user.cpp \
    qtmultimediaplayer.cpp

HEADERS += \
    playlist.h \
    player.h \
    playlistitem.h \
    songfile.h \
    youtubeitem.h \
    library.h \
    user.h \
    qtmultimediaplayer.h
