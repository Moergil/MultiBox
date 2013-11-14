#pragma once

#include "socketmessanger.h"

#include <QObject>

class MessageProcessor : public QObject
{
    Q_OBJECT

private:
    SocketMessanger *messanger;

public:
    MessageProcessor(int socketDescriptor, QObject *parent = 0);
    ~MessageProcessor();

    void test();
    void close();
};
