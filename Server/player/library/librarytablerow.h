#include <QSqlQuery>
#include <QString>
#include <QList>

#pragma once

class LibraryTableRow
{
    private:
        qint64 id;
        QString name;
        qint32 type;
        qint32 length;
        QString path;
        qint64 parent;
        QList<LibraryTableRow *> descendants;

    public:
        LibraryTableRow();
        void setId(qint64 id);
        void setName(QString name);
        void setType(qint32 type);
        void setLength(qint32 length);
        void setPath(QString path);
        void setParent(qint64 parent);
        qint64 getId() const;
        QString getName() const;
        qint32 getType() const;
        qint32 getLength() const;
        QString getPath() const;
        qint64 getParent() const;
        QList<LibraryTableRow *> getDescendants() const;
        void setDescendants(const QList<LibraryTableRow *> &value);
};
