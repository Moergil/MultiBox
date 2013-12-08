#include "multimedia.h"

#include <QList>

#pragma once

class PlaylistState : public QJsonExportableInterface
{
    private:
        QList<Multimedia *> items;

    public:
        PlaylistState(QList<Multimedia *> items = QList<Multimedia *>());

        QList<Multimedia *> getItems() const;
        QJsonObject toQJsonObject() const;
};
