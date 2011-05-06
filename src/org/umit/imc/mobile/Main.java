package org.umit.imc.mobile;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Main extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, InformationActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Information").setIndicator("Information",
                          res.getDrawable(R.drawable.tabs_icons))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ControlActivity.class);
        spec = tabHost.newTabSpec("Control").setIndicator("Control",
                          res.getDrawable(R.drawable.tabs_icons))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MapActivityTab.class);
        spec = tabHost.newTabSpec("Map").setIndicator("Map",
                          res.getDrawable(R.drawable.tabs_icons))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);
    }
}