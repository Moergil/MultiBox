#include "abstractrequest.h"

#pragma once

class PauseRequest : public AbstractRequest
{
private:
    bool playing;

public:
    PauseRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    bool isPlaying();
    bool canResponse() const;
    void execute();
};
