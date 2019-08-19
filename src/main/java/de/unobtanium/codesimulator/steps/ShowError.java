package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class ShowError extends Step {
	
	private String errorMessage;
	private String identifier;
	private long line;
	
	public ShowError(int id, long time, String errorMessage) {
		super(id, time);
		this.errorMessage = errorMessage;
		this.identifier = null;
		this.line = -1;
	}
	
	public ShowError(int id, long time, String errorMessage, String identifier, long line) {
		super(id, time);
		this.errorMessage = errorMessage;
		this.identifier = identifier;
		this.line = line;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "error");
		jsonObj.put("id", id);
		jsonObj.put("msg", errorMessage);
		if (identifier != null) {
			jsonObj.put("identifier", identifier);
			jsonObj.put("line", line);
		}
		return jsonObj;
	}
	
}
