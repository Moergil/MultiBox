#include "datamessage.h"
#include "bytearrayparser.h"
#include "connectionthread.h"
#include "socketmessanger.h"
#include "messageprocessor.h"

#include <QDataStream>
#include <QByteArray>

ConnectionThread::ConnectionThread(int socketDescriptor, QObject *parent)
   : QThread(parent), socketDescriptor(socketDescriptor)
{
}

void ConnectionThread::run()
{
    MessageProcessor processor(socketDescriptor);
    processor.test();
    //processor.close();
}

