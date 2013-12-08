using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using MultiboxClientCSharpCore.Model;

namespace MultiBoxWindowsPhoneApp.ViewModels
{
	public class MultimediaViewModel
	{
		private MultimediaItem multimedia;

		public MultimediaViewModel(MultimediaItem multimedia)
		{
			this.multimedia = multimedia;
		}

		public string Name
		{
			get { return multimedia.Name; }
		}

		public MultimediaItem Multimedia
		{
			get { return multimedia; }
		}
	}
}
