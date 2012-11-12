package org.umit.icm.mobile.connectivity;

public interface AbstractServiceTest {
	public String connect();
	public Service getService();
	public String getServiceURL();
	public String getServicePacket();
}