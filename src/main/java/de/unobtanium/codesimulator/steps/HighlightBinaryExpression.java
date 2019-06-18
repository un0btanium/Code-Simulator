package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class HighlightBinaryExpression extends Step {

	public String operator;
	
	public String value;
	public String valueType;
	
	public HighlightBinaryExpression(int id, long time, String operator, String value) {
		super(id, time);
		this.operator = operator;
		this.value = value;
		this.valueType = "String";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, int value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "int";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, double value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "double";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, boolean value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "boolean";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, float value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "float";
	}

	public HighlightBinaryExpression(int id, long time, String operator, char value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "char";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, long value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "long";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, short value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "short";
	}
	
	public HighlightBinaryExpression(int id, long time, String operator, byte value) {
		super(id, time);
		this.operator = operator;
		this.value = "" + value;
		this.valueType = "byte";
	}
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("type", "highlightValue");
		jsonObj.put("id", id);

		jsonObj.put("valueType", valueType);
		jsonObj.put("value", value);
		
		if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("*") || operator.equals("%") ) {
			if (valueType.equals("String") && operator.equals("+")) {
				jsonObj.put("action", "String Concatenation");
			} else {
				jsonObj.put("action", "Calculation (Arithmetic Operation)");
			}
		} else if (operator.equals("<") || operator.equals("<=") || operator.equals("==") || operator.equals("!=") || operator.equals(">=") || operator.equals(">")) {
			jsonObj.put("action", "Comparison (Relational Operation)");
		} else if (operator.equals("||") || operator.equals("&&") || operator.equals("!")) {
			jsonObj.put("action", "Conditional (Boolean Operation)");
		} else if (operator.equals(">>") || operator.equals("<<") || operator.equals(">>>")) {
			jsonObj.put("action", "Bit-Shifting Operation");
		} else if (operator.equals("|") || operator.equals("&")  || operator.equals("^")) {
			jsonObj.put("action", "Bitwise Operation");
		}
		
		return jsonObj;
	}
	
}
