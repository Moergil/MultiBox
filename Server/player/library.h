#include <player/entity/libraryitem.h>

#include <player/library/librarydbmanager.h>

#include <QDir>
#include <QList>

#pragma once

class Library : public QObject
{
        Q_OBJECT

    private:
        LibraryDbManager *dbManager;

    public:
        Library(QObject *parent = 0);
        ~Library();

    public slots:
        void addDirectory(QDir &dir);
        QList<LibraryItem *> *listDirectories();
        void removeDirectory(QDir &dir);

        LibraryItem *readById(qint64 itemId);
        QList<LibraryItem *> *readAll();
        bool write(LibraryWritableInterface &item);
        bool exists(LibraryWritableInterface &item);
        void remove(LibraryItem *item);
        LibraryItem *readRootDir();
};
