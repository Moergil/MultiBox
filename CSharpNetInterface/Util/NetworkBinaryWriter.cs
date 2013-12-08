using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace CSharpNetInterface.Util
{
	public class NetworkBinaryWriter
	{
		private readonly Stream output;

		public NetworkBinaryWriter(Stream output)
		{
			this.output = output;
		}

		public void WriteInt(int value)
		{
			if (BitConverter.IsLittleEndian)
			{
				value = ByteSwapper.Swap(value);
			}

			byte[] bytes = BitConverter.GetBytes(value);

			WriteBytes(bytes);
		}

		public void WriteUTF(string value)
		{
			byte[] bytes = System.Text.Encoding.UTF8.GetBytes(value);
			WriteInt(bytes.Length);
			WriteBytes(bytes);
		}

		public void WriteBytes(byte[] bytes)
		{
			output.Write(bytes, 0, bytes.Length);
		}
	}
}
