#include "librarydbmanager.h"
#include "librarytablerow.h"

#include <QFile>
#include <QSqlQuery>
#include <QVariant>
#include <QDebug>

const QString LibraryDbManager::DB_FILENAME = "MultiboxLibrary.sqlite";

bool LibraryDbManager::tableExists()
{
    bool exists = false;

    if(db.isOpen())
    {
        QSqlQuery query("SELECT COUNT(*) AS rows "
                        "FROM sqlite_master WHERE type='table' AND name='library';", db);

        if(query.next())
        {
            if(query.value(0).toInt() > 0)
            {
                exists = true;
            }
        }
    }

    return exists;
}

void LibraryDbManager::createTable()
{
    if(db.isOpen())
    {
        QSqlQuery query(db);

        if (query.exec("CREATE TABLE library ( "
                       "id INTEGER PRIMARY KEY NOT NULL, "
                       "name VARCHAR(250) NOT NULL, "
                       "type INTEGER NOT NULL, "
                       "length INTEGER, "
                       "path VARCHAR(250) NOT NULL UNIQUE, "
                       "parent INTEGER, "
                       "FOREIGN KEY(parent) REFERENCES library(id) ON DELETE CASCADE"
                       ");"))
        {
           qDebug() << "Library table created.";
        }
        else
        {
           qWarning() << "Problem with creating library table!" << query.lastError();
        }
    }
}

LibraryDbManager::LibraryDbManager(QObject *parent)
    : QObject(parent)
{
    db = QSqlDatabase::addDatabase("QSQLITE");
}

bool LibraryDbManager::open()
{
    db.setDatabaseName(DB_FILENAME);

    bool open = db.open();

    if(!tableExists())
    {
        createTable();
    }

    return open;
}

LibraryItem *LibraryDbManager::readById(qint64 id)
{
    LibraryTableRow *row = readTableRow(id);

    if(row != NULL)
    {
        row->setDescendants(readTableRowsByParent(id));

        LibraryItemFactory *factory = LibraryItemFactoryChooser::getFactory(row->getType());
        return factory->getLibraryItem(row);
    }
    else
    {
        return NULL;
    }
}

LibraryTableRow *LibraryDbManager::readTableRow(qint64 id)
{
    QSqlQuery query(QString("SELECT id, name, type, length, path, parent FROM library WHERE id = %1").arg(id), db);
    return readTableRowFromQuery(query);
}

LibraryTableRow *LibraryDbManager::readTableRowFromQuery(QSqlQuery &query)
{
    LibraryTableRow *tableRow = NULL;

    if (query.next())
    {
        tableRow = new LibraryTableRow();

        tableRow->setId(query.value(0).toLongLong());
        tableRow->setName(query.value(1).toString());
        tableRow->setType(query.value(2).toInt());
        tableRow->setLength(query.value(3).toInt());
        tableRow->setPath(query.value(4).toString());
        tableRow->setParent(query.value(5).toInt());
    }

    return tableRow;
}

QList<LibraryTableRow *> LibraryDbManager::readTableRowsByParent(qint64 parentId)
{
    QSqlQuery query(QString("SELECT id, name, type, length, path, parent FROM library "
                            "WHERE parent = %1 OR (0 = %1 AND parent IS NULL)")
                    .arg(parentId)
                    , db);

    QList<LibraryTableRow *> list;
    LibraryTableRow *item;

    while((item = readTableRowFromQuery(query)) != NULL)
    {
        list.append(item);
    }

    return list;
}

qint64 LibraryDbManager::write(LibraryWritableInterface &item)
{
    qint16 newId = -1;

    if(db.isOpen())
    {
        QSqlQuery query(db);

        if (query.exec(item.getInsertString()))
        {
            newId = query.lastInsertId().toLongLong();
            qDebug() << "Item added" << item.getIdentifier();
        }
        else
        {
            qDebug() << "Item not added" << db.lastError();
            qDebug() << item.getInsertString();
        }

    }

    return newId;
}

void LibraryDbManager::remove(LibraryItem *item)
{
    if(db.isOpen())
    {
        QSqlQuery query(db);
        if(query.exec(QString("DELETE FROM library WHERE id = %1").arg(item->getId())))
        {
            qDebug() << "Item deleted" << item->getName();
        }
        else
        {
            qDebug() << "Item not deleted" << db.lastError();
        }
    }
}

void LibraryDbManager::close()
{
    db.close();
}

bool LibraryDbManager::exists(LibraryWritableInterface &item)
{
    bool exists = false;

    if(db.isOpen())
    {
        QSqlQuery query(QString("SELECT COUNT(*) AS rows "
                                "FROM library WHERE path = '%1';")
                        .arg(item.getIdentifier().replace("'", "''"))
                        , db);

        if(query.next())
        {
            if(query.value(0).toInt() > 0)
            {
                exists = true;
            }
        }
    }

    return exists;
}

QList<LibraryItem *> *LibraryDbManager::readAll()
{
    QSqlQuery query(QString("SELECT id, name, type, length, path, parent FROM library"), db);

    QList<LibraryItem *> *list = new QList<LibraryItem *>();

    LibraryTableRow *item;

    while((item = readTableRowFromQuery(query)) != NULL)
    {
        LibraryItemFactory *factory = LibraryItemFactoryChooser::getFactory(item->getType());
        list->append(factory->getLibraryItem(item));
    }

    return list;
}

QList<LibraryItem *> *LibraryDbManager::readByParentId(qint64 parentId)
{
    QList<LibraryItem *> *list = new QList<LibraryItem *>();

    foreach(LibraryTableRow *item, readTableRowsByParent(parentId))
    {
        LibraryItemFactory *factory = LibraryItemFactoryChooser::getFactory(item->getType());
        list->append(factory->getLibraryItem(item));
    }

    return list;
}
