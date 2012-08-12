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
import org.umit.icm.mobile.connectivity.Website;

/**
 * Holds the application wide constants.
 */
public class Constants {
	public static String MY_PUBLIC_KEY_FILE = "myPublicKey.pub";
	public static String MY_DH_PUBLIC_KEY_FILE = "myDHPublicKey.pub";
	public static String ACCESS_TOKEN_FILE = "accessToken.token";
	public static String MY_PRIVATE_KEY_FILE = "myPrivateKey.priv";
	public static String MY_DH_PRIVATE_KEY_FILE = "myDHPrivateKey.priv";
	public static String MY_SECRET_KEY_FILE = "mySecretKey.sec";
	public static String MY_CIPHERED_KEY_FILE_MOD = "myCipheredKeyMod.ciph";
	public static String MY_CIPHERED_KEY_FILE_EXP = "myCipheredKeyExp.ciph";
	public static String AGGR_PUBLIC_KEY_FILE = "aggrPublicKey.pub";
	public static String PEER_SECRET_KEY_FILE = "SecretKey.sec";
	public static int RSA_KEY_SIZE = 1024;
	public static int AES_KEY_SIZE = 256;
	public static int AES_BLOCK_SIZE =AES_KEY_SIZE/8;
	public static byte  AES_DEFAULT_PADDING='{';
	public static double averageThroughput=0;
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
	public static int DEFAULT_SCAN_INTERVAL = 5;
	public static String DEFAULT_SCAN_STATUS = "On";
	public static String DEFAULT_TWITTER_STATUS = "Off";
	public static String WEBSITES_DIR = ICM_ROOT_DIR + "/websites";
	public static String WEBSITE_FILE = "-site.web";
	public static String WEBSITES_LIST_FILE = "websites.list";
	public static String AUTHENTICATED_PEERS_FILE = "authenticatedPeers.list";
	public static String SERVICES_DIR = ICM_ROOT_DIR + "/services";
	public static String SERVICE_FILE = ".service";
	public static String PROFILER_DIR = ICM_ROOT_DIR + "/profiler";
	public static String PROFILER_FILE = "profile.csv";
	public static String SERVICES_LIST_FILE = "services.list";
	public static String TESTS_DIR = ICM_ROOT_DIR + "/tests";
	public static String TESTS_FILE = "tests.test";
	public static List<Website> WEBSITE_LIST = new ArrayList<Website>()
	{ 

	/**
		 * 
		 */
		private static final long serialVersionUID = 6933062698660638968L;

	{
		add(new Website("http://www.google.com", "false", "true", 1001, 0));
		add(new Website("http://www.facebook.com", "false", "true", 1002, 0));
		add(new Website("http://www.youtube.com", "false", "true", 1003, 0));
		add(new Website("http://www.twitter.com", "false", "true", 1004, 0));
		add(new Website("http://www.yahoo.com", "false", "true", 1005, 0));
		add(new Website("http://www.cnn.com", "false", "true", 1006, 0));
		add(new Website("http://www.bbc.com", "false", "true", 1007, 0));
		add(new Website("http://www.gmail.com", "false", "true", 1008, 0));
		add(new Website("http://www.umitproject.org", "false", "true", 1009, 0));
		add(new Website("http://www.flickr.com", "false", "true",1010, 0));
		add(new Website("http://www.hotmail.com", "false", "true", 1011, 0));
	}};
	
