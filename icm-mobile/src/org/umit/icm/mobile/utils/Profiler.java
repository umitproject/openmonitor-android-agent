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


import java.io.IOException;

import android.util.Log;

public class Profiler {
	
	private long start;
	private long end;
	
	public Profiler() {
		start = 0;
		end = 0;
	}
	
	private long timeTaken(TaskInterface taskInterface) {
		start = 0;
		end = 0;
		start = System.currentTimeMillis();
		taskInterface.task();
		end = System.currentTimeMillis()-start;
		return end;
	}
			
	public void runProfiler(TaskInterface taskInterface) {
		Log.w(taskInterface.taskName(), "Done");
		try {
			SDCardReadWrite.writeStringAppend(Constants.PROFILER_FILE
					, Constants.PROFILER_DIR
					, taskInterface.taskName()
					+ "," + Long.toString(timeTaken(taskInterface))
					+ ",ms,\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}			
	
}

