/**
 * Copyright (C) 2012 Adriano Monteiro Marques
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

package org.umit.icm.mobile.connectivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.Address;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class DNSLookup
{	
	public static InetAddress getIP(String url) throws UnknownHostException {
		return Address.getByName(url); 
	}
	
	public static String getIPString(String url) throws UnknownHostException {
		return Address.getByName(url).toString(); 
	}
	
	public static Record[] getDNSRecord(String url, int type) throws TextParseException {
		return new Lookup(url, type).run();
	}
	
	public static Record[] getDNSRecordNS(String url) throws TextParseException {
		return getDNSRecord(url, Type.NS);
	}
	
	public static String[] getDNSRecordNSString(String url) throws TextParseException {
		Record [] records = DNSLookup.getDNSRecordNS(url);
		String [] nsString = null;
		if(records != null) {
			nsString = new String[records.length]; 
			for (int i = 0; i < records.length; i++) {
				NSRecord nsRecord = (NSRecord) records[i];
				nsString[i] = nsRecord.rdataToString();
			}
		}
		return nsString;
	}
	
	public static Record[] getDNSRecordA(String url) throws TextParseException {
		return getDNSRecord(url, Type.A);
	}
	
	public static String[] getDNSRecordAString(String url) throws TextParseException {
		Record [] records = DNSLookup.getDNSRecordA(url);
		String [] aString = null;
		if(records != null) {
			aString = new String[records.length]; 
			for (int i = 0; i < records.length; i++) {
				ARecord aRecord = (ARecord) records[i];
				aString[i] = aRecord.rdataToString();
			}
		}
		return aString;
	}
	
	public static Record[] getDNSRecordSOA(String url) throws TextParseException {
		return getDNSRecord(url, Type.SOA);
	}
	
	public static String[] getDNSRecordSOAString(String url) throws TextParseException {
		Record [] records = DNSLookup.getDNSRecordSOA(url);
		String [] soaString = null;
		if(records != null) {
			soaString = new String[records.length]; 
			for (int i = 0; i < records.length; i++) {
				SOARecord aRecord = (SOARecord) records[i];
				soaString[i] = aRecord.rdataToString();
			}
		}
		return soaString;
	}
	
}
