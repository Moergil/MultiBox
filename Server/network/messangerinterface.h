#include "datamessage.h"

#include <QObject>

#pragma once

class MessangerInterface
{
public:
    virtual bool canWaitForMessage() = 0;
    virtual DataMessage waitForMessage() = 0;
    virtual void writeMessage(DataMessage message) = 0;
    virtual void close() = 0;
};
