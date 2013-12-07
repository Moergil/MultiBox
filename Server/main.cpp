#include "server.h"

#include <QCoreApplication>
#include <QDir>

#include <player/player.h>
#include <player/qtmultimediaplayer.h>

#include <test/client.h>

#include <player/library/directoryscanner.h>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    // START Client test
    Client *client = new Client();
    client->start();
    // END Client test

    Player *player = new QtMultimediaPlayer("");

    Server *server = new Server(player);

    if(server->listen(QHostAddress::Any, 13110))
    {
        qDebug() << "Server is listening...";
    }
    else
    {
        qFatal("Server is already running!");
    }

    DirectoryScanner *directoryScanner = new DirectoryScanner(player);
    directoryScanner->start();

    return a.exec();
}
