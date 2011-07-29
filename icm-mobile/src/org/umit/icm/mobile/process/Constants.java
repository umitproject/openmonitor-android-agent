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

package org.umit.icm.mobile.process;

import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.connectivity.Service;

/**
 * Holds the application wide constants.
 */
public class Constants {
	public static String MY_PUBLIC_KEY_FILE = "myPublicKey.pub";
	public static String ACCESS_TOKEN_FILE = "accessToken.token";
	public static String MY_PRIVATE_KEY_FILE = "myPrivateKey.priv";
	public static String MY_SECRET_KEY_FILE = "mySecretKey.sec";
	public static String MY_CIPHERED_KEY_FILE = "myCipheredKey.ciph";
	public static String PEER_SECRET_KEY_FILE = "SecretKey.sec";
	public static int RSA_KEY_SIZE = 1024;
	public static int AES_KEY_SIZE = 128;
	public static String ICM_ROOT_DIR = "/icm-mobile";
	public static String KEYS_DIR = ICM_ROOT_DIR + "/keys";
	public static String PARAMETERS_DIR = ICM_ROOT_DIR + "/params";
	public static String VERSIONS_DIR = ICM_ROOT_DIR + "/versions";
	public static String INTERVAL_FILE = "interval.param";
	public static String TOKEN_FILE = "token.param";
	public static String AGENTID_FILE = "agentid.param";
	public static String PEERS_FILE = "peers.param";
	public static String SUPER_PEERS_FILE = "superpeers.param";
	public static String EVENTS_FILE = "events.param";
	public static String TESTS_VERSION_FILE = "tests.ver";
	public static String AGENT_VERSION_FILE = "agent.ver";
	public static String SCAN_FILE = "scan.param";
	public static String TWITTER_STATUS_FILE = "twitter.param";
	public static int DEFAULT_SCAN_INTERVAL = 60;
	public static String DEFAULT_SCAN_STATUS = "On";
	public static String DEFAULT_TWITTER_STATUS = "On";
	public static String WEBSITES_DIR = ICM_ROOT_DIR + "/websites";
	public static String WEBSITE_FILE = "-site.web";
	public static String WEBSITES_LIST_FILE = "websites.list";
	public static String SERVICES_DIR = ICM_ROOT_DIR + "/services";
	public static String SERVICE_FILE = ".service";
	public static String PROFILER_DIR = ICM_ROOT_DIR + "/profiler";
	public static String PROFILER_FILE = "profile.csv";
	public static String SERVICES_LIST_FILE = "services.list";
	public static String TESTS_DIR = ICM_ROOT_DIR + "/tests";
	public static String TESTS_FILE = "tests.test";
	public static List<String> WEBSITE_LIST = new ArrayList<String>()
	{  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add("http://www.google.com");
		add("http://www.facebook.com");
		add("http://www.youtube.com");
		add("http://www.twitter.com");
		add("http://www.yahoo.com");
		add("http://www.cnn.com");
		add("http://www.bbc.com");
		add("http://www.gmail.com");
		add("http://www.umitproject.org");
		add("http://www.flickr.com");
		add("http://www.hotmail.com");
	}};
	
	public static int P2P_MESSAGE_QUEUE_SIZE = 10;
	public static String AGGREGATOR_URL = "http://5.icm-dev.appspot.com";
	public static String AGGR_REGISTER_AGENT = "/api/registeragent/";
	public static String AGGR_GET_PEER_LIST = "/api/getpeerlist/";
	public static String AGGR_GET_PEER_SUPER_LIST = "/api/getsuperpeerlist/";
	public static String AGGR_GET_EVENTS = "/api/getevents/";
	public static String AGGR_SEND_WEBSITE_REPORT = "/api/sendwebsitereport/";
	public static String AGGR_SEND_SERVICE_REPORT = "/api/sendservicereport/";
	public static String AGGR_CHECK_VERSION = "/api/checkversion/";
	public static String AGGR_CHECK_TESTS = "/api/checktests/";
	public static String AGGR_WEBSITE_SUGGESTION = "/api/websitesuggestion/";
	public static String AGGR_SERVICE_SUGGESTION = "/api/servicesuggestion/";
	public static String AGGR_TESTS = "/api/tests/";
	public static int DEFAULT_TESTS_VERSION = 1;
	public static int DEFAULT_AGENT_VERSION = 1;
	public static String AGGR_MSG_KEY = "msg";
	public static int MY_TCP_PORT = 5555;
	/*
	public static List<Service> SERVICE_LIST = new ArrayList<Service>()
	{
		private static final long serialVersionUID = 2L;
		private List<Integer> ports = new ArrayList<Integer>();

		{
			ports.add(443);
			ports.add(1863);
			add(new Service("msn", ports,"open", "true"));
			ports.clear();
			ports.add(5222);			
			add(new Service("gtalk", ports,"open", "true"));
			ports.clear();
			ports.add(21);			
			add(new Service("ftp", ports,"open", "true"));
			ports.clear();
			ports.add(80);			
			add(new Service("http", ports,"open", "true"));
			ports.clear();
			ports.add(22);			
			add(new Service("ssh", ports,"open", "true"));
			ports.clear();
			ports.add(5060);			
			add(new Service("sip", ports,"open", "true"));
			ports.clear();
			ports.add(21);			
			add(new Service("ftp", ports,"open", "true"));
			ports.clear();
			ports.add(110);			
			add(new Service("pop3", ports,"open", "true"));
			ports.clear();
			ports.add(143);			
			add(new Service("imap", ports,"open", "true"));
			ports.clear();
			ports.add(25);			
			add(new Service("smtp", ports,"open", "true"));
		
		}};
		*/
	
	public static List<Service> SERVICE_LIST = new ArrayList<Service>()
	{
     	/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private List<Integer> ports = new ArrayList<Integer>();

		{
			ports.add(443);			
			add(new Service("https", ports, "203.135.62.113" ,"open", "true"));
			ports.clear();
			ports.add(80);												
			add(new Service("http", ports, "www.google.com" ,"open", "true"));
			ports.add(21);						
			add(new Service("ftp", ports, "ftp.secureftp-test.com", "open", "true"));
			ports.clear();
			ports.add(995);			
			add(new Service("pop3", ports, "pop.gmail.com", "open", "true"));
			ports.clear();
			ports.add(993);			
			add(new Service("imap", ports, "imap.gmail.com", "open", "true"));
			ports.clear();
			
		
		}};
		public static boolean RUN_PROFILER = false;
		public static long DEFAULT_AGENT_ID = 911;
		public static String DEFAULT_TOKEN = "myToken";
		public static String TWITTER_CONSUMER_KEY = "EE5Tdr3bbOUkeuHhsIZBow";
		public static String TWITTER_CONSUMER_KEY_SECRET 
		= "ZVQzO8f07K9HOyKIPoYsXxunLpASL7CwNWbyYIkzI9Y";
		public static int DEFAULT_GET_EVENTS_INTERVAL = 300;
		public static int DEFAULT_GET_UPDATES_INTERVAL = 550;
		public static int DEFAULT_AGGREGATOR_ACCESS_INTERVAL = 30;
		public static int AUTHENTICATE_PEER_ID = 1;
		public static int AUTHENTICATE_PEER_RESPONSE_ID = 2;
		public static int GET_PEER_LIST_ID = 3;
		public static int GET_PEER_LIST_RESPONSE_ID = 4;
		public static int GET_SUPER_PEER_LIST_ID = 5;
		public static int GET_SUPER_PEER_LIST_RESPONSE_ID = 6;
		public static int UPDATE_AGENT_ID = 7;
		public static int UPDATE_AGENT_RESPONSE_ID = 8;
		public static int UPDATE_TEST_ID = 9;
		public static int UPDATE_TEST_RESPONSE_ID = 10;
}