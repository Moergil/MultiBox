#pragma once

#include "user.h"

#include <QUrl>

class PlaylistItem
{
private:
    User *user;
    QUrl itemUrl;

    /*const qint64 id
    const QString name;
    const qint32 length;*/

public:
    PlaylistItem(QUrl itemUrl);

    QUrl getItemUrl();
};
