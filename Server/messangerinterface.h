#include "datamessage.h"

#pragma once

class MessangerInterface
{
public:
    virtual void open() = 0;
    virtual DataMessage waitForMessage() = 0;
    virtual void writeMessage(DataMessage message) = 0;
    virtual void close() = 0;
};
