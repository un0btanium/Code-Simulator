package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class InitializePrimitiveVariable extends Step {
	
	public String variableName;
	public String variableType;
	public String value;
	
	public InitializePrimitiveVariable(int id, long time, String variableName, String variableType, String value) {
		super(id, time);
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "declarePrimitiveVariable");
		jsonObj.put("id", id);
		jsonObj.put("name", variableName);
		jsonObj.put("valueType", variableType);
		jsonObj.put("value", value);
		return jsonObj;
	}
	
}
