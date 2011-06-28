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

import java.io.IOException;

import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;

public class ProcessActions {	
	
	public static void updateAgentVersion(ResponseHeader header) 
		throws IOException {
		
		if (header.getCurrentVersionNo() 
				> Globals.versionManager.getAgentVersion()) {
			Globals.versionManager.setAgentVersion(header.getCurrentVersionNo());
			// TODO patch current binary
		}
	}
	
	public static void updateTestsVersion(ResponseHeader header)
		throws IOException {
		
		if (header.getCurrentTestVersionNo() 
				> Globals.versionManager.getTestsVersion()) {
			Globals.versionManager.setTestsVersion(header.getCurrentTestVersionNo());
			// TODO update current tests
		}
	}
	
	public static void newTestsResponseAdd(NewTestsResponse response) throws IOException, RuntimeException {		
		for (int i = 0 ; i < response.getTestsCount() ; i++) {			
				Globals.testManager.addTest(MessageConversion.testToTestObject(response.getTests(i)));			
		}		
		Globals.testManager.writeTests();
	}
	
	/*public static void initializeRequestHeader() throws IOException, RuntimeException {
		Globals.requestHeader = RequestHeader.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setToken(Globals.runtimeParameters.getToken())
		.build();
	}*/
	
	public static boolean updateAgent(NewVersionResponse newVersionResponse) {	
		return true;
	}
	
	public static boolean updateTests(NewTestsResponse newTestsResponse) {
		try {
			newTestsResponseAdd(newTestsResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}