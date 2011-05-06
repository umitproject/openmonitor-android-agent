package org.umit.imc.mobile;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class ControlActivity extends Activity {
    /** Called when the activity is first created. */
	private Button sendButton, intervalButton, scanButton;
	private String scanStatus = "On";
	private RadioButton rb1;
	private RadioButton rb2;
	private Button b1;
	private TextView t1;
	private EditText et1, et2, et3;
	private final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
             "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
             "\\@" +
             "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
             "(" +
             "\\." +
             "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
             ")+"
         );
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controlactivity);
        rb1 = (RadioButton)findViewById(R.id.option1);
        rb2 = (RadioButton)findViewById(R.id.option2);
        t1 = (TextView)findViewById(R.id.TextView01);
        et1 = (EditText) this.findViewById(R.id.text1);
        et2 = (EditText) this.findViewById(R.id.text2);
        et3 = (EditText) this.findViewById(R.id.text3);
        sendButton = (Button) this.findViewById(R.id.selected);
        intervalButton = (Button) this.findViewById(R.id.intervalButton);
        scanButton = (Button) this.findViewById(R.id.scanButton);
        
        sendButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		if((et1.getText().toString().equals("")) && (et2.getText().toString().equals(""))){
	        		Context context = getApplicationContext();
	        		CharSequence text = "Enter Suggestion!";
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();
	        		
	        	} 
	        	else if(!checkEmail(et2.getText().toString())){
	        		Context context = getApplicationContext();
	        		CharSequence text = "Invalid Email Address!";
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();
	        	}
	        	else{
	    	    	if(v == b1){
	    	    		if(rb1.isChecked() == true)
	    	    			t1.setText("Selected is : "+rb1.getText()+ "&" + et1.getText().toString()
	    	    			+ "&" + et2.getText().toString());
	    	    		
	    	    		if(rb2.isChecked() == true)
	    	    			t1.setText("Selected is : "+rb2.getText()+ "&" + et1.getText().toString()
	    	    			+ "&" + et2.getText().toString());
	    	    		else{
	    	    			Context context = getApplicationContext();
	    	        		CharSequence text = "No Selection!";
	    	        		int duration = Toast.LENGTH_SHORT;

	    	        		Toast toast = Toast.makeText(context, text, duration);
	    	        		toast.show();
	    	    		}
	    	    	}
	        	}                
	       		
	       	}

	    }  );
        
        intervalButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		if(et3.getText().toString().equals("")){
	       			Context context = getApplicationContext();
	        		CharSequence text = "Empty!";
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();
	       				
	       		}
	       		else{
	       			int newInterval = Integer.parseInt(et3.getText().toString());
	       			Context context = getApplicationContext();
	        		CharSequence text = "New scan interval: " + et3.getText().toString() + " s";
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();
	       		}
	       		
	       	}

	   	}  );
        
        scanButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {
	       		if(scanStatus.compareTo("On") == 0)
	       			scanStatus = "Off";
	       		else
	       			scanStatus = "On";
	       		
	       		Context context = getApplicationContext();
        		CharSequence text = "Scan status changed to: " + scanStatus;
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
	       	}

	   	}  );
                
    }
   
    
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
	
}




