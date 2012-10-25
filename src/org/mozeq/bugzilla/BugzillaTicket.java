package org.mozeq.bugzilla;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class BugzillaTicket {

	String status = null;
	String product = null;
	int id = 0;
	String shortDesc = null;
	String longDesc = null;
	String component = null;
	String reporter = null;
	String assignedTo = null;
	Date creationTS = null;
	ArrayList<String> ccList = null;
	ArrayList<Integer> dependsOn = null;
	ArrayList<Integer> blockedBy = null;

	HashMap<String, Object> fields = null;

	BugzillaTicket(HashMap<String, Object> fields) {
		this.fields = fields;
	}

	public String getSummary(){
		return (String) fields.get("summary");
	}

	public String getComponent(){

		//System.out.println(((Object[])fields.get("component")).length);
		//System.out.println(((Object[])fields.get("component"))[0]);


		//why the heck is component stored in an array????
		return (String) ((Object[])fields.get("component"))[0];
	}

	public void getCCList(){
		Object[] ccList = (Object[]) fields.get("cc");

		for(int i = 0; i < ccList.length; i++)
			System.out.println(ccList[i]);
	}

	public int getID() {
		return (Integer)fields.get("id");
	}



}
