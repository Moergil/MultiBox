#include "getplayerstaterequest.h"
#include <QtDebug>

GetPlayerStateRequest::GetPlayerStateRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

RequestRunnable *GetPlayerStateRequest::getRunnable()
{
    return new GetPlayerStateRequest::Runnable(this);
}

GetPlayerStateRequest::Runnable::Runnable(AbstractRequest *request)
    : RequestRunnable(request)
{
}

void GetPlayerStateRequest::Runnable::run()
{
    qDebug() << "getPlaylist :-)";
}
