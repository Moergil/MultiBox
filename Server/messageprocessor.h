#pragma once

#include "message/abstractrequest.h"
#include "network/messangerinterface.h"

#include <QObject>

#include <message/messagerecognizer.h>

class MessageProcessor : public QObject
{
        Q_OBJECT

    private:
        MessengerInterface *messanger;
        MessageRecognizer *recognizer;

    public:
        MessageProcessor(PlayerHandler *handler, int socketDescriptor, QObject *parent = 0);

        void proccess();
};
