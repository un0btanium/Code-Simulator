package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONObject;

public class ShowError extends SimulationStep {
	
	private String errorMessage;
	
	public ShowError(int id, long time, String errorMessage) {
		super(id, time);
		this.errorMessage = errorMessage;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "highlight");
		jsonObj.put("id", id);
		jsonObj.put("msg", errorMessage);
		return jsonObj;
	}
	
}
