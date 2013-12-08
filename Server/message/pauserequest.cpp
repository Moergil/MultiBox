#include "pauserequest.h"

#include <QDebug>

#include <util/messagecontentreader.h>

PauseRequest::PauseRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    playing = reader.readBool();
}

bool PauseRequest::canResponse() const
{
    return false;
}

void PauseRequest::execute()
{
    emit getPlayerHandler()->setPlaying(playing);
}
