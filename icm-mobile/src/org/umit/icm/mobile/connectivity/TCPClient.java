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

package org.umit.icm.mobile.connectivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.umit.icm.mobile.process.Constants;



import android.util.Log;

/**
 * This is the TCPClient class. Implements a standard TCP Client.
 */

public class TCPClient {
	
	private Socket socket;
    private DataOutputStream dataOutputStream; 
    private BufferedReader bufferedReader;    
    
    public TCPClient() {
    	socket = null;
    	dataOutputStream = null;
    	bufferedReader = null;    	
    }
    
    /**
	 * Opens the connection by creating a new {@link Socket} using the passed
	 * ip and port, {@link DataOutputStream} and {@link BufferedReader}. 
	 * 
	 *	 
	                          
	@param  ip  An object of the type {@link String}
	 *  	                          	
	 
	@param  port  An object of the type int
	 */
    public void openConnection(String ip, int port) throws UnknownHostException, IOException {
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "open");
    	socket = new Socket(ip, port);
    	dataOutputStream = new DataOutputStream(socket.getOutputStream());
    	bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    	
    }
    
    /**
	 * Returns a {@link String} read from the {@link BufferedReader}.
	 * 
	 *	 
	                          	      
	@return      {@link String}
	 */
    public String readLine() throws IOException {
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "read");
    	String line =  bufferedReader.readLine();
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "readafter" + line);
    	return line;
    }
    
    /**
	 * Returns a byte[] read from the {@link BufferedReader}.
	 * 
	 *	 
	                          	      
	@return      byte[]
	 */
    public byte[] readBytes() throws IOException {
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "readbytes");
    	socket.setKeepAlive(true);
    	socket.setSoTimeout(0);
    	InputStream inputStream = socket.getInputStream();     	
    	long length = inputStream.available();
    	byte[] bytes = new byte[(int) length];
    	inputStream.read(bytes);
    	return bytes;
    }
    
    public byte[] readBytes(int size) throws IOException {
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client2", "readbytes");    	
    	InputStream inputStream = socket.getInputStream();
    	byte[] bytes = new byte[size];
    	inputStream.read(bytes);
    	return bytes;
    }
    
    /**
	 * Returns a {@link String} object read from the {@link BufferedReader}.
	 * 
	 *	 
	                          	      
	@return      {@link String} 
	 */
    public String readLines() throws IOException {
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "readlines");    	
    	String line = null;
    	StringBuffer lines = new StringBuffer();    	
    	 while (((line = bufferedReader.readLine()) != null) && (!(line.equals("")))) {   		
    		lines.append(line);    		  		
    	 }    	    	    	 
    	 return lines.toString();    	
    	    	    	
    }
    
    
    /**
	 * Takes as parameter a {@link String} object and writes it to the
	 * {@link DataOutputStream}. 
	 *	 
	                          	      
	@param      line  An object of the type {@link String} 
	 */
    public void writeLine(String line) throws IOException {
    	dataOutputStream.writeBytes(line + '\n');   
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "write");
    }
    
    /**
	 * Takes as parameter a byte[] object and writes it to the
	 * {@link DataOutputStream}. 
	 *	 
	                          	      
	@param      line  An object of the type byte[] 
	 */
    public void writeLine(byte[] line) throws IOException {
    	dataOutputStream.write(line);  
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "writeByte");
    }
    
    /**
	 * Returns the current {@link Socket} {@link InetAddress} 
	 *	 
	                          	      
	@return      {@link InetAddress} 
	 */
    public InetAddress getInetAddress() {
    	return socket.getInetAddress();
    }
    
    /**
	 * Returns the current {@link Socket} Port number
	 *	 
	                          	      
	@return      int 
	 */
    public int getPort() {
    	return socket.getPort();
    }
    
    /**
	 * Closes the open {@link Socket}, {@link DataOutputStream} and 
	 * {@link BufferedReader} 
	 */
    public void closeConnection() throws IOException {    	
    	dataOutputStream.close();
    	bufferedReader.close();
    	socket.close();    
    	if(Constants.DEBUG_MODE)
    		Log.w("##Client", "close");
    }
    
}