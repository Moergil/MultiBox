#include <QObject>

#include <network/datacontent.h>
#include <network/datamessage.h>

#pragma once

class AbstractResponse : public QObject
{
        Q_OBJECT

        public:
            explicit AbstractResponse(QObject *parent = 0);
            virtual DataContent toDataContent() const = 0;
            virtual qint32 getMessageCode() const = 0;

            DataMessage toDataMessage();
};

