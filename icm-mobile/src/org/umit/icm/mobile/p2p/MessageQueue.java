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

package org.umit.icm.mobile.p2p;

import java.util.LinkedList;
import java.util.Queue;

import org.umit.icm.mobile.process.Constants;

/**
 * Provides a queueing mechanism for the P2P messages.
 */

public class MessageQueue {

	private Queue<QueueObject> messageQ; 
	
	public MessageQueue() {
		messageQ = new LinkedList<QueueObject>();
	}
	
	/**
	 * Adds a message to the queue.
	 * 
	 * 
	 
	 @param message A message of type {@link QueueObject}
	 *
	 
	 @return boolean
	 *
	 
	 @see Queue
	 *
	 
	 @see QueueObject
	 */
	public boolean addMessage(QueueObject message) {
		if(messageQ.size()<=Constants.P2P_MESSAGE_QUEUE_SIZE) {
			messageQ.offer(message);
			return true;
		}
		else
			return false;
			
	}
	
	/**
	 * Returns a {@link QueueObject} at the beginning of the queue.
	 * 
	 * 
	 
	 @return QueueObject
	 *
	 
	 @see Queue
	 *
	 
	 @see QueueObject
	 */
	public QueueObject removeMessage() {
		if(messageQ.size()!= 0) {			
			return messageQ.poll();
		}
		
		return null;
	}
	
}