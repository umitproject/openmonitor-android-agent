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

package org.umit.icm.mobile.test;

import org.umit.icm.mobile.ControlActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class ControlActivityTest extends
		ActivityInstrumentationTestCase2<ControlActivity> {
	
	private ControlActivity mActivity;
	private Button intervalButton;
	
	public ControlActivityTest() {
	      super("org.umit.icm.mobile", ControlActivity.class);
	    }
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        intervalButton = (Button) mActivity.findViewById(org.umit.icm.mobile.R.id.intervalButton);    
    }
	
	public void testPreconditions() {
	      assertNotNull(intervalButton);
	    }
	
	public void testText() {
	      assertEquals("Set Interval",(String)intervalButton.getText());
	    }
}
