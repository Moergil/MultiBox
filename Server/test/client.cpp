#include "client.h"

#include <QDataStream>

#include <network/socketmessenger.h>

#include <util/bytearrayconverter.h>
#include <util/messagecontentreader.h>

Client::Client(QObject *parent) :
    QThread(parent)
{
    connect(this, SIGNAL(finished()), this, SLOT(deleteLater()));
}

void Client::run()
{
    QObject object;

    QThread::sleep(2);

    messenger = new SocketMessenger("localhost", 13110, &object);

    try
    {
        //pauseTest();

        playerStateTest();

        playlistTest();

        QThread::sleep(3);

        playerStateTest();

        QThread::sleep(5);

        playerStateTest();
    }
    catch(MessengerException &exception)
    {
        qDebug() << exception.getMessage() << "Closing connection to server...";
    }


    messenger->close();
}

void Client::pauseTest()
{
    DataContent pauseContent(ByteArrayConverter::fromBool(false));
    DataMessage pauseMessage(7, pauseContent);
    messenger->writeMessage(pauseMessage);

    QThread::sleep(2);

    DataContent pauseContent2(ByteArrayConverter::fromBool(true));
    DataMessage pauseMessage2(7, pauseContent2);
    messenger->writeMessage(pauseMessage2);
}

void Client::playerStateTest()
{
    messenger->writeMessage(DataMessage(1));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
    qDebug() << reader.readQInt32();
    qDebug() << reader.readBool();
}

void Client::playlistTest()
{
    messenger->writeMessage(DataMessage(2));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}
