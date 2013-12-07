#include "libraryitemfactorychooser.h"
#include "librarywritableinterface.h"

#include <QSqlDatabase>
#include <QSqlError>

#include <player/entity/libraryitem.h>

#pragma once

class LibraryDbManager : public QObject
{
        Q_OBJECT

    public:
        static const QString DB_FILENAME;

    private:
        QSqlDatabase db;

        bool tableExists();
        void createTable();

        LibraryTableRow *readTableRow(qint64 id);
        LibraryTableRow *readTableRowFromQuery(QSqlQuery &query);
        QList<LibraryTableRow *> readTableRowsByParent(qint64 parentId);

    public:
        LibraryDbManager(QObject *parent = 0);

        bool open();
        void close();

        LibraryItem *readById(qint64 id);
        QList<LibraryItem *> *readAll();
        QList<LibraryItem *> *readByParentId(qint64 parentId);
        qint64 write(LibraryWritableInterface &item);
        bool exists(LibraryWritableInterface &item);
        void remove(LibraryItem *item);
};

