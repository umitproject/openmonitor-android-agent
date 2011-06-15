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

package org.umit.icm.mobile.aggregator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;

import android.util.Log;

public class TestAggregatorCommunication {
	public static void testServiceSuggestion () {
		RequestHeader requestHeader = RequestHeader.newBuilder()
		.setAgentID(10)
		.setToken("token")
		.build();
		ServiceSuggestion serviceSuggestion = ServiceSuggestion.newBuilder()
		.setEmailAddress("email")
		.setHostName("hostname")
		.setIp("ip")
		.setServiceName("servicename")
		.setHeader(requestHeader)
		.build();
		
		try {
			
			TestSuggestionResponse testSuggestionResponse = AggregatorRetrieve.sendServiceSuggestion(serviceSuggestion);
			Log.w("####Aggre", Integer.toString(testSuggestionResponse.getHeader().getCurrentTestVersionNo()));
			Log.w("####Aggre", Integer.toString(testSuggestionResponse.getHeader().getCurrentVersionNo()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}