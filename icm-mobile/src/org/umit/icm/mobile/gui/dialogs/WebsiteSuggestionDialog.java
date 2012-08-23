package org.umit.icm.mobile.gui.dialogs;


import org.umit.icm.mobile.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebsiteSuggestionDialog extends Dialog{
	
	String selection;
	Context contextControl;
    private ReadyListener readyListener;
    EditText WebsiteURL;
	
	 public WebsiteSuggestionDialog(Context context, String selection, 
	            ReadyListener readyListener) {
	        super(context);
	        this.selection = selection;
	        this.readyListener = readyListener;
	        this.contextControl = context;
	    }
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.suggestwebsitedialog);
	        Button buttonOK = (Button) findViewById(R.id.SendWebsiteSuggestion);
	        buttonOK.setOnClickListener(new sendListener());
	        WebsiteURL = (EditText) findViewById(R.id.WebsiteURL);
	    }
	 
	 
	 public interface ReadyListener {
	        public void ready(String selection);
	    }
	 
	 private class sendListener implements android.view.View.OnClickListener {

			@Override
			public void onClick(View arg0) {	
				Context context = WebsiteSuggestionDialog.this.getContext();
				if((WebsiteURL.getText().toString().equals(""))){
	        		
	        		CharSequence text = context.getString(R.string.suggested_website_name_blank);
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();        		
	        	} 
	        	else{
	        		readyListener.ready("Website" 
	        				+ "&" + WebsiteURL.getText().toString()     				    	    			
		    	    	+ "&" + "host" 
		    	    	+ "&" + "ip"
		    	    	+ "&" + "port");
		    	        WebsiteSuggestionDialog.this.dismiss(); 
	        		}                
	       		
				}

	    	}
}
