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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.os.Environment;



public class SDCardReadWrite {
	private static File sdCard;
	
	public static void writeString(String fileName, String data) throws Exception{
		sdCard = Environment.getExternalStorageDirectory();	
		FileWriter fileWriter = new FileWriter(new File(sdCard, fileName));
		try {
			fileWriter.write(data);
			
		} finally {
			fileWriter.close();
		}
	}
	
	public static String readString(String fileName) throws Exception{
		sdCard = Environment.getExternalStorageDirectory();	
		FileReader fileReader = new FileReader(new File(sdCard, fileName));
		BufferedReader bufferedReader = new BufferedReader(fileReader); 
		try {
			return bufferedReader.readLine();
			
		} finally {
			fileReader.close();
		}
	}
}