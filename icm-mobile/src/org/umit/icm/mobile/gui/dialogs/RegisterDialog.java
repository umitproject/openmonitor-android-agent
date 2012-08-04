package org.umit.icm.mobile.gui.dialogs;

import org.umit.icm.mobile.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class RegisterDialog extends Dialog{
	
	public Context context;

	public RegisterDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerdialog);
		
		
	}

}