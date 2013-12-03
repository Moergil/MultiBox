#pragma once

#include <QObject>

#include <network/datacontent.h>
#include <network/datamessage.h>

class AbstractResponse : public QObject
{
    Q_OBJECT

    public:
        explicit AbstractResponse(QObject *parent = 0);
        virtual DataContent toDataContent() = 0;
        virtual qint32 getMessageCode() = 0;

        DataMessage toDataMessage();
};

