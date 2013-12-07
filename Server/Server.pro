#-------------------------------------------------
#
# Project created by QtCreator 2013-11-07T15:46:55
#
#-------------------------------------------------

QT  += core
QT  += network
QT  += multimedia
QT  += sql

QT  -= gui

TARGET  = MultiBoxServer

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
    network/datamessage.cpp \
    util/bytearrayconverter.cpp \
    util/messagecontentreader.cpp \
    util/messagecontentwriter.cpp \
    network/datacontent.cpp \
    player/qtmultimediaplayer.cpp \
    player/playlist.cpp \
    player/player.cpp \
    player/library.cpp \
    message/pauserequest.cpp \
    test/client.cpp \
    player/entity/libraryitem.cpp \
    player/entity/multimedia.cpp \
    player/entity/directory.cpp \
    player/entity/playliststate.cpp \
    message/getplaylistresponse.cpp \
    message/getplaylistrequest.cpp \
    network/messengerexception.cpp \
    message/messagerecognizer.cpp \
    network/socketmessenger.cpp \
    player/library/librarydbmanager.cpp \
    player/entity/libraryitemtype.cpp \
    player/library/libraryitemfactorychooser.cpp \
    player/library/directoryscanner.cpp \
    player/library/writabledirectory.cpp \
    player/library/writablemultimedia.cpp \
    player/library/directoryfactory.cpp \
    player/library/multimediafactory.cpp \
    player/library/librarytablerow.cpp \
    message/getlibraryitemrequest.cpp \
    message/getlibraryitemresponse.cpp \
    message/addlibraryitemtoplaylistrequest.cpp \
    message/addlibraryitemtoplaylistresponse.cpp \
    message/getserverinforequest.cpp \
    message/getserverinforesponse.cpp

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
    network/datamessage.h \
    network/messangerinterface.h \
    util/bytearrayconverter.h \
    util/messagecontentreader.h \
    util/messagecontentwriter.h \
    network/datacontent.h \
    player/qtmultimediaplayer.h \
    player/playlist.h \
    player/player.h \
    player/library.h \
    message/pauserequest.h \
    test/client.h \
    message/messagerecognizer.h \
    player/entity/libraryitem.h \
    player/entity/multimedia.h \
    player/entity/directory.h \
    player/entity/qjsonexportableinterface.h \
    player/entity/playliststate.h \
    message/getplaylistresponse.h \
    message/getplaylistrequest.h \
    network/socketmessenger.h \
    network/messengerexception.h \
    player/library/librarydbmanager.h \
    player/entity/libraryitemtype.h \
    player/library/libraryitemfactorychooser.h \
    player/library/directoryscanner.h \
    player/library/libraryitemfactory.h \
    player/library/librarywritableinterface.h \
    player/library/writabledirectory.h \
    player/library/writablemultimedia.h \
    player/library/directoryfactory.h \
    player/library/multimediafactory.h \
    player/library/librarytablerow.h \
    message/getlibraryitemrequest.h \
    message/getlibraryitemresponse.h \
    message/addlibraryitemtoplaylistrequest.h \
    message/addlibraryitemtoplaylistresponse.h \
    message/getserverinforequest.h \
    message/getserverinforesponse.h
