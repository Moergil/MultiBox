package sk.hackcraft.multibox.android.clientlegacy.util;

import sk.hackcraft.multibox.android.clientlegacy.R;
import android.app.Activity;

public class ActivityTransitionAnimator
{
	public static void runStartActivityAnimation(Activity activity)
	{
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.zoom_out);
	}
	
	public static void runFinishActivityAnimation(Activity activity)
	{
		activity.overridePendingTransition(R.anim.zoom_in, R.anim.slide_out_left);
	}
}
