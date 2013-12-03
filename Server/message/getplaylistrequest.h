#include "abstractrequest.h"

#include <QJsonDocument>

#pragma once

class GetPlayerStateRequest : public AbstractRequest
{
    Q_OBJECT

public:
    GetPlayerStateRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    bool canResponse() const;
    void execute();
};

