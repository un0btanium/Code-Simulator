package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONObject;

public class SolveCalculation extends SimulationStep {
	
	private String result;
	
	public SolveCalculation(int id, long time, String result) {
		super(id, time);
		this.result = result;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "calculation");
		jsonObj.put("id", id);
		jsonObj.put("res", result);
		return jsonObj;
	}
	
}
