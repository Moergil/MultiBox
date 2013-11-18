#include <QCoreApplication>
#include <QDataStream>
#include <QTcpSocket>
#include <QThread>

qint32 parseInt(QByteArray numberBuffer)
{
    qint32 number;

    QDataStream numberStream(numberBuffer);
    numberStream.setByteOrder(QDataStream::BigEndian);

    numberStream >> number;

    return number;
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    while(true)
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
    }

    return a.exec();
}
