#pragma once

#include <QFile>
#include <QObject>
#include <QSet>

class ConfigurationManager : public QObject
{
        Q_OBJECT

    public:
        static const QString CONFIG_FILENAME;
        static const qint16 DEFAULT_SERVER_PORT;
        static const QString DEFAULT_SERVER_NAME;

        static const QString SERVER_NAME_KEY;
        static const QString SERVER_PORT_KEY;
        static const QString SERVER_LIBRARIES_KEY;

    private:
        QFile file;

        QSet<QString> libraries;
        qint16 serverPort;
        QString serverName;

        void create();
        void parse(QString content);

        QString readContent();

    public:
        ConfigurationManager(QObject *parent = 0);

        QSet<QString> getLibraries() const;
        qint16 getServerPort() const;
        QString getServerName() const;
};
