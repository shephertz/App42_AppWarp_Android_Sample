package com.app42.tictactoe.appwarptictactoe;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class AsyncApp42ServiceApi {
		
	private static WarpClient warpClient = null;	
		
	private static AsyncApp42ServiceApi mInstance = null;
	
	private AsyncApp42ServiceApi(){
		// initialize the singletons.
    	
    	WarpClient.initialize(Constants.App42ApiKey, Constants.App42ApiSecret);
    	
	}	
	
	public static WarpClient getMyWarpClient(){
		if(warpClient == null){
			try {
				warpClient = WarpClient.getInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return warpClient;
	}
	
	public static AsyncApp42ServiceApi instance()
	{
		if(mInstance == null)
		{
			mInstance = new AsyncApp42ServiceApi();
		}
		
		return mInstance;
	}
	
	
}