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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
	String start_date;
	String due_date;
	String estimated_time;
	String done;
	String input;
	String file_description;
	String assignedID;
	String username;
	String password;
	String component;
	String summary;
	String details;
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
		status="";
		priority="Normal";
		target_version = "0.1b";
		start_date="";
		due_date="";
		estimated_time="";
		done="";
		input="";
		file_description= "";
		assignedID="";
		username="";
		password="";
		component="";
		summary="";
		details="";
		reporter="";
		
		
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://dev.umitproject.org/projects/icm-mobile/issues.xml");
		

	Report.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			

			try {
				
				reporter = Reporter.getText().toString();
				subject = "[BugCrashReport]" + Subject.getText().toString();
				description= "From: " + reporter+ Description.getText().toString();
				
				
				
				
				String XML="<issue>"+ 
						"<tracker>"+ tracker +"</tracker>"+
						"<subject>"+ subject +"</subject>"+
						"<status>"+ status +"</status>"+
						"<priority>"+ priority +"</priority>"+
						"<target_version>"+ target_version +"</target_version>"+
						"<start_date>"+ start_date +"</startdate>" +
						"<due_date>"+ due_date + "</due_date>" +
						"<input>" + input + "</input>"+
						"<file_description>"+file_description +"</file_description>" +
						"<assignedID>"+ assignedID + "</assignedID>"+
						"<username>" + username + "</username>" +
						"<password>" + password + "</password>" +
						"<component>" + component + "</component>" +
						"<summary>" + summary + "</summary>" +
						"<details>" + details + "</details>" +
						"</issue>";
						
				
				
				
				StringEntity requestEntity= new StringEntity(XML,HTTP.UTF_8);
				httppost.setHeader("Content-type", "application/xml");
				
				httppost.setEntity(requestEntity);
				
				
				HttpResponse response= httpclient.execute(httppost);
				String responseBody = EntityUtils.toString(response.getEntity());
				Show.Info(act, responseBody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			Show.Error(act, e.toString());
			}
			
			
		}
		
	});
}

}
		
/*		
		
	
	}
	
	
	  private static void tryGetIssues(RedmineManager mgr) throws Exception {
	        List<Issue> issues = mgr.getIssues("icm-mobile", null);
	        for (Issue issue : issues) {
	            System.out.println(issue.toString());
	            Show.Info(act, issue.toString());
	        }
	    }

}*/
