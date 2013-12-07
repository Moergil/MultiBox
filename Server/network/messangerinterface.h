#include "datamessage.h"
#include "messengerexception.h"

#include <QObject>

#pragma once

class MessengerInterface
{
public:
    virtual bool canWaitForMessage() = 0;
    virtual DataMessage waitForMessage() throw(MessengerException) = 0;
    virtual void writeMessage(DataMessage message) throw(MessengerException) = 0;
    virtual void close() = 0;
};
