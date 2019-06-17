package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class Highlight extends Step {
	
	public Highlight(int id, long time) {
		super(id, time);
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "highlight");
		jsonObj.put("id", id);
		return jsonObj;
	}
	
}
