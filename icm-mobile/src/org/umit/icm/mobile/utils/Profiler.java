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

package org.umit.icm.mobile.utils;

import org.umit.icm.mobile.proto.MessageProtos.Trace;

import android.util.Log;

public class Profiler {
	
	
	private static long timeTaken() {
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		task();
		end = System.currentTimeMillis()-start;
		return end;
	}
	
	private static void task() {
		Trace trace = Trace.newBuilder()
		.setHop(10)
		.setIp("IP")				
		.build();
	}
	
	public static void runProfiler() {
		Log.w("##Profile", Long.toString(timeTaken()));
	}
	
	
}