package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class PrintLineToConsole extends Step {

	public String message;
	
	public PrintLineToConsole(int id, long time, String message) {
		super(id, time);
		this.message = message + "\n";
	}
	
	@Override
	public String toString() {
		return "PrintLineToConsole: " + id + " " + time + " " + message;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "console");
		jsonObj.put("id", id);
		jsonObj.put("msg", message);
		return jsonObj;
	}
	
}
