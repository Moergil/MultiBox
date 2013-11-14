#pragma once

#include "user.h"

#include <QUrl>

class PlaylistItem
{
private:
    User *user;
    QUrl itemUrl;

    /*const QString name;
    const QString artist;
    const QString album;
    const QString year;
    const qint32 length;*/

public:
    PlaylistItem(QUrl itemUrl);

    QUrl getItemUrl();
};
