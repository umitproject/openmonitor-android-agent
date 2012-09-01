package org.umit.icm.mobile.gui;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
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

public class BugReportActivity extends Activity {
	
	private Button Report;
	private EditText Reporter;
	private EditText Subject;
	private EditText Description;
	
	static Activity activity;
	private HttpPost httppost;
	private HttpClient httpclient;
	
	private String tracker;
	private String subject;
	private String description;
	private String priority;
	private String targetVersion ;
	private String reporter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bugreportactivity);
		
		activity = this;
		
		Report = (Button)findViewById(R.id.report);
		Reporter = (EditText)findViewById(R.id.Email);
		Description = (EditText)findViewById(R.id.description);
		Subject = (EditText)findViewById(R.id.subject);
		
		tracker = "Bug";
		subject = "";
		description = "";
		priority = "Normal";
		targetVersion = "0.1b";
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://dev.umitproject.org/projects/icm-mobile/issues.xml");
		
		Report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					if(Subject.getText().toString().equalsIgnoreCase("")) {
						CharSequence text = "Please enter Subject";
		        		int duration = Toast.LENGTH_SHORT;
		        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		        		toast.show(); 
					}
					else if(Description.getText().toString().equalsIgnoreCase("")) {
						CharSequence text = "Please describe the bug";
		        		int duration = Toast.LENGTH_SHORT;
		        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		        		toast.show(); 
					}
					else {
						reporter = Reporter.getText().toString();
						subject = "[BugCrashReport]" + Subject.getText().toString();
						if(reporter.equalsIgnoreCase("")) {
							description = "Description: " + Description.getText().toString();
						}
						else {
							description = "From: " + reporter + "\n" + "Description: " 
									+ Description.getText().toString();
						}
								
						String XML = "<issue>"+ 
								"<tracker>" + tracker + "</tracker>" +
								"<subject>" + subject + "</subject>" +
								"<description>" + description + "</description>" +
								"<priority>" + priority + "</priority>" +
								"<target_version>" + targetVersion + "</target_version>" +
								"</issue>";			
						new Background().execute(XML);
						
					}
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Show.Error(activity, e.toString());
				}
						
			}
			
		});
		
	}
	
	private class Background extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... XML) {
			// TODO Auto-generated method stub
			try {
				StringEntity requestEntity= new StringEntity(XML[0],HTTP.UTF_8);
				httppost.setHeader("Content-type", "application/xml");
		
				httppost.setEntity(requestEntity);
				
				httpclient.execute(httppost);
				
				CharSequence text = getString(R.string.bug_report);
				int duration = Toast.LENGTH_SHORT;
		
				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
				toast.show();
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String result) {
    		BugReportActivity.this.finish();
	    }
		  
	}
	  
}
		

