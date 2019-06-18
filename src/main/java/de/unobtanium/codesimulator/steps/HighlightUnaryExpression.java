package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class HighlightUnaryExpression extends Step {

	public boolean isPostfix;

	String value;
	String valueAfter;

	String valueType;
	
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, int value, int valueAfter) {
		super(id, time);
		this.valueType = "int";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, double value, double valueAfter) {
		super(id, time);
		this.valueType = "double";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, float value, float valueAfter) {
		super(id, time);
		this.valueType = "float";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, char value, char valueAfter) {
		super(id, time);
		this.valueType = "char";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, long value, long valueAfter) {
		super(id, time);
		this.valueType = "long";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, short value, short valueAfter) {
		super(id, time);
		this.valueType = "short";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, byte value, byte valueAfter) {
		super(id, time);
		this.valueType = "byte";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Integer value, Integer valueAfter) {
		super(id, time);
		this.valueType = "Integer";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Double value, Double valueAfter) {
		super(id, time);
		this.valueType = "Double";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Float value, Float valueAfter) {
		super(id, time);
		this.valueType = "Float";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Character value, Character valueAfter) {
		super(id, time);
		this.valueType = "Character";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Long value, Long valueAfter) {
		super(id, time);
		this.valueType = "Long";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Short value, Short valueAfter) {
		super(id, time);
		this.valueType = "Short";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	public HighlightUnaryExpression(int id, long time, boolean isPostfix, Byte value, Byte valueAfter) {
		super(id, time);
		this.valueType = "Byte";
		this.isPostfix = isPostfix;
		this.value = "" + value;
		this.valueAfter = "" + valueAfter;
	}
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("type", "highlightValue");
		jsonObj.put("id", id);
		jsonObj.put("action", "Unary Expression");
		jsonObj.put("valueType", valueType);

		jsonObj.put("isPostfix", "" + isPostfix);
		jsonObj.put("valueBefore", value);
		jsonObj.put("valueAfter", valueAfter);
		
		return jsonObj;
	}
	
}
