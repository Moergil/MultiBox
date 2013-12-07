#include "librarydbmanager.h"

#include <QThread>
#include <QDebug>
#include <QDir>

#include <player/player.h>

#pragma once

class DirectoryScanner : public QThread
{
    private:
        QSet<QString> allowedTypes;
        Player *player;

        void scanDirectory();
        void fixExistingFiles();
        void findNewFiles();

        void proccessPath(QDir dir);
        void handleFile(QFileInfo fileInfo);
        void handleMusicFile(QFileInfo fileInfo);
        void handleDirectory(QDir dir);

        void proccessDirectories(QDir dir);
        void proccessFiles(QDir dir);

        qint64 getParentDirectoryId(QFileInfo fileInfo);

    public:
        DirectoryScanner(Player *player);

    protected:
        void run();
};
