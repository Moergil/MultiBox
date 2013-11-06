package sk.hackcraft.netinterface;


public interface ServerInterface
{
	public void setEventListener(EventListener listener);
	
	public void connect();
	public void close();

	public void returnParticle(int particleId, int destinationChamberId);
	
	public interface EventListener
	{
		public void onDisconnect();
		
		public void onChamberIdReceived(int chamberId);
		public void onNeighborChamberConnected(int chamberId);
		public void onNeighborChamberDisconnected(int chamberId);
		
		public void onGameStart();
		public void onGameEnd();
		
		public void onParticleSpawned(int particleId, double speed);
		public void onParticleReceived(int particleId, double speed, int sourceChamberId);
	}
}
