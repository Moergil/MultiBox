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

        playerStateTest();

        playlistTest();

        QThread::sleep(3);

        playerStateTest();

        QThread::sleep(5);

        playerStateTest();

        QThread::sleep(3);

        for(int i=1; i<10; i++)
        {
            getLibraryItemTest(i);
        }

        playlistTest();
        addMultimediaToLibrary(7);
        playlistTest();

        getPlayerInfoTest();
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

void Client::getLibraryItemTest(qint64 cislo)
{
    MessageContentWriter writer;
    writer.write(cislo);

    messenger->writeMessage(DataMessage(3, writer.toDataContent()));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQJsonObject();
}

void Client::addMultimediaToLibrary(qint64 cislo)
{
    MessageContentWriter writer;
    writer.write(cislo);

    messenger->writeMessage(DataMessage(4, writer.toDataContent()));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readBool();
    qDebug() << reader.readQJsonObject();
}

void Client::getPlayerInfoTest()
{
    messenger->writeMessage(DataMessage(5));
    DataMessage message = messenger->waitForMessage();

    MessageContentReader reader(message.getDataContent());

    qDebug() << reader.readQString();
}
