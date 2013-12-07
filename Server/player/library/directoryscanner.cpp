#include "directoryfactory.h"
#include "directoryscanner.h"
#include "multimediafactory.h"
#include "writabledirectory.h"
#include "writablemultimedia.h"

#include <QDebug>

void DirectoryScanner::scanDirectory()
{
    qDebug() << "Scanning library...";

    fixExistingFiles();
    findNewFiles();
}

void DirectoryScanner::fixExistingFiles()
{
    QList<LibraryItem *> *list;

    QMetaObject::invokeMethod(player->getLibrary(), "readAll",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(QList<LibraryItem *> *, list));

    foreach(LibraryItem *item, *list)
    {
        if(!item->getFileInfo().exists())
        {
            QMetaObject::invokeMethod(player->getLibrary(), "remove",
                                      Qt::BlockingQueuedConnection,
                                      Q_ARG(LibraryItem *, item));
        }
    }
}

void DirectoryScanner::findNewFiles()
{
    /*
    foreach(LibraryItem *item, manager->getRootDirs())
    {
        proccessPath(QDir(item->getPath());
    }
    */

    QDir dir("/home/joso/Home/Music");
    handleDirectory(dir);
    proccessPath(dir);
}

void DirectoryScanner::proccessPath(QDir dir)
{
    proccessDirectories(dir);
    proccessFiles(dir);
}

void DirectoryScanner::proccessDirectories(QDir dir)
{
    dir.setFilter(QDir::Dirs);
    QStringList directories = dir.entryList();

    foreach(QString directory, directories)
    {
        if(directory != tr(".") && directory != tr(".."))
        {
            QDir currentDir(dir.path() + QDir::separator() + directory);
            handleDirectory(currentDir);
            proccessPath(currentDir);
        }
    }
}

void DirectoryScanner::proccessFiles(QDir dir)
{
    dir.setFilter(QDir::Files);
    QStringList files = dir.entryList();

    foreach(QString file, files)
    {
        QFile qFile(dir.path() + QDir::separator() + file);
        handleFile(qFile);
    }
}

void DirectoryScanner::handleFile(QFileInfo fileInfo)
{
    if(allowedTypes.contains(fileInfo.suffix().toLower()))
    {
        handleMusicFile(fileInfo);
    }
}

void DirectoryScanner::handleMusicFile(QFileInfo fileInfo)
{
    MultimediaFactory *factory = MultimediaFactory::getFactory();
    WritableMultimedia *multimedia = (WritableMultimedia *)factory->getLibraryItem(fileInfo);

    QMetaObject::invokeMethod(player->getLibrary(), "write",
                              Qt::BlockingQueuedConnection,
                              Q_ARG(LibraryWritableInterface &, *multimedia));

}

void DirectoryScanner::handleDirectory(QDir dir)
{
    QFileInfo info(dir.absolutePath());

    DirectoryFactory *factory = DirectoryFactory::getFactory();
    WritableDirectory *directory = (WritableDirectory *)factory->getLibraryItem(info);

    QMetaObject::invokeMethod(player->getLibrary(), "write",
                              Qt::BlockingQueuedConnection,
                              Q_ARG(LibraryWritableInterface &, *directory));
}

DirectoryScanner::DirectoryScanner(Player *player)
    : player(player)
{
    allowedTypes << "mp3" << "wma";
}

void DirectoryScanner::run()
{
    forever
    {
        scanDirectory();

        QThread::sleep(60);
    }
}
