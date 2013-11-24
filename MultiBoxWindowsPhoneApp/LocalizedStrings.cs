using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MultiBoxWindowsPhoneApp
{
	public class LocalizedStrings
	{
		private static MultiBoxWindowsPhoneApp.AppResources localizedResources = new MultiBoxWindowsPhoneApp.AppResources();

		public LocalizedStrings()
		{
		}

		public MultiBoxWindowsPhoneApp.AppResources LocalizedResources
		{
			get
			{
				return localizedResources;
			}
		}
	}
}
