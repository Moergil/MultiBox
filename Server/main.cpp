#include "server.h"

#include <QCoreApplication>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    Server server;
    server.serverPort();
    server.listen(QHostAddress::Any, 13110);

    return a.exec();
}
