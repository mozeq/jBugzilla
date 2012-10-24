package org.mozeq.bugzilla;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

public class BugzillaProxy {

	XmlRpcClient client = null;
	String bugzillaURL = null;
	final String xmlrpcSuffix = "/xmlrpc.cgi";

	public BugzillaProxy(String xmlrpcURL) {
		this.bugzillaURL = xmlrpcURL;
	}

	public void connect(String name, String pass) throws MalformedURLException {
		HttpClient httpClient = new HttpClient();
		client = new XmlRpcClient();
		XmlRpcCommonsTransportFactory factory = new XmlRpcCommonsTransportFactory(client);

		factory.setHttpClient(httpClient);
		client.setTransportFactory(factory);


		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		String loginURL = bugzillaURL + xmlrpcSuffix;
		config.setServerURL(new URL(loginURL));
		config.setBasicUserName(name);
		config.setBasicPassword(pass);
		client = new XmlRpcClient();
		client.setConfig(config);
	}

	public BugzillaTicket[] getTickets(int[] ticketIDS) throws XmlRpcException {

		Map<String, int[]> query = new HashMap<String, int[]>();
		query.put("ids", ticketIDS);

		Object result = client.execute("Bug.get", new Object[]{query});

		//[id, time_created, time_changed, attributes].
		@SuppressWarnings("unchecked")
		HashMap<String, Object[]> bugObj = (HashMap<String, Object[]>)result;
		//for (Map.Entry<String, Object[]> e: bugFields.entrySet()) {
		@SuppressWarnings("unchecked")
		HashMap<String, Object>[] bugs = (HashMap<String, Object>[]) bugObj.get("bugs");

		BugzillaTicket[] tickets = new BugzillaTicket[bugs.length];

		for (int i = 0; i < bugs.length; i++){
			HashMap<String, Object> bug = (HashMap<String, Object>) bugs[i];

			tickets[i] = new BugzillaTicket(bug);
		}

		return tickets;

	}

	public BugzillaTicket getTicket(int ticketID) throws XmlRpcException {
		Map<String, String> query = new HashMap<String, String>();
		query.put("ids", ""+ticketID);

		Object result = client.execute("Bug.get", new Object[]{query});

		//[id, time_created, time_changed, attributes].
		@SuppressWarnings("unchecked")
		HashMap<String, Object[]> bugObj = (HashMap<String, Object[]>)result;
		//for (Map.Entry<String, Object[]> e: bugFields.entrySet()) {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> fields = (HashMap<String, Object>)bugObj.get("bugs")[0];

		return new BugzillaTicket(fields);
	}

	public String getURL() {
		return bugzillaURL;
	}

}
