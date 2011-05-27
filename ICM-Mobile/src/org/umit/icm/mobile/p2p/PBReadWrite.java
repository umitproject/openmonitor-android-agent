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

package org.umit.icm.mobile.p2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.umit.icm.mobile.proto.generated.MessageProtos.*;

public class PBReadWrite {
	
	private static WebsiteReportNormal websiteNormal = WebsiteReportNormal.newBuilder()
	.setBandwidth(128.9)
	.setMatchPattern(true)
	.setResponseContent("Content")
	.setStatusCode(1)
	.setTestID(10)
	.setTimeUTC(123456)
	.build();
	

	public void testWrite(){
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("/tmp/temp.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			websiteNormal.writeTo(outputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WebsiteReportNormal testRead(){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/tmp/temp.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebsiteReportNormal.newBuilder().build();
		try {
			WebsiteReportNormal newWebsiteNormal = WebsiteReportNormal.parseFrom(inputStream);
			return newWebsiteNormal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static WebsiteReportNormal testReturn() {
		return websiteNormal;
	}
}

