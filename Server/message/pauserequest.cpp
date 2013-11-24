#include "pauserequest.h"

#include <QDebug>

#include <util/messagecontentreader.h>

PauseRequest::PauseRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    playing = reader.readBool();
}

RequestRunnable *PauseRequest::getRunnable()
{
    return new PauseRequest::Runnable(this);
}

bool PauseRequest::isPlaying()
{
    return playing;
}


PauseRequest::Runnable::Runnable(AbstractRequest *request)
    : RequestRunnable(request)
{
}

void PauseRequest::Runnable::run()
{
    bool playing = ((PauseRequest *)getRequest())->isPlaying();

    emit getPlayerHandler()->setPlaying(playing);
}
