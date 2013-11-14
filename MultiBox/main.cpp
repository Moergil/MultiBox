#include "player.h"
#include "qtmultimediaplayer.h"

#include <QCoreApplication>
#include <QMediaPlayer>
#include <QUrl>

//#include <stdio.h>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    Player *player = new QtMultimediaPlayer();

    player->start();

    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/1.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/2.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/3.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/4.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/5.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/6.mp3")));
    player->getPlaylist()->addItem(new PlaylistItem(QUrl::fromLocalFile("/home/joso/playlist/7.mp3")));

    /*char key = '0';

    while(key != 'q')
    {
        key = getchar();

        switch(key)
        {
        case 's':
            qDebug() << player->getPlaylist()->count();
            break;

        case 'p':
            player->pause();
            break;

        case 'r':
            player->resume();
            break;
        }
    }*/

    return a.exec();
}
