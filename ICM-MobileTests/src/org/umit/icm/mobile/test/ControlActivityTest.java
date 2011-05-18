package org.umit.icm.mobile.test;

import org.umit.icm.mobile.ControlActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class ControlActivityTest extends
		ActivityInstrumentationTestCase2<ControlActivity> {
	
	private ControlActivity mActivity;
	private TextView mView;
	
	public ControlActivityTest() {
	      super("org.umit.icm.mobile", ControlActivity.class);
	    }
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        mView = (TextView) mActivity.findViewById(org.umit.icm.mobile.R.id.TextView01);    
    }
	
	public void testPreconditions() {
	      assertNotNull(mView);
	    }
	
	public void testText() {
	      assertEquals("Selected is :",(String)mView.getText());
	    }
}
