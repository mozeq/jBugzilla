package org.mozeq.bugzilla;

import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;

public class jBugzillaTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BugzillaProxy bz = new BugzillaProxy("https://bugzilla.redhat.com");
		try {
			bz.connect("username", "password");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BugzillaTicket bzTicket = bz.getTicket(867118);
			String ticketURL = bz.getURL() + "/show_bug.cgi?id=" + bzTicket.getID();
			System.out.println("["+ bzTicket.getComponent() +"] " + bzTicket.getSummary() + " <"+ ticketURL +">");
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
