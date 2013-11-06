package sk.hackcraft.multibox;

import java.util.Scanner;

import sk.hackcraft.multibox.model.Multimedia;
import sk.hackcraft.multibox.model.Player;
import sk.hackcraft.multibox.model.ServerPlayerShadow;
import sk.hackcraft.multibox.server.MockServerInterface;
import sk.hackcraft.util.ConsoleLog;
import sk.hackcraft.util.Log;
import sk.hackcraft.util.ManualEventLoop;
import sk.hackcraft.util.MessageQueue;
import sk.hackcraft.util.SimpleEventLoop;

public class TestApp
{
	public static void main(String[] args)
	{
		System.out.println("Go!");
		
		final Log log = new ConsoleLog();
		final ManualEventLoop eventLoop = new ManualEventLoop();
		final MockServerInterface server = new MockServerInterface(eventLoop);
		MockServerInterface.Controller controller = server.getController();
		
		eventLoop.post(new Runnable()
		{
			@Override
			public void run()
			{
				Player player = new ServerPlayerShadow(server);
				player.registerPlayerEventListener(new Player.PlayerEventListener()
				{
					@Override
					public void onPlayingStateChanged(boolean playing)
					{
						log.print("P: playing state changed: " + playing);
					}
					
					@Override
					public void onPlaybackPositionChanged(int newPosition)
					{
						log.print("P: position changed: " + newPosition);
					}

					@Override
					public void onMultimediaChanged(Multimedia newMultimedia)
					{
						log.print("P: multimedia changed: " + newMultimedia);
					}
				});

				player.init();
			}
		});
		eventLoop.processAllMessages();
		
		controller.addSong("Song 1", 10);
		eventLoop.processAllMessages();

		controller.addSong("Song 2", 8);
		eventLoop.processAllMessages();

		controller.addSong("Song 3", 11);
		eventLoop.processAllMessages();

		controller.setPlaybackPosition(4);
		eventLoop.processAllMessages();

		controller.finishSong();
		eventLoop.processAllMessages();
		
		controller.setPlaybackPosition(3);
		eventLoop.processAllMessages();

		controller.setPlaying(false);
		eventLoop.processAllMessages();

		controller.setPlaying(true);
		eventLoop.processAllMessages();

		controller.finishSong();
		eventLoop.processAllMessages();

		controller.finishSong();
		eventLoop.processAllMessages();

		controller.addRandomSong();
		eventLoop.processAllMessages();

		controller.addRandomSong();
		eventLoop.processAllMessages();

		controller.setPlaying(true);
		eventLoop.processAllMessages();

		controller.finishSong();
		eventLoop.processAllMessages();

		controller.finishSong();
		eventLoop.processAllMessages();
	}
}
