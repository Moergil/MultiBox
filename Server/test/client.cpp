#include "client.h"

#include <QDataStream>

#include <network/socketmessenger.h>

#include <util/bytearrayconverter.h>
#include <util/messagecontentreader.h>
#include <util/messagecontentwriter.h>

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

        //playerStateTest();

        //playlistTest();

        /*for(int i=0; i<5; i++)
        {
            getLibraryItemTest(i);
        }*/

        /*playlistTest();
        addMultimediaToLibrary(759);
        addMultimediaToLibrary(760);
        addMultimediaToLibrary(761);
        addMultimediaToLibrary(762);
        addMultimediaToLibrary(763);
        addMultimediaToLibrary(764);
        playlistTest();*/

        //Thread::sleep(4);

        //playerStateTest();

        //getPlayerInfoTest();
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
}

void Client::playlistTest()
{
    messenger->writeMessage(DataMessage(2));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}

void Client::getLibraryItemTest(qint64 number)
{
    MessageContentWriter writer;
    QVariantMap map;
    map["itemId"] = number;
    writer.write(QJsonObject::fromVariantMap(map));

    messenger->writeMessage(DataMessage(3, writer.toDataContent()));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}

void Client::addMultimediaToLibrary(qint64 number)
{
    MessageContentWriter writer;
    QVariantMap map;
    map["multimediaId"] = number;
    writer.write(QJsonObject::fromVariantMap(map));

    messenger->writeMessage(DataMessage(4, writer.toDataContent()));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}

void Client::getPlayerInfoTest()
{
    messenger->writeMessage(DataMessage(5));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}
