package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONObject;

public class ReadString extends SimulationStep {
	
	public ReadString(int id, long time) {
		super(id, time);
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "readString");
		jsonObj.put("id", id);
		return jsonObj;
	}
	
}
