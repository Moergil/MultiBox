#include "playerhandler.h"

void PlayerHandler::setConnections()
{
    connect(this, SIGNAL(setPlaying(bool)), player, SLOT(setPlaying(bool)));
}

PlayerHandler::PlayerHandler(Player *player, QObject *parent)
    : QObject(parent), player(player)
{
    setConnections();
}
