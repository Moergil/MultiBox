using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpNetInterface.Message
{
	public interface IDataTransformer<I, O>
	{
		O Transform(I input);
	}
}
