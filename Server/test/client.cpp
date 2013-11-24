#include "client.h"

#include <QDataStream>

#include <network/socketmessanger.h>

#include <util/bytearrayconverter.h>

Client::Client(QObject *parent) :
    QThread(parent)
{
    connect(this, SIGNAL(finished()), this, SLOT(deleteLater()));
}

void Client::run()
{
    /*forever
    {
        QTcpSocket socket;

        socket.connectToHost("localhost", 13110);


        if(socket.waitForConnected())
        {
            qDebug() << "sending";

            while (socket.waitForReadyRead())
            {
                QByteArray array = socket.readAll();
                socket.write(array.constData(), array.length());

                qDebug() << socket.bytesToWrite();
                socket.flush();

                socket.waitForBytesWritten();
            }
        }

        qDebug() << "closing";
        socket.close();

        QThread::sleep(2);
    }*/

    QObject object;
    QThread::sleep(2);

    SocketMessanger *messanger = new SocketMessanger("localhost", 13110, &object);

    DataContent pauseContent(ByteArrayConverter::fromBool(false));
    DataMessage pauseMessage(7, pauseContent);
    messanger->writeMessage(pauseMessage);

    QThread::sleep(2);

    DataContent pauseContent2(ByteArrayConverter::fromBool(true));
    DataMessage pauseMessage2(7, pauseContent2);
    messanger->writeMessage(pauseMessage2);

    messanger->close();
}
