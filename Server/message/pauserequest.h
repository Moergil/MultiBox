#include "abstractrequest.h"

#pragma once

class PauseRequest : public AbstractRequest
{
public:
    class Runnable : public RequestRunnable
    {
    public:
        Runnable(AbstractRequest *request);

        void run();
    };

private:
    bool playing;

public:
    PauseRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    RequestRunnable *getRunnable();

    bool isPlaying();
};
