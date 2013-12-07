#include "abstractrequest.h"

#include <QJsonDocument>

#pragma once

class GetPlaylistRequest : public AbstractRequest
{
        Q_OBJECT

    public:
        GetPlaylistRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        bool canResponse() const;
        void execute();
};

