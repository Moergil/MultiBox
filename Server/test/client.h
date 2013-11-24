#pragma once

#include <QTcpSocket>
#include <QThread>

class Client : public QThread
{
    Q_OBJECT
public:
    Client(QObject *parent = 0);

protected:
    void run();
    qint32 parseInt(QByteArray numberBuffer);
};
