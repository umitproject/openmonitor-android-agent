	/**
	 * Copyright (C) 2011 Adriano Monteiro Marques
	 *
	 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
	 *
	 * This program is free software; you can redistribute it and/or modify
	 * it under the terms of the GNU General Public License as published by
	 * the Free Software Foundation; either version 2 of the License, or
	 * (at your option) any later version.
	 *
	 * This program is distributed in the hope that it will be useful,
	 * but WITHOUT ANY WARRANTY; without even the implied warranty of
	 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 * GNU General Public License for more details.
	 *
	 * You should have received a copy of the GNU General Public License
	 * along with this program; if not, write to the Free Software
	 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
	 * USA
	 */
	
	package org.umit.icm.mobile.gui;
	
	
	import java.io.IOException;

import org.umit.icm.mobile.Main;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.debug.Show;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Initialization;
import org.umit.icm.mobile.proto.MessageProtos.LoginCredentials;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

	
	
	public class LoginActivity extends Activity {
		
	    private EditText etUsername;
	    private EditText etPassword;
	    private Button buttonSet;
	    private Button registerButton;
	    ProgressDialog progressDialog;    
	    private Context context;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.logindialog);
	        buttonSet = (Button) findViewById(R.id.loginButton);
	        buttonSet.setOnClickListener(new intervalListener());
	        registerButton= (Button)findViewById(R.id.registerButton);
	        registerButton.setOnClickListener(new intervalListener());
	        etUsername = (EditText) findViewById(R.id.etUsername);                           
	        etPassword = (EditText) findViewById(R.id.etPassword);
	        context=this;
	    }
	        
	    private class intervalListener implements android.view.View.OnClickListener {
	
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()){
				case R.id.loginButton:
				if (etUsername.getText().toString().equals("")) {
					CharSequence text = getString(R.string.username);
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	        		toast.show(); 
				} else if (etPassword.getText().toString().equals("")) {
					CharSequence text = getString(R.string.password);
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	        		toast.show(); 
				} else {
					progressDialog= ProgressDialog.show(context, "", "Logging in.. ", true, false);
					progressDialog.show();
					
					
					
					String login = etUsername.getText().toString();
					String password = etPassword.getText().toString();
					
					
					LoginCredentials loginCredentials = LoginCredentials.newBuilder()
					.setUsername(login)
					.setPassword(password)
					.build();
					
					
					Initialization.checkProfiler();		
					try {
						Initialization.checkFiles();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					new Background().execute(loginCredentials);
					
/*					Initialization.registration(loginCredentials);
					Initialization.login();
					
					Initialization.loadLists();
					Initialization.initializeEventsList();
			        Initialization.initializerPeersList();
			        Initialization.startServices(context);*/
					
					
				}
				break;
				
				case R.id.registerButton:
					Intent i = new Intent(Intent.ACTION_VIEW, 
						       Uri.parse(Constants.AGGREGATOR_URL + Constants.AGGR_REGISTER_USER));
				
					startActivity(i);
					finish();
					break;
				}               		
			}			
	
	    }   
	    
	    
	    private class Background extends AsyncTask<LoginCredentials,String,String>{

			@Override
			protected String doInBackground(LoginCredentials... loginCredentials) {
				// TODO Auto-generated method stub
				Initialization.checkProfiler();		
				try {
					Initialization.checkFiles();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				publishProgress("Registering Agent");
				boolean register=Initialization.registration(loginCredentials[0]);
				if(register){
					publishProgress("Registration Done.");
					publishProgress("Logging Agent");
					boolean login=Initialization.login();
					if(login){
						publishProgress("Done.");
					}else{
						publishProgress("Unable to login Agent");
						this.cancel(true);
					}
				}else{
					publishProgress("Unable to Register Agent");
					this.cancel(true);
				}
				
				return null;
			}
			
			protected void onProgressUpdate(String... string) {
				progressDialog.setMessage(string[0]);
				if(string[0].equalsIgnoreCase("Unable to Register Agent")){
					Show.Error((Activity)context, string[0]);
					this.cancel(true);
				}else if(string[0].equalsIgnoreCase("Unable to login Agent")){
					Show.Error((Activity)context, string[0]);
					this.cancel(true);
				}
		     }
	    	
			protected void onPostExecute(String result) {
				progressDialog.dismiss();
				if(!isCancelled()){
					Intent intent = new Intent(LoginActivity.this,Main.class);
					startActivity(intent);
					finish();	
				}
				
		     }
			
			protected void onCancelled() {
				progressDialog.dismiss();
		     }
	    	
	    }
	    
	            
	}