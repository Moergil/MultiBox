#include "network/datamessage.h"
#include "playerhandler.h"
#include "requestrunnable.h"

#include <QObject>

#pragma once

class AbstractRequest : public QObject
{
    Q_OBJECT

private:
    DataMessage dataMessage;
    PlayerHandler *handler;

public:
    AbstractRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    DataMessage &getDataMessage();
    PlayerHandler *getPlayerHandler();

    virtual RequestRunnable *getRunnable() = 0;
};
