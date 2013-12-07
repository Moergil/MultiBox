#pragma once

#include "libraryitem.h"

#include <QFileInfo>
#include <QUrl>

class Multimedia : public LibraryItem
{
    public:
        static Multimedia createEmpty();

    private:
        const qint32 length;

    public:
        Multimedia(const qint64 id, const QString name, const qint32 length, const QFileInfo file, const qint64 parentId = 0);

        qint32 getLength() const;
        QUrl getQUrl() const;

        QJsonObject toQJsonObject() const;
};

