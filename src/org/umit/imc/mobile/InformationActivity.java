package org.umit.imc.mobile;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.Formatter;

public class InformationActivity extends Activity{
    /** Called when the activity is first created. */
	private CheckBox c1, c2;
	private ListView lv1;
	private String lv_arr[]={"Websites","Services"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationactivity);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        TextView ipTextView = (TextView) findViewById(R.id.ipTextView);
        ipTextView.append(Formatter.formatIpAddress(ipAddress));
        
        c1 = (CheckBox) findViewById(R.id.check1);
        c2 = (CheckBox) findViewById(R.id.check2);
        c2.setVisibility(8);
        lv1=(ListView)findViewById(R.id.ListView01);
     // By using setAdpater method in listview we an add string array in list.
        lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
     
     
        
                        
    }
    
    
	
}