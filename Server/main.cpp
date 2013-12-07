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



    Player *player = new QtMultimediaPlayer();

    DirectoryScanner *directoryScanner = new DirectoryScanner(player);
    directoryScanner->start();

    player->getPlaylist()->addItem(new Multimedia(1,    "Prva",     156,    QFileInfo(QDir::currentPath()+"/playlist/1.mp3")));
    player->getPlaylist()->addItem(new Multimedia(2,    "Druha",    1564,   QFileInfo(QDir::currentPath()+"/playlist/2.mp3")));
    player->getPlaylist()->addItem(new Multimedia(3,    "atretia",  4655,   QFileInfo(QDir::currentPath()+"/playlist/3.mp3")));
    player->getPlaylist()->addItem(new Multimedia(4,    "stvrta",   756,    QFileInfo(QDir::currentPath()+"/playlist/4.mp3")));
    player->getPlaylist()->addItem(new Multimedia(5,    "piata",    456,    QFileInfo(QDir::currentPath()+"/playlist/5.mp3")));
    player->getPlaylist()->addItem(new Multimedia(6,    "siesta",   456,    QFileInfo(QDir::currentPath()+"/playlist/6.mp3")));

    Server *server = new Server(player);

    if(server->listen(QHostAddress::Any, 13110))
    {
        qDebug() << "Server is listening...";
    }
    else
    {
        qFatal("Server is already running!");
    }

    return a.exec();
}
