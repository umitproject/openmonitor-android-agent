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
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.umit.icm.mobile.process.Constants;

import android.util.Log;

/**
 * This is the TCPServer class. Implements a standard TCP Server.
 */

public class TCPServer {
	
	ServerSocket serverSocket;
	String response;
	String request;
	Thread thread;
	private volatile boolean stop = false;
	
	/**
	 * This is the default constructor. Takes as parameter a port number.
	 * Opens a {@link Socket} using the port number.
	 */	
	public TCPServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		response = "";
		request = "";
	}
	
	/**
	 * This is the main Server thread. Listens on the port and accepts connections.
	 * Reads requests and Writes responses. Stops running when stop == false.
	 */
	public void runServer() {
		 	thread = new Thread() {
			 public void run() {		 
				 while(!stop){
					  	 try {	
					  		if(Constants.DEBUG_MODE)
					  			Log.w("##Server", "loop");
					  		Socket aSocket = serverSocket.accept();
					  		if(Constants.DEBUG_MODE)
					  			Log.w("##Server", "accept");
					  		BufferedReader bufferedReader =
					  				new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
					  		DataOutputStream dataOutputStream = new DataOutputStream(aSocket.getOutputStream());
					  		if(Constants.DEBUG_MODE)
					  			Log.w("##Server", "pre-read");
					  		request = bufferedReader.readLine();  
					  		if(Constants.DEBUG_MODE)
					  			Log.w("##Server", "read" + request);
					  		dataOutputStream.writeBytes(response + '\n');
					  		if(Constants.DEBUG_MODE)
					  			Log.w("##Server", "write");
						 } catch (Exception e) {
							 throw new RuntimeException("run Server Exception", e);
						 }
				         } 
						try {
							serverSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
			 }
				 
		 };
		 thread.start();
	}
	
	/**
	 * Returns the response String.
	 */
	public String getResponse() {
		return response;
	}
		
	/**
	 * Returns the request String.
	 */
	public String getRequest() {
		if(Constants.DEBUG_MODE)
			Log.w("##Server", "get" + request);
		return request;
	}
	
	/**
	 * Sets the response.
	 */
	public void setResponse(String response) {
		this.response = response;
	}		
	
	/**
	 * Stops the thread by asserting stop as true.
	 */
	public void closeConnection() {
		stop = true;
		if(Constants.DEBUG_MODE)
			Log.w("##Server", "close");
	}
	
}