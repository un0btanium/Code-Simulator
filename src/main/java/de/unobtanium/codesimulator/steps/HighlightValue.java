package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class HighlightValue extends Step {

	public String stringValue;

	public String value;
	public String valueType;

	public HighlightValue(int id, long time) {
		super(id, time);
		this.value = "null";
		this.valueType = "null";
	}
	
	public HighlightValue(int id, long time, String value) {
		super(id, time);
		this.value = value;
		this.valueType = "String";
	}
	
	public HighlightValue(int id, long time, int value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "int";
	}
	
	public HighlightValue(int id, long time, double value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "double";
	}
	
	public HighlightValue(int id, long time, boolean value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "boolean";
	}
	
	public HighlightValue(int id, long time, float value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "float";
	}

	public HighlightValue(int id, long time, char value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "char";
	}
	
	public HighlightValue(int id, long time, long value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "long";
	}
	
	public HighlightValue(int id, long time, short value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "short";
	}
	
	public HighlightValue(int id, long time, byte value) {
		super(id, time);
		this.value = "" + value;
		this.valueType = "byte";
	}
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "highlightValue");
		jsonObj.put("id", id);
		
		if (valueType.equals("String")) {
			jsonObj.put("action", "Object");
		} else {
			jsonObj.put("action", "Literal");
		}
		

		jsonObj.put("value", value);
		jsonObj.put("valueType", valueType);
		return jsonObj;
	}
	
}
