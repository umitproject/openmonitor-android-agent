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
	
	public static void writeString(String fileName
			, String dir, String data) throws Exception{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir, fileName);
    	if(!file.exists()){
    		file.createNewFile();
    		FileWriter fileWriter = new FileWriter(file);
    		try {
    			fileWriter.write(data);
			
    		} catch (Exception e) {
		    throw new RuntimeException("SDCardWrite exception", e);
    		} finally {
			fileWriter.close();
    		}
    	}
	}
	
	public static String readString(String fileName
			, String dir) throws Exception{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader); 
		try {
			return bufferedReader.readLine();
			
		} catch (Exception e) {
		    throw new RuntimeException("SDCardRead exception", e);
  	    } finally {
			fileReader.close();
		}
	}
}