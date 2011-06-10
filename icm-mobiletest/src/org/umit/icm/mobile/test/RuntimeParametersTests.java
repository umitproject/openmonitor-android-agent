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


import org.umit.icm.mobile.process.RuntimeParameters;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class RuntimeParametersTests extends AndroidTestCase {

    public void testStatusSetGet() throws Throwable {
    	RuntimeParameters runtimeParameters = new RuntimeParameters();
    	runtimeParameters.setScanStatus("On");
    	Assert.assertEquals("On", runtimeParameters.getScanStatus());
    }
    
    public void testIntervalSetGet() throws Throwable {
    	RuntimeParameters runtimeParameters = new RuntimeParameters();
    	runtimeParameters.setScanInterval(33);
    	Assert.assertEquals(33, runtimeParameters.getScanInterval());
    }
    
    public void testAgentIDSetGet() throws Throwable {
    	RuntimeParameters runtimeParameters = new RuntimeParameters();
    	runtimeParameters.setAgentID(40000);
    	Assert.assertEquals(40000, runtimeParameters.getAgentID());
    }
    
    public void testTokenSetGet() throws Throwable {
    	RuntimeParameters runtimeParameters = new RuntimeParameters();
    	runtimeParameters.setToken("token");
    	Assert.assertEquals("token", runtimeParameters.getToken());
    }
}