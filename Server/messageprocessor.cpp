#include "messageprocessor.h"
#include "network/socketmessenger.h"

#include <QThread>

#include <message/getplayerstaterequest.h>
#include <message/messagerecognizer.h>
#include <message/pauserequest.h>
#include <message/undefinedrequest.h>

#include <network/messengerexception.h>

MessageProcessor::MessageProcessor(PlayerHandler *handler, int socketDescriptor, QObject *parent)
    : QObject(parent)
{
    messanger = new SocketMessenger(socketDescriptor, this);
    recognizer = new MessageRecognizer(handler, this);
}

void MessageProcessor::proccess()
{
    try
    {
        while(messanger->canWaitForMessage())
        {
            DataMessage dataMessage = messanger->waitForMessage();

            AbstractRequest *request = recognizer->recognizeMessage(dataMessage);
            request->execute();

            if(request->canResponse())
            {
                AbstractResponse *response = request->getResponse();
                messanger->writeMessage(response->toDataMessage());
            }
        }
    }
    catch(MessengerException &exception)
    {
        qDebug() << exception.getMessage() << "Closing connection...";
    }

    messanger->close();
}

