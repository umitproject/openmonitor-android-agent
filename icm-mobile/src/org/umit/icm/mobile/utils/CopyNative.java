/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 * 
 * Author: Angad Singh
 * Edited by:  Zubair Nabi <zn.zubairnabi@gmail.com>
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;

public class CopyNative {
	/**
     * Copies the native binary from resource to path
     * 
     * @param path
     * path to where the native binary needs to be copied
     * 
     * @param resource
     * integer value of the resource (e.g. R.raw.synscanner)
     * 
     */
    public static void CopyNativeFunction(String path, int resource, Context context) {
        InputStream setdbStream = context.getResources().openRawResource(resource);
        try {
        	//Set folder permissions
            Process processFolderPerm = Runtime.getRuntime().exec("su");
            DataOutputStream osFolderPerm = new DataOutputStream(processFolderPerm.getOutputStream());            
            osFolderPerm.writeBytes("chmod 777 " + path + "\n");
            osFolderPerm.writeBytes("exit\n");
            osFolderPerm.flush();
        	
            String filePath = path + "/busybox";
            byte[] bytes = new byte[setdbStream.available()];
            DataInputStream dis = new DataInputStream(setdbStream);
            dis.readFully(bytes);   
            FileOutputStream setdbOutStream = new FileOutputStream(filePath);
            setdbOutStream.write(bytes);
            setdbOutStream.close();
                        
            Process processExecPerm = Runtime.getRuntime().exec("su");
            DataOutputStream osExecPerm = new DataOutputStream(processExecPerm.getOutputStream());
            osExecPerm.writeBytes("chmod 755 " + filePath + "\n");
            osExecPerm.writeBytes("exit\n");
            osExecPerm.flush();
        }
        
        catch (Exception e) {
        	e.printStackTrace();
          return;
        }
      }
}
