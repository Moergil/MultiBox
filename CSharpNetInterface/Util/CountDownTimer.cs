using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CSharpNetInterface.Util.CountDownTimerEventArgs;

namespace CSharpNetInterface.Util
{
	public class CountDownTimer
	{
		private readonly IMessageQueue messageQueue;
		private readonly long millisLength;
		private readonly long millisStep;

		private long startTime;

		private bool cancelled;

		public CountDownTimer(IMessageQueue messageQueue, long millisLength, long millisStep)
		{
			this.messageQueue = messageQueue;
			this.millisLength = millisLength;
			this.millisStep = millisStep;

			this.cancelled = false;
		}

		public event EventHandler<TickEventArgs> Tick = delegate { };
		public event EventHandler<FinishEventArgs> Finish = delegate { };

		public void Start()
		{
			startTime = Environment.TickCount;

			messageQueue.Post(TechnicalTick);
		}

		public void Cancel()
		{
			cancelled = true;
		}

		private long last = 0;

		private void TechnicalTick()
		{
			if (cancelled)
			{
				return;
			}

			long environmentTicks = Environment.TickCount;

			last = environmentTicks;

			if (startTime + millisLength < environmentTicks)
			{
				Finish(this, new FinishEventArgs(millisLength));
				return;
			}

			long millisToEnd = (startTime + millisLength) - environmentTicks;
			Tick(this, new TickEventArgs(millisLength, millisToEnd));

			messageQueue.PostDelayed(TechnicalTick, millisStep);
		}
	}

	namespace CountDownTimerEventArgs
	{
		public class TickEventArgs : EventArgs
		{
			public TickEventArgs(long millisLength, long millisToEnd)
			{
				this.Length = millisLength;
				this.MillisToEnd = millisToEnd;
			}

			public long Length
			{
				get;
				private set;
			}

			public long MillisToEnd
			{
				get;
				private set;
			}
		}

		public class FinishEventArgs : EventArgs
		{
			public FinishEventArgs(long length)
			{
				this.Length = length;
			}

			public long Length
			{
				get;
				private set;
			}
		}
	}
}
