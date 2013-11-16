#include "abstractrequest.h"

#pragma once

class UndefinedRequest : public AbstractRequest
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
    UndefinedRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    RequestRunnable *getRunnable();
};
