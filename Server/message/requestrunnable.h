#pragma once

#include <QRunnable>
#include <playerhandler.h>

class AbstractRequest;

class RequestRunnable : public QRunnable
{
private:
    AbstractRequest *request;

public:
    RequestRunnable(AbstractRequest *request);
    PlayerHandler *getPlayerHandler() const;
    AbstractRequest *getRequest() const;
    virtual void run() = 0;
};
