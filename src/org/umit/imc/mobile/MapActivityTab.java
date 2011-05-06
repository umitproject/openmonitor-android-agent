package org.umit.imc.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivityTab extends Activity{
    /** Called when the activity is first created. */
	private Button ISPButton;
	String param1;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.mapactivity);
        ISPButton = (Button) findViewById(R.id.ISPButton);
        
        ISPButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		Bundle bundle = new Bundle();	
	       	 	bundle.putString("param1","Blank");         		 
	       		Intent i = new Intent(MapActivityTab.this, ISPActivity.class);
	       		i.putExtras(bundle);
	             startActivity(i); 
	       	}

	   	}  );
    }
}

