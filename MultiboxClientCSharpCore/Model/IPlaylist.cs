using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using MultiboxClientCSharpCore.Model.PlaylistEventArgs;

namespace MultiboxClientCSharpCore.Model
{
	public interface IPlaylist
	{
		void Init();
		void Close();

		IList<MultimediaItem> Items { get; }
		void AddItem(long itemId);

		event EventHandler<PlaylistChangedEventArgs> PlaylistChanged;
		event EventHandler<ItemAddedEventArgs> ItemAdded;
	}

	namespace PlaylistEventArgs
	{
		public class PlaylistChangedEventArgs : EventArgs
		{
			public PlaylistChangedEventArgs(IList<MultimediaItem> newPlaylist)
			{
				this.Playlist = newPlaylist;
			}

			public IList<MultimediaItem> Playlist
			{
				get;
				private set;
			}
		}

		public class ItemAddedEventArgs : EventArgs
		{
			public ItemAddedEventArgs(bool success, MultimediaItem multimedia)
			{
				this.Success = success;
				this.Multimedia = multimedia;
			}

			public bool Success
			{
				get;
				private set;
			}

			public MultimediaItem Multimedia
			{
				get;
				private set;
			}
		}
	}
}
