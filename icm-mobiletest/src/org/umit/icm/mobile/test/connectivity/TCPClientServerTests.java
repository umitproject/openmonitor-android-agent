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

package org.umit.icm.mobile.test.connectivity;



import org.umit.icm.mobile.connectivity.TCPClient;
import org.umit.icm.mobile.connectivity.TCPServer;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class TCPClientServerTests extends AndroidTestCase {

    public void testClientServer() throws Throwable {
    	TCPServer tcpServer = new TCPServer(5005);
    	tcpServer.runServer();
    	tcpServer.setResponse("Server Response");
    	TCPClient tcpClient = new TCPClient();
    	tcpClient.openConnection("localhost", 5005);
    	tcpClient.writeLine("Client Request");    	
    	String serverResponse = tcpClient.readLine();
    	tcpClient.closeConnection();
    	tcpServer.closeConnection();
        Assert.assertTrue(serverResponse.equals("Server Response"));        
        
    }        

}