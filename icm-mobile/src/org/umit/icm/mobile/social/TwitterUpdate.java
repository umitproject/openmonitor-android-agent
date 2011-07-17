/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
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

package org.umit.icm.mobile.social;

import java.io.IOException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterUpdate {
	private Twitter twitter;
	private RequestToken requestToken;
	private AccessToken accessToken;
	
	public TwitterUpdate() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
		Constants.TWITTER_CONSUMER_KEY_SECRET);
		accessToken = null;
		requestToken = null;		
	}
	
	public void reset() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
		Constants.TWITTER_CONSUMER_KEY_SECRET);
		accessToken = null;
		requestToken = null;
	}
	
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	public void sendTweet(String tweet) throws TwitterException, IOException, RuntimeException {
		if(accessToken != null && Globals.runtimeParameters.getTwitter().equals("On")) {			
			twitter.setOAuthAccessToken(accessToken);
			twitter.updateStatus(tweet);
		}		
	}
	
	public void requestToken(Context context) throws TwitterException, IOException, HttpException {		
			requestToken = twitter.getOAuthRequestToken();	    
		    Intent browserIntent 
		    = new Intent(Intent.ACTION_VIEW
		    		, Uri.parse(requestToken.getAuthorizationURL()));
		    context.startActivity(browserIntent);				   	    	    	   
	}
	
	public void enterPin(String pin) throws TwitterException, IOException, RuntimeException {
		if(requestToken != null) {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			SDCardReadWrite.writeAccessToken(Constants.KEYS_DIR, accessToken);
		}
	}
	
	public void sendTweet(String message, Context context) {		
		Bundle bundle = new Bundle();	
		bundle.putString("twitter"
				, message);	        	
		Intent intent = new Intent("org.umit.icm.mobile.TWITTER_SERVICE");
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}	
	
}