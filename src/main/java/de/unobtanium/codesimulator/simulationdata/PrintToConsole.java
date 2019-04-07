package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONObject;

public class PrintToConsole extends SimulationStep {

	public String message;
	
	public PrintToConsole(int id, long time, String message) {
		super(id, time);
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "PrintToConsole: " + id + " " + time + " " + message;
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
