package com.app42.tictactoe.appwarptictactoe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class MainActivity extends Activity implements  ConnectionRequestListener{
		
	private AsyncApp42ServiceApi asyncService; 
	private EditText userName;

	private SharedPreferences mPrefs;
	private ProgressDialog progressDialog;
	private TextView responseTV;		
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);            
        userName = (EditText) this.findViewById(R.id.uname);
        responseTV=(TextView)this.findViewById(R.id.responseTV);
        // Check if we can use saved creds
        mPrefs = getSharedPreferences(MainActivity.class.getName(), MODE_PRIVATE);
        asyncService = AsyncApp42ServiceApi.instance();
        String loggedInName = mPrefs.getString(Constants.SharedPrefUname, null);        
//        if(loggedInName != null && !loggedInName.isEmpty())
//        {
    		// now connect and join Warp Server 
        	userName.setText(loggedInName);
    		AsyncApp42ServiceApi.getMyWarpClient().addConnectionRequestListener(this);
    		
     //   }        
    }    
    
    public void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);
    }
    
    public void onStart()
    {
    	super.onStart();    	    							
    }
    
	 public void onSigninClicked(View view) {
		 AsyncApp42ServiceApi.getMyWarpClient().connectWithUserName(userName.getText().toString());    	
		// progressDialog = ProgressDialog.show(this, "", "signing in..");
		 //asyncService.authenticateUser(userName.getText().toString(), password.getText().toString(), this);
	 }
	
	 
	private void saveCreds()
	{
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(Constants.SharedPrefUname, userName.getText().toString());
		editor.commit();		
	}
	
    private void gotoHomeActivity(String signedInUserName)
    {		
        //Finish the activity so it can't be returned to.
        this.finish();
        // Create an Intent that will start the home activity.
        Intent mainIntent = new Intent(this, UserHomeActivity.class);
        mainIntent.putExtra(Constants.IntentUserName, signedInUserName);
        this.startActivity(mainIntent);    	    
    }
        

	

	/*
	 * Warp connection listener callback
	 * 
	 */
	@Override
	public void onConnectDone(ConnectEvent arg0) {
		if(arg0.getResult() == WarpResponseResultCode.SUCCESS){
			// yay! we are connected to Warp server
			gotoHomeActivity(userName.getText().toString());
		
		}		
	}

	@Override
	public void onDisconnectDone(final ConnectEvent arg0) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				responseTV.setText("onDisconnectDone"+arg0.getResult());
			}
		});

		
	}

	@Override
	public void onInitUDPDone(byte arg0) {
		// TODO Auto-generated method stub
		responseTV.setText("onInitUDPDone");
	}
}

