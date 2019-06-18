package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class EmptyStep extends Step {
	
	public EmptyStep() {
		super(-1, -1);
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "empty");
		jsonObj.put("id", id);
		return jsonObj;
	}
	
}
