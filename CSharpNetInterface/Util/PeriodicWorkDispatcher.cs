using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Util
{
	public class PeriodicWorkDispatcher
	{
		private readonly IMessageQueue messageQueue;
		private readonly long pauseDelay;
		private readonly Action work;
	
		private bool active;
	
		public PeriodicWorkDispatcher(IMessageQueue messageQueue, long pauseDelay, Action work)
		{
			this.messageQueue = messageQueue;
			this.pauseDelay = pauseDelay;
			this.work = work;

			this.active = false;
		}
	
		public void Start()
		{
			if (active)
			{
				return;
			}
		
			active = true;

			messageQueue.Post(() => Work());
		}

		private void Work()
		{
			if (active)
			{
				work();
				messageQueue.PostDelayed(() => Work(), pauseDelay);
			}
		}
	
		public void Stop()
		{
			if (!active)
			{
				return;
			}
		
			active = false;
		}
	}
}
