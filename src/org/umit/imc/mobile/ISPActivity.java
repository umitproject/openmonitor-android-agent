package org.umit.imc.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ISPActivity extends Activity{
    /** Called when the activity is first created. */
	private ListView lv1;
	private String lv_arr[]={"Websites","Services"};
	private Button backButton;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.ispactivity);
        lv1=(ListView)findViewById(R.id.ListView01);
        // By using setAdpater method in listview we an add string array in list.
        lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
        
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		Bundle bundle = new Bundle();	
	       	 	bundle.putString("param1","Blank");         		 
	       		Intent i = new Intent(ISPActivity.this, MapActivityTab.class);
	       		i.putExtras(bundle);
	            startActivity(i); 
	       	}

	   	}  );
    }
}

