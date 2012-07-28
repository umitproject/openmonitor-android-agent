package org.umit.icm.mobile.gui;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.debug.Show;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BugReportActivity extends Activity{
	
	Button Report;
	EditText Reporter;
	EditText Subject;
	EditText Description;
	
	
	static Activity act;
	HttpPost httppost;
	HttpClient httpclient;
	
	
	String tracker;
	String subject;
	String description;
	String status;
	String priority;
	String target_version ;
	String reporter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bugreportactivity);
		
		act=this;
		
		Report= (Button)findViewById(R.id.report);
		Reporter =(EditText)findViewById(R.id.Email);
		Description = (EditText)findViewById(R.id.description);
		Subject = (EditText)findViewById(R.id.subject);
		
		tracker="Bug";
		subject="";
		description="";
		priority="Normal";
		target_version = "0.1b";

		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://dev.umitproject.org/projects/icm-mobile/issues.xml");
		

	Report.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			

			try {
				if(Subject.getText().toString().equalsIgnoreCase(""))
				{
					CharSequence text = "Please enter Subject";
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	        		toast.show(); 
				}
				else if(Description.getText().toString().equalsIgnoreCase(""))
				{
					CharSequence text = "Please describe the bug";
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	        		toast.show(); 
				}
				else{
					reporter = Reporter.getText().toString();
					subject = "[BugCrashReport]" + Subject.getText().toString();
					description= "From: " + reporter+ Description.getText().toString();
					
					
					
					
					String XML="<issue>"+ 
							"<tracker>"+ tracker +"</tracker>"+
							"<subject>"+ subject +"</subject>"+
							"<description>"+ description + "</description>" +
							"<priority>"+ priority +"</priority>"+
							"<target_version>"+ target_version +"</target_version>"+
							"</issue>";
							
					new Background().execute(XML);
					
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
			Show.Error(act, e.toString());
			}
			
			
		}
		
	});
}
	
	  private class Background extends AsyncTask<String,String,String>{

		@Override
		protected String doInBackground(String... XML) {
			// TODO Auto-generated method stub
			try{
			StringEntity requestEntity= new StringEntity(XML[0],HTTP.UTF_8);
			httppost.setHeader("Content-type", "application/xml");
			
			httppost.setEntity(requestEntity);
			
			
			HttpResponse response= httpclient.execute(httppost);
			String responseBody = EntityUtils.toString(response.getEntity());
			Show.Info(act, responseBody);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		  
	  }

}
		

