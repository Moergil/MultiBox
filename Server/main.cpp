#include "configurationmanager.h"
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

    ConfigurationManager configurationManager;

    Player *player = new QtMultimediaPlayer();
    player->setPlayerName(configurationManager.getServerName());

    Server *server = new Server(player);

    if(server->listen(QHostAddress::Any, configurationManager.getServerPort()))
    {
        qDebug() << "Server is listening...";
    }
    else
    {
        qFatal("Server is already running!");
    }

    DirectoryScanner *directoryScanner = new DirectoryScanner(configurationManager.getLibraries(), player);
    directoryScanner->start();

    return a.exec();
}
