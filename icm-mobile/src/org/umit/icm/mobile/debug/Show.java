package org.umit.icm.mobile.debug;

import android.app.Activity;
import android.widget.Toast;

public class Show {
	
	
	public static void Error(final Activity activity,final String msg){
		
		System.err.println(msg);
		activity.runOnUiThread(new Runnable() {
		    public void run() {
		        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		    }
		});
		
	}
	public static void Info(final Activity activity,final String msg){
		
		System.out.println(msg);
		activity.runOnUiThread(new Runnable() {
		    public void run() {
		        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		    }
		});
		
	}
	

}
