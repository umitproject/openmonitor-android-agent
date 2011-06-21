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

import android.util.Log;


public class TCPServer {
	
	ServerSocket serverSocket;
	String response;
	String request;
	Thread thread;
	private volatile boolean stop = false;
	
	public TCPServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		response = "";
		request = "";
	}
	
	public void runServer() {
		 	thread = new Thread() {
			 public void run() {		 
				 while(!stop){
					  	 try {						 					 
						Log.w("##Server", "loop");
			            Socket aSocket = serverSocket.accept();
			            Log.w("##Server", "accept");
			            BufferedReader bufferedReader =
			               new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			            DataOutputStream dataOutputStream = new DataOutputStream(aSocket.getOutputStream());
			            Log.w("##Server", "pre-read");
			            request = bufferedReader.readLine();                        
			            Log.w("##Server", "read" + request);
			            dataOutputStream.writeBytes(response + '\n');
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

	public String getResponse() {
		return response;
	}
	
	public String getRequest() {
		Log.w("##Server", "get" + request);
		return request;
	}

	public void setResponse(String response) {
		this.response = response;
	}		
	
	public void closeConnection() {
		stop = true;		
		Log.w("##Server", "close");
	}
	
}