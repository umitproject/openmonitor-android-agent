package org.umit.imc.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ISPActivity extends Activity{
    /** Called when the activity is first created. */
	private ListView lv1;
	private String lv_arr[]={"Websites","Services"};
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.ispactivity);
        lv1=(ListView)findViewById(R.id.ListView01);
        // By using setAdpater method in listview we an add string array in list.
        lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
    }
}

