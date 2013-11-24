#include "abstractrequest.h"

#include <QJsonDocument>

#pragma once

class GetPlayerStateRequest : public AbstractRequest
{
    Q_OBJECT

public:
    class Runnable : public RequestRunnable
    {
    public:
        Runnable(AbstractRequest *request);
        void run();
    };

public:
    GetPlayerStateRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    RequestRunnable *getRunnable();
};

