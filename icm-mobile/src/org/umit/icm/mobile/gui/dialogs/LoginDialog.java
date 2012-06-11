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
	
	package org.umit.icm.mobile.gui.dialogs;
	
	
	import org.umit.icm.mobile.Main;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.debug.Show;
import org.umit.icm.mobile.process.Initialization;
import org.umit.icm.mobile.process.InitializationThread;
import org.umit.icm.mobile.proto.MessageProtos.LoginCredentials;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
	
	
	public class LoginDialog extends Dialog {
		
	    private EditText etUsername;
	    private EditText etPassword;
	    private Button buttonSet;
	    private Button registerButton;
	    
	    public static Context context;
	    public static Activity activity;
	        
	    public LoginDialog(Context context) {    	
	        super(context);            
	        this.context = context;
	        this.activity = (Activity)context;
	        
	    }
	    
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
	    }
	        
	    private class intervalListener implements android.view.View.OnClickListener {
	
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()){
				case R.id.loginButton:
				if (etUsername.getText().toString().equals("")) {
					CharSequence text = context.getString(R.string.username);
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show(); 
				} else if (etPassword.getText().toString().equals("")) {
					CharSequence text = context.getString(R.string.password);
	        		int duration = Toast.LENGTH_SHORT;
	
	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show(); 
				} else {				
					String login = etUsername.getText().toString();
					String password = etPassword.getText().toString();
					
					
					LoginCredentials loginCredentials = LoginCredentials.newBuilder()
					.setUsername(login)
					.setPassword(password)
					.build();
					
					Initialization.registration(loginCredentials);
					
					Show.Info(activity, "This is running from inside login dialog!!");
					new InitializationThread(context).start();
					LoginDialog.this.dismiss();
				}
				break;
				
				case R.id.registerButton:
					LoginDialog.this.dismiss();
					RegisterDialog RegisterDialog = new RegisterDialog(context);
					RegisterDialog.show();
					break;
				}               		
			}			
	
	    }   
	            
	}