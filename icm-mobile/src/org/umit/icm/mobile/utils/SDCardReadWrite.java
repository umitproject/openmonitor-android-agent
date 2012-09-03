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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;

import twitter4j.auth.AccessToken;

import android.os.Environment;

/**
 * Provides methods for reading and writing different objects to the SDCard
 * storage.
 */
public class SDCardReadWrite {
	private static File sdCard;
	
	/**
	 * Writes a {@link String} to the specified filename in directory.
	 * 
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	                          
	@param  data  An object of the type {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeString(String fileName
			, String dir, String data) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir, fileName);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    		FileWriter fileWriter = new FileWriter(file, false);
    		try {
    			
    			fileWriter.write(data);
			
    		} catch (Exception e) {
		    throw new RuntimeException("SDCardWrite exception", e);
    		} finally {
			fileWriter.close();
    		}
		}
	
	/**
	 * Appends a {@link String} to the specified filename in directory.
	 * 
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	                          
	@param  data  An object of the type {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeStringAppend(String fileName
			, String dir, String data) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir, fileName);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    		FileWriter fileWriter = new FileWriter(file, true);
    		try {    			
    			fileWriter.write(data);
			
    		} catch (Exception e) {
		    throw new RuntimeException("SDCardWrite exception", e);
    		} finally {
			fileWriter.close();
    		}
		}
	
	/**
	 * Returns a {@link String} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	                          
	@return  {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static String readString(String fileName
			, String dir) throws IOException, RuntimeException{
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
	
	/**
	 * Checks if a certain file in a specified directory exists or not.
	 * 
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          		                          	

	@param  dir  An object of the type {@link String}
	 *   	                          
	
	@return boolean
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static boolean fileExists(String fileName
			, String dir) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, fileName);
    	if(file.exists()){
    		return true;
    	}
    	return false;
	}
	
	/**
	 * Checks if a certain file in a specified directory is empty or not.
	 * 
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          		                          	

	@param  dir  An object of the type {@link String}
	 *   	                          
	
	@return boolean
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static boolean fileNotEmpty(String fileName
			, String dir) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, fileName);
    	if(!file.exists()){
    		return false;
    	}    	
    	FileReader fileReader = new FileReader(file);
    	BufferedReader bufferedReader = new BufferedReader(fileReader);
    	if(bufferedReader.readLine() == null){
    		return false;
    	}
    	return true;
	}
	
	/**
	 * Writes a {@link Website} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link Website}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeWebsite(String dir, Website data) throws IOException , RuntimeException{
		
		ObjectOutputStream objOutStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() + dir);
		keyDir.mkdirs();
    	File file = new File(keyDir, data.getUrl()+ Constants.WEBSITE_FILE);
    	
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));
			objOutStream.writeObject(data.getUrl());
			objOutStream.writeObject(data.getCheck());
			objOutStream.writeObject(data.getStatus());
			objOutStream.writeObject(data.getTestID());
			objOutStream.writeObject(Long.toString(data.getExecuteAtTimeUTC()));
    	} catch (Exception e) {
  		    throw new RuntimeException("writeWebsite exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link Website} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  url  An object of the type {@link String}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static Website readWebsite(String dir
			, String url) throws IOException , RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, url
    			+ Constants.WEBSITE_FILE);
    	Website website = new Website();
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
	    	    website.setUrl((String) objInputStream.readObject());
	    	    website.setCheck((String) objInputStream.readObject());
	    	    website.setStatus((String) objInputStream.readObject());
	    	    website.setTestID((String)objInputStream.readObject());
	    	    website.setExecuteAtTimeUTC(Long.parseLong((String)objInputStream.readObject()));
	    	    
	    	    return website;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("readWebsite exception", e);
  	  	} finally {
  		  objInputStream.close();
  	  	}
	}
	
	/**
	 * Checks if the SDCard is present or not.
	 * 
	 *	 
	
	@return boolean	                          	
	*
	
	@see         Environment
	 */
	public static boolean checkSDCard() {
		
		String storageState = android.os.Environment.getExternalStorageState();
		String mediaMounted = android.os.Environment.MEDIA_MOUNTED;
		if(storageState.equals(mediaMounted))
			return true;
		return false;
	}
	
