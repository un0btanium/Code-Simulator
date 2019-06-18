package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class DeclarePrimitiveVariable extends Step {
	
	public String variableName;
	public String variableType;
	
	public DeclarePrimitiveVariable(int id, long time, String variableName, String variableType) {
		super(id, time);
		this.variableName = variableName;
		this.variableType = variableType;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "declarePrimitiveVariable");
		jsonObj.put("id", id);
		jsonObj.put("action", "Variable Declaration");
		jsonObj.put("name", variableName);
		jsonObj.put("valueType", variableType);
		return jsonObj;
	}
	
}
