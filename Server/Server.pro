#-------------------------------------------------
#
# Project created by QtCreator 2013-11-07T15:46:55
#
#-------------------------------------------------

QT  += core
QT  += network
QT  += multimedia

QT  -= gui

TARGET  = Server

CONFIG  += console
CONFIG  -= app_bundle
CONFIG  += multimedia

MOBILITY    = multimedia

TEMPLATE    = app

INCLUDEPATH += .
RESOURCES += resources.qrc

SOURCES += main.cpp \
    server.cpp \
    connectionthread.cpp \
    messageprocessor.cpp \
    playerhandler.cpp \
    message/abstractrequest.cpp \
    message/abstractresponse.cpp \
    message/getplayerstaterequest.cpp \
    message/undefinedrequest.cpp \
    message/getplayerstateresponse.cpp \
    network/socketmessanger.cpp \
    network/datamessage.cpp \
    util/bytearrayconverter.cpp \
    util/messagecontentreader.cpp \
    util/messagecontentwriter.cpp \
    network/datacontent.cpp \
    player/youtubeitem.cpp \
    player/user.cpp \
    player/songfile.cpp \
    player/qtmultimediaplayer.cpp \
    player/playlistitem.cpp \
    player/playlist.cpp \
    player/player.cpp \
    player/library.cpp \
    message/pauserequest.cpp \
    message/requestrunnable.cpp \
    test/client.cpp \
    network/messangerexception.cpp

HEADERS += \
    server.h \
    connectionthread.h \
    messageprocessor.h \
    playerhandler.h \
    message/abstractrequest.h \
    message/abstractresponse.h \
    message/getplayerstaterequest.h \
    message/undefinedrequest.h \
    message/getplayerstateresponse.h \
    network/socketmessanger.h \
    network/datamessage.h \
    network/messangerinterface.h \
    util/bytearrayconverter.h \
    util/messagecontentreader.h \
    util/messagecontentwriter.h \
    network/datacontent.h \
    player/youtubeitem.h \
    player/user.h \
    player/songfile.h \
    player/qtmultimediaplayer.h \
    player/playlistitem.h \
    player/playlist.h \
    player/player.h \
    player/library.h \
    message/pauserequest.h \
    message/requestrunnable.h \
    test/client.h \
    network/messangerexception.h
