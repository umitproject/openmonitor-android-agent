/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
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

package org.umit.icm.mobile.process;

import java.util.Random;

import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.debug.Show;
import org.umit.icm.mobile.proto.MessageProtos.Login;

import android.app.Activity;
import android.content.Context;

public class InitializationThread extends Thread {
    
	Context context;
	Activity activity;
    public InitializationThread(Context context) {
        this.context = context;
        this.activity=(Activity) context;
    }
    public void run() {  
    	
    	boolean result;
    	
		Initialization.initializeIP(context);
		
		Random random = new Random();
		
		String challenge= Double.toString(random.nextDouble());
		
		Login login = Login.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setPort(80)
		.setChallenge(challenge)
		.setIp(Integer.toString(Globals.myIP))
		.build();
		
		
		try {
		result = AggregatorRetrieve.login(login);
		Initialization.loadLists();
    	Initialization.initializeEventsList();
    	Initialization.initializerPeersList();
    	Initialization.startServices(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result= false;
			e.printStackTrace();
			Show.Error(activity, "Unable to login mobile agent because : "+e.toString());
		}
    }
}
