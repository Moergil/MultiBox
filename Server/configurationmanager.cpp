#include "configurationmanager.h"

#include <QFileInfo>
#include <QJsonArray>
#include <QJsonDocument>
#include <QJsonObject>
#include <QVariant>

const QString ConfigurationManager::CONFIG_FILENAME         = "MultiBoxConfig.json";

const qint16  ConfigurationManager::DEFAULT_SERVER_PORT     = 13110;
const QString ConfigurationManager::DEFAULT_SERVER_NAME     = "MultiBox Server";

const QString ConfigurationManager::SERVER_NAME_KEY         = "serverName";
const QString ConfigurationManager::SERVER_PORT_KEY         = "serverPort";
const QString ConfigurationManager::SERVER_LIBRARIES_KEY    = "libraries";


void ConfigurationManager::parse(QString content)
{
    QJsonDocument document = QJsonDocument::fromJson(content.toUtf8());

    if(!content.isEmpty())
    {
        QJsonObject object = document.object();
        serverPort = (qint16) object.value(SERVER_PORT_KEY).toDouble(DEFAULT_SERVER_PORT);
        serverName = object.value(SERVER_NAME_KEY).toString(DEFAULT_SERVER_NAME);

        QJsonArray array = object.value(SERVER_LIBRARIES_KEY).toArray();

        foreach(QJsonValue object, array)
        {
            libraries.insert(QFileInfo(object.toString()).absoluteFilePath());
        }
    }
}

QString ConfigurationManager::readContent()
{
    file.open(QIODevice::ReadOnly | QIODevice::Text);
    QString content = file.readAll();
    file.close();

    return content;
}

ConfigurationManager::ConfigurationManager(QObject *parent) :
    QObject(parent)
{
    file.setFileName(CONFIG_FILENAME);

    serverPort = DEFAULT_SERVER_PORT;
    serverName = DEFAULT_SERVER_NAME;

    if(file.exists())
    {
        QString content = readContent();
        parse(content);
    }
}

QSet<QString> ConfigurationManager::getLibraries() const
{
    return libraries;
}

qint16 ConfigurationManager::getServerPort() const
{
    return serverPort;
}

QString ConfigurationManager::getServerName() const
{
    return serverName;
}