	public static int P2P_MESSAGE_QUEUE_SIZE = 10;
	public static String AGGREGATOR_URL = "http://10.0.2.2:8000";	
	public static String AGGR_REGISTER_AGENT = "/api/registeragent/";
	public static String AGGR_GET_PEER_LIST = "/api/getpeerlist/";
	public static String AGGR_CHECK_AGGREGATOR = "/api/checkaggregator/";	
	public static String AGGR_GET_PEER_SUPER_LIST = "/api/getsuperpeerlist/";
	public static String AGGR_GET_EVENTS = "/api/getevents/";
	public static String AGGR_SEND_WEBSITE_REPORT = "/api/sendwebsitereport/";
	public static String AGGR_SEND_SERVICE_REPORT = "/api/sendservicereport/";
	public static String AGGR_CHECK_VERSION = "/api/checkversion/";
	public static String AGGR_CHECK_TESTS = "/api/checktests/";
	public static String AGGR_WEBSITE_SUGGESTION = "/api/websitesuggestion/";
	public static String AGGR_SERVICE_SUGGESTION = "/api/servicesuggestion/";
	public static String AGGR_TESTS = "/api/tests/";
	public static String AGGR_LOGIN_1 = "/api/loginagent/";
	public static String AGGR_LOGIN_2 = "/api/loginagent2/";
	public static String AGGR_LOGOUT = "/api/logoutagent/";
	public static String AGGR_GET_BANLIST = "/api/get_banlist/";
	public static String AGGR_GET_BANNETS = "/api/get_bannets/";
	public static String AGGR_GENERATE_SECRET_KEY = "/api/generatesecretkey/";
	public static String AGGR_GET_TOKEN_ASYMMETRIC_KEYS = "/api/gettokenandasymmetrickeys/";
	public static String AGGR_REGISTER_USER = "http://east1.openmonitor.org/accounts/register/";
	public static int DEFAULT_TESTS_VERSION = 1;
	public static int DEFAULT_AGENT_VERSION = 1;
	public static String AGGR_MSG_KEY = "msg";
	public static int MY_TCP_PORT = 5555;		
	public static List<Service> SERVICE_LIST = new ArrayList<Service>()
	{
     	/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private List<Integer> ports = new ArrayList<Integer>();

		{
			ports.add(443);			
			add(new Service("https", ports, "203.135.62.113" ,"open", "true", 2001, 0));
			ports.clear();
			ports.add(80);												
			add(new Service("http", ports, "www.google.com" ,"open", "true", 2001, 0));
			ports.add(21);						
			add(new Service("ftp", ports, "ftp.secureftp-test.com", "open", "true", 2001, 0));
			ports.clear();
			ports.add(995);			
			add(new Service("pop3", ports, "pop.gmail.com", "open", "true", 2001, 0));
			ports.clear();
			ports.add(993);			
			add(new Service("imap", ports, "imap.gmail.com", "open", "true", 2001, 0));
			ports.clear();
			ports.add(1863);			
			add(new Service("msn", ports, "messenger.hotmail.com", "open", "true", 2001, 0));
			ports.clear();		
			ports.add(5222);			
			add(new Service("gtalk", ports, "talk.google.com", "open", "true", 2001, 0));
			ports.clear();	
		
		}};
		public static boolean RUN_PROFILER = false;
		public static long DEFAULT_AGENT_ID = 911;
		public static long AGENT_ID= 0;
		public static String DEFAULT_TOKEN = "myToken";
		public static String TWITTER_CONSUMER_KEY = "EE5Tdr3bbOUkeuHhsIZBow";
		public static String TWITTER_CONSUMER_KEY_SECRET 
		= "ZVQzO8f07K9HOyKIPoYsXxunLpASL7CwNWbyYIkzI9Y";
		public static int DEFAULT_GET_EVENTS_INTERVAL = 5;
		public static int DEFAULT_GET_UPDATES_INTERVAL = 10;
		public static int DEFAULT_AGGREGATOR_ACCESS_INTERVAL = 1;		
		public static final String BING_SEARCH_API = "930F43D8FAD299B1368D22FB4B32467E11EC1BE8";
		public static final String TWITTER_HASHTAG = "#ICMMobileAgent";
		public static String SUPER_GET_PEER_LIST = "/api/getpeerlist/";		
		public static String SUPER_GET_PEER_SUPER_LIST = "/api/getsuperpeerlist/";
		public static String SUPER_GET_EVENTS = "/api/getevents/";
		public static String SUPER_SEND_WEBSITE_REPORT = "/api/sendwebsitereport/";
		public static String SUPER_SEND_SERVICE_REPORT = "/api/sendservicereport/";
		public static String SUPER_CHECK_VERSION = "/api/checkversion/";
		public static String SUPER_CHECK_TESTS = "/api/checktests/";
		public static String SUPER_WEBSITE_SUGGESTION = "/api/websitesuggestion/";
		public static String SUPER_SERVICE_SUGGESTION = "/api/servicesuggestion/";
		public static String SUPER_TESTS = "/api/tests/";
		public static String SUPER_AUTHENTICATE_PEER = "/api/authenticatepeer/";
		public static String SUPER_LOGIN = "/api/login/";
		public static String SUPER_MSG_KEY = "msg";
		public static String AGENT_TYPE = "MOBILE";
		public static int AGENT_TYPE_NUMBER = 3;
		public static int MAX_PEERS = 10;
		public static int MAX_SUPER_PEERS = 2;	
		public static boolean P2P_ENCRYPTION = false;
		public static boolean AGGR_ENCRYPTION = false;
}
