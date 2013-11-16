#include "messageprocessor.h"
#include "network/socketmessanger.h"

#include <QThread>

#include <message/getplayerstaterequest.h>
#include <message/pauserequest.h>
#include <message/undefinedrequest.h>

#include <util/bytearrayconverter.h>

MessageProcessor::MessageProcessor(PlayerHandler *handler, int socketDescriptor, QObject *parent)
    : QObject(parent), handler(handler)
{
    messanger = new SocketMessanger(socketDescriptor, this);
}

AbstractRequest *MessageProcessor::recognizeRequest(DataMessage &dataMessage)
{
    switch(dataMessage.getMessageType())
    {
    case GetPlayerState:
        return new GetPlayerStateRequest(dataMessage, getPlayerHandler());

    /* case GetPlaylist :
        break;

    case GetLibraryDirectoryContent:
        break;

    case GetLibraryItemInfo:
        break;

    case AddItemToLibrary:
        break;

    case AddItemToPlaylist:
        break; */

    case Pause:
        return new PauseRequest(dataMessage, getPlayerHandler());

    default:
        return new UndefinedRequest(dataMessage, getPlayerHandler());
    }
}

PlayerHandler *MessageProcessor::getPlayerHandler()
{
    return handler;
}

void MessageProcessor::proccess()
{
    while(messanger->canWaitForMessage())
    {
        DataMessage dataMessage = messanger->waitForMessage();

        AbstractRequest *request = recognizeRequest(dataMessage);
        request->getRunnable()->run();
    }

    messanger->close();
}