	/**
	 * Writes a {@link WebsiteReport} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link WebsiteReport}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         FileOutputStream
	 */
	public static void writeWebsiteReport(String dir
			, WebsiteReport data) throws IOException, RuntimeException{
		OutputStream outputStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, data.getReport().getWebsiteURL().substring(11).replaceAll("/", "-")
    			+ Constants.WEBSITE_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			outputStream = new FileOutputStream(file);
			data.writeTo(outputStream);
			
    	} catch (Exception e) {
  		    throw new RuntimeException("write website exception", e);
  	  	} finally {
    		outputStream.close();
    	}
	}
	
	/**
	 * Returns a {@link WebsiteReport} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 
	                                              
	@return  {@link WebsiteReport}
	 *   

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  url  An object of the type {@link String}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static WebsiteReport readWebsiteReport(String dir
			, String url) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, url.substring(11).replaceAll("/", "-")
    			+ Constants.WEBSITE_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	
  	  	try {
	      		WebsiteReport websiteReport 
	      		= WebsiteReport.parseFrom(inputStream);
	      		return websiteReport;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read website exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link Website} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link Website}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeWebsitesList(String dir
			, List<Website> data) throws IOException, RuntimeException{
		ObjectOutputStream objOutStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.WEBSITES_LIST_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));
			objOutStream.writeObject(data);
    	} catch (Exception e) {
  		    throw new RuntimeException("write websites list exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link Website} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link Website}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	@SuppressWarnings("unchecked")
	public static List<Website> readWebsitesList(String dir
			) throws IOException, RuntimeException{
		List<Website> websites = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.WEBSITES_LIST_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
	    	    websites = ((List<Website>) objInputStream.readObject());
	      		return websites;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read website exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link Service} object to the default 
	 * filename in directory.
	 * 
	 *	 	                          	

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  data  An object of the type {@link String}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeService(String dir
			, Service data) throws IOException , RuntimeException{
		ObjectOutputStream objOutStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir, data.getName()
    			+ Constants.SERVICE_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));
			objOutStream.writeObject(data.getCheck());
			objOutStream.writeObject(data.getName());
			objOutStream.writeObject(data.getIp());
			objOutStream.writeObject(data.getPort());
			objOutStream.writeObject(data.getStatus());
			objOutStream.writeObject(data.getTestID());
			objOutStream.writeObject(Long.toString(data.getExecuteAtTimeUTC()));
    	} catch (Exception e) {
  		    throw new RuntimeException("writeService exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link Service} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link Service}
	 *   

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  name  An object of the type {@link String}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */

	public static Service readService(String dir
			, String name) throws IOException , RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, name
    			+ Constants.SERVICE_FILE);
    	Service service = new Service();
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
	    	    service.setCheck((String) objInputStream.readObject());
	    	    service.setName((String) objInputStream.readObject());
	    	    service.setIp((String) objInputStream.readObject());
	    	    service.setPort((Integer) objInputStream.readObject());
	    	    service.setStatus((String) objInputStream.readObject());
	    	    service.setTestID((String)objInputStream.readObject());
	    	    service.setExecuteAtTimeUTC(Long.parseLong((String)objInputStream.readObject()));
	    	    
	    	    return service;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("readService exception", e);
  	  	} finally {
  		  objInputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link Service} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link Service}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeServicesList(String dir
			, List<Service> data) throws IOException, RuntimeException{
		ObjectOutputStream objOutStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.SERVICES_LIST_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));
			objOutStream.writeObject(data);
    	} catch (Exception e) {
  		    throw new RuntimeException("write services list exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link Service} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link Service}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	@SuppressWarnings("unchecked")
	public static List<Service> readServicesList(String dir
			) throws IOException, RuntimeException{
		List<Service> services = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.SERVICES_LIST_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
	    	    services = ((List<Service>) objInputStream.readObject());
	      		return services;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read services list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link AgentData} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link AgentData}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	
	public static void writePeersList(String dir
			, List<AgentData> data) throws IOException, RuntimeException{
		OutputStream outputStream = null;		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.PEERS_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(21)
    	.setCurrentVersionNo(21)
    	.build();
    	
    	GetPeerListResponse getPeerListResponse 
    	= GetPeerListResponse.newBuilder()
    	.setHeader(responseHeader)
    	.addAllKnownPeers(data)    	
    	.build();
    	    	
    	try {
    		outputStream = new FileOutputStream(file);
    		getPeerListResponse.writeTo(outputStream);				    												 				 
				
    	} catch (Exception e) {
  		    throw new RuntimeException("write peers list exception", e);
  	  	} finally {
    		outputStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link AgentData} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link AgentData}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static List<AgentData> readPeersList(String dir
			) throws IOException, RuntimeException{		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.PEERS_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());  	  	 	   
  	  	try {
  	  		GetPeerListResponse getPeerListResponse 
  	  		= GetPeerListResponse.parseFrom(inputStream); 	  	
	    	
	      	return getPeerListResponse.getKnownPeersList();
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read peers list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link AgentData} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link AgentData}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeSuperPeersList(String dir
			, List<AgentData> data) throws IOException, RuntimeException{
		OutputStream outputStream = null;		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.SUPER_PEERS_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(21)
    	.setCurrentVersionNo(21)
    	.build();
    	
    	GetSuperPeerListResponse getSuperPeerListResponse 
    	= GetSuperPeerListResponse.newBuilder()
    	.setHeader(responseHeader)
    	.addAllKnownSuperPeers(data)    	
    	.build();
    	    	
    	try {
    		outputStream = new FileOutputStream(file);
    		getSuperPeerListResponse.writeTo(outputStream);								 
				
    	} catch (Exception e) {
  		    throw new RuntimeException("write super peers list exception", e);
  	  	} finally {
    		outputStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link AgentData} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link AgentData}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static List<AgentData> readSuperPeersList(String dir
			) throws IOException, RuntimeException{		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.SUPER_PEERS_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());  	  	
  	  	try {
  	  		GetSuperPeerListResponse getSuperPeerListResponse 
  	  		= GetSuperPeerListResponse.parseFrom(inputStream); 	  	
	    	
	      	return getSuperPeerListResponse.getKnownSuperPeersList();
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read super peers list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link Event} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link Event}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         ObjectOutputStream
	 *
	 
	@see         Environment
	 */
	public static void writeEventsList(String dir
			, List<Event> data) throws IOException, RuntimeException{
		OutputStream outputStream = null;		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.EVENTS_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(21)
    	.setCurrentVersionNo(21)
    	.build();
    	
    	GetEventsResponse getEventsResponse 
    	= GetEventsResponse.newBuilder()
    	.addAllEvents(data)
    	.setHeader(responseHeader)
    	.build();
    	    	
    	try {
		
			 outputStream = new FileOutputStream(file);
     		 getEventsResponse.writeTo(outputStream);	
				
    	} catch (Exception e) {
  		    throw new RuntimeException("write events list exception", e);
  	  	} finally {
    		outputStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link Event} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link Event}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         ObjectInputStream
	 *
	 
	@see         Environment
	 */
	public static List<Event> readEventsList(String dir
			) throws IOException, RuntimeException{		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.EVENTS_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());  	  	
  	  	try {
  	  		GetEventsResponse getEventsResponse
  	  		= GetEventsResponse.parseFrom(inputStream); 	  	
	    	
	      	return getEventsResponse.getEventsList();
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read events list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link Test} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link Test}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         ObjectOutputStream
	 *
	 
	@see         Environment
	 */
	public static void writeTestsList(String dir
			, List<Test> data) throws IOException, RuntimeException{
		ObjectOutputStream objOutStream = null;		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, Constants.TESTS_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(21)
    	.setCurrentVersionNo(21)
    	.build();
    	
    	NewTestsResponse newTestsResponse = NewTestsResponse.newBuilder()
    	.addAllTests(data)
    	.setTestVersionNo(10)
    	.setHeader(responseHeader)
    	.build();
    	    	
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));									
			 newTestsResponse.writeTo(objOutStream);				 
				
    	} catch (Exception e) {
  		    throw new RuntimeException("write tests list exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link Test} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link Test}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see		 ObjectInputStream         
	 *
	 
	@see         Environment
	 */
	public static List<Test> readTestsList(String dir
			) throws IOException, RuntimeException{		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, Constants.TESTS_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
  	  		NewTestsResponse newTestsResponse
  	  		= NewTestsResponse.parseFrom(objInputStream); 	  	
	    	
	      	return newTestsResponse.getTestsList();
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read tests list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link WebsiteReport} to the default filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link WebsiteReport}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         FileOutputStream
	 */
	public static void writeServiceReport(String dir
			, ServiceReport data) throws IOException, RuntimeException{
		OutputStream outputStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, data.getReport().getServiceName()
    			+ Constants.SERVICE_FILE);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			outputStream = new FileOutputStream(file);
			data.writeTo(outputStream);
			
    	} catch (Exception e) {
  		    throw new RuntimeException("write service report exception", e);
  	  	} finally {
    		outputStream.close();
    	}
	}
	
	/**
	 * Returns a {@link WebsiteReport} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 
	                                              
	@return  {@link WebsiteReport}
	 *   

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  url  An object of the type {@link String}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static ServiceReport readServiceReport(String dir
			, String url) throws IOException, RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, url
    			+ Constants.SERVICE_FILE);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	
  	  	try {
	      		ServiceReport serviceReport 
	      		= ServiceReport.parseFrom(inputStream);
	      		return serviceReport;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read service report exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link AccessToken} object to the default 
	 * filename in directory.
	 * 
	 *	 	                          	

	@param  dir  An object of the type {@link String}
	 *  
	 
	@param  data  An object of the type {@link AccessToken}
	 *  
	 	                          	                          
	@see         File
	 *
	 
	@see         FileWriter
	 *
	 
	@see         Environment
	 */
	public static void writeAccessToken(String dir
			, AccessToken data) throws IOException , RuntimeException{
		ObjectOutputStream objOutStream = null;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir,
    			Constants.ACCESS_TOKEN_FILE);    	
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));
			objOutStream.writeObject(data.getToken());
			objOutStream.writeObject(data.getTokenSecret());
    	} catch (Exception e) {
  		    throw new RuntimeException("writeAccessToken exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link AccessToken} object of the file content from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link AccessToken}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         FileReader
	 *
	 
	@see         Environment
	 */
	public static AccessToken readAccessToken(String dir) throws IOException , RuntimeException{
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir, 
    			Constants.ACCESS_TOKEN_FILE);    	
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
	    	    String token = ((String) objInputStream.readObject());	    	    
	    	    String tokenSecret = ((String) objInputStream.readObject());
	    	    return new AccessToken(token, tokenSecret);
  	  	} catch (Exception e) {
  		    throw new RuntimeException("readToken exception", e);
  	  	} finally {
  		  objInputStream.close();
  	  	}
	}
	
	/**
	 * Writes a {@link List} of {@link String} to the given filename in directory.
	 * 
	 *	 	                          	
	                          
	@param  data  An object of the type {@link List} of {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *   	                          
	                          
	@see         File
	 *
	 
	@see         ObjectOutputStream
	 *
	 
	@see         Environment
	 */
	public static void writeStringList(String dir, String filename
			, List<String> data) throws IOException, RuntimeException{
		ObjectOutputStream objOutStream = null;		
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
		keyDir.mkdirs();
    	File file = new File(keyDir
    			, filename);
    	if(!file.exists()){
    		file.createNewFile();
    	}    	    	
    	    	
    	try {
			objOutStream = new ObjectOutputStream(
				    new BufferedOutputStream(new FileOutputStream(file)));									
				objOutStream.writeObject(data);				 				
    	} catch (Exception e) {
  		    throw new RuntimeException("write string list exception", e);
  	  	} finally {
    		objOutStream.close();
    	}
	}
	
	/**
	 * Returns a {@link List} of {@link String} object from the specified 
	 * filename in directory.
	 * 
	 *	 	                          	
	                          
	@return  {@link List} of {@link String}
	 *   

	@param  dir  An object of the type {@link String}
	 *  	 	
	 	                          	                          
	@see         File
	 *
	 
	@see         ObjectInputStream
	 *
	 
	@see         Environment
	 */
	@SuppressWarnings("unchecked")
	public static List<String> readStringList(String dir, String filename
			) throws IOException, RuntimeException{
		List<String> list;
		sdCard = Environment.getExternalStorageDirectory();
		File keyDir = new File (sdCard.getAbsolutePath() 
    			+ dir);
    	File file = new File(keyDir
    			, filename);
    	InputStream inputStream = new FileInputStream(file.toString());
  	  	ObjectInputStream objInputStream =
  	    new ObjectInputStream(new BufferedInputStream(inputStream));
  	  	try {
  	  		list = ((List<String>) objInputStream.readObject());
  	  		return list;
  	  	} catch (Exception e) {
  		    throw new RuntimeException("read String list exception", e);
  	  	} finally {
  		  inputStream.close();
  	  	}
	}
	

}