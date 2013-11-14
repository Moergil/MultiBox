#-------------------------------------------------
#
# Project created by QtCreator 2013-11-07T15:46:55
#
#-------------------------------------------------

QT       += core
QT       += network

QT       -= gui

TARGET = Server
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app


SOURCES += main.cpp \
    server.cpp \
    connectionthread.cpp \
    bytearrayparser.cpp \
    datamessage.cpp \
    messageprocessor.cpp \
    socketmessanger.cpp

HEADERS += \
    server.h \
    connectionthread.h \
    bytearrayparser.h \
    datamessage.h \
    messageprocessor.h \
    socketmessanger.h \
    messangerinterface.h
