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


import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.process.TestManager;
import org.umit.icm.mobile.process.TestObject;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class TestManagerTests extends AndroidTestCase {

    public void testReadWrite() throws Throwable {
    	TestObject test1 = new TestObject(1,"1",1,1);
    	TestObject test2 = new TestObject(2,"2",2,2);
    	List<TestObject> testList = new ArrayList<TestObject>();
    	testList.add(test1);
    	testList.add(test2);
    	TestManager testManager = new TestManager();
    	testManager.setCurrentTests(testList);
        Assert.assertTrue(testList.get(0).isEqual(
        		testManager.getCurrentTests().get(0)));
    }

}