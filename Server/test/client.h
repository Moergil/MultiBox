#pragma once

#include <QTcpSocket>
#include <QThread>

#include <network/socketmessenger.h>

class Client : public QThread
{
        Q_OBJECT

    private:
        SocketMessenger *messenger;

    public:
        Client(QObject *parent = 0);

    protected:
        void run();

    private:
        void pauseTest();
        void playerStateTest();
        void playlistTest();
        void getLibraryItemTest(qint64 number);
        void addMultimediaToLibrary(qint64 number);
        void getPlayerInfoTest();
};
