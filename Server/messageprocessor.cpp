#include "messageprocessor.h"

#include <QThread>

MessageProcessor::MessageProcessor(int socketDescriptor, QObject *parent) :
    QObject(parent)
{
    messanger = new SocketMessanger(socketDescriptor);

    connect(messanger, SIGNAL(incommingMessage()), this, SLOT(onMessageAvailable()));

    messanger->open();
}

MessageProcessor::~MessageProcessor()
{
    delete messanger;
}

void MessageProcessor::test()
{
    QByteArray data;
    data.append('a').append('s').append('d').append('f').append((char)0);

    DataMessage outmessage(45, data);
    messanger->writeMessage(outmessage);
    DataMessage outmessage1(46, data);
    messanger->writeMessage(outmessage1);

    QThread::sleep(1);

    DataMessage outmessage2(48, data);
    messanger->writeMessage(outmessage2);

    while(true)
    {
        DataMessage inmessage = messanger->waitForMessage();

        qDebug() << "reading";
        qDebug() << inmessage.getMessageType();
        qDebug() << inmessage.getByteArray().constData();
    }
}

void MessageProcessor::close()
{
    messanger->close();
}
