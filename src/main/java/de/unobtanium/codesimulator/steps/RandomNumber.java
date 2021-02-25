package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class RandomNumber extends Step {


	public double value;

	public RandomNumber(int id, long time, double value) {
		super(id, time);
		this.value = value;
	}
	
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "random");
		jsonObj.put("id", id);
		jsonObj.put("value", value);
		return jsonObj;
	}
	
}
