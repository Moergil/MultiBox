package sk.hackcraft.multibox.android.client;

import sk.hackcraft.multibox.R;
import sk.hackcraft.multibox.model.Server;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ServerSelectActivity extends Activity
{
	public static final String EXTRA_KEY_DISCONNECT = "disconnect";
	
	private MultiBoxApplication application;
	
	private EditText serverAddressInputField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		application = (MultiBoxApplication)getApplication();

		setContentView(R.layout.activity_server_select);
		
		serverAddressInputField = (EditText)findViewById(R.id.server_address_input_field);
		serverAddressInputField.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_GO)
				{
					onManualConnectRequested();
					return true;
				}
				else
				{
					return false;
				}
			}
		});
		
		Intent startIntent = getIntent();
		if (startIntent.getBooleanExtra(EXTRA_KEY_DISCONNECT, false))
		{
			Toast.makeText(this, "disconnect", Toast.LENGTH_LONG).show();
		}
		
		Button connectButton = (Button)findViewById(R.id.button_server_connect);
		connectButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onManualConnectRequested();
			}
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		if (application.hasActiveConnection())
		{
			application.destroyServerConnection();
		}
	}
	
	private void onManualConnectRequested()
	{
		String address = serverAddressInputField.getText().toString();
		application.createServerConnection(address);
		
		Server server = application.getServer();
		server.requestInfo(new Server.ServerInfoListener()
		{
			@Override
			public void onServerNameReceived(String name)
			{
				startMainActivity(name);
			}
		});
	}
	
	private void startMainActivity(String serverName)
	{
		Intent intent = new Intent(this, MainActivity.class);
		
		intent.putExtra(MainActivity.EXTRA_KEY_SERVER_NAME, serverName);
		
		startActivity(intent);
		ActivityTransitionAnimator.runStartActivityAnimation(this);
	}
}
