using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiBoxCSharpCore.PlaylistEventArgs;

namespace MultiBoxCSharpCore
{
	public interface IPlaylist
	{
		void Init();
		void Close();

		IList<Multimedia> Items { get; }
		void AddItem(long itemId);

		event EventHandler<NewPlaylistEventArgs> PlaylistChanged;
		event EventHandler<ItemAddedEventArgs> ItemAdded;
	}

	namespace PlaylistEventArgs
	{
		public class NewPlaylistEventArgs : EventArgs
		{
			public NewPlaylistEventArgs(IList<Multimedia> newPlaylist)
			{
				this.NewPlaylist = newPlaylist;
			}

			public IList<Multimedia> NewPlaylist
			{
				get;
				private set;
			}
		}

		public class ItemAddedEventArgs : EventArgs
		{
			public ItemAddedEventArgs(bool success, Multimedia multimedia)
			{
				this.Success = success;
				this.Multimedia = multimedia;
			}

			public bool Success
			{
				get;
				private set;
			}

			public Multimedia Multimedia
			{
				get;
				private set;
			}
		}
	}
}
