package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class AssignVariable extends Step {
	
	public String variableName;
	
	public int intValue;
	public long longValue;
	public short shortValue;
	public byte byteValue;
	public double doubleValue;
	public float floatValue;
	public String stringValue;
	public char charValue;
	public boolean booleanValue;

	public boolean isInt = false;
	public boolean isLong = false;
	public boolean isShort = false;
	public boolean isByte = false;
	public boolean isDouble = false;
	public boolean isFloat = false;
	public boolean isString = false;
	public boolean isChar = false;
	public boolean isBoolean = false;
	
	// TODO different operators!
	
	public AssignVariable(int id, long time, String variableName, int value) {
		super(id, time);
		this.variableName = variableName;
		this.isInt = true;
		this.intValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, long value) {
		super(id, time);
		this.variableName = variableName;
		this.isLong = true;
		this.longValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, short value) {
		super(id, time);
		this.variableName = variableName;
		this.isShort = true;
		this.shortValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, byte value) {
		super(id, time);
		this.variableName = variableName;
		this.isByte = true;
		this.byteValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, double value) {
		super(id, time);
		this.variableName = variableName;
		this.isDouble = true;
		this.doubleValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, float value) {
		super(id, time);
		this.variableName = variableName;
		this.isFloat = true;
		this.floatValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, String value) {
		super(id, time);
		this.variableName = variableName;
		this.isString = true;
		this.stringValue = value;
	}

	public AssignVariable(int id, long time, String variableName, char value) {
		super(id, time);
		this.variableName = variableName;
		this.isChar = true;
		this.charValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, boolean value) {
		super(id, time);
		this.variableName = variableName;
		this.isBoolean = true;
		this.booleanValue = value;
	}
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("type", "variableAssignment");
		jsonObj.put("id", id);
		jsonObj.put("name", variableName);
		jsonObj.put("valueType", getType());
		
		if (isInt) {
			jsonObj.put("value", intValue);
		} else if (isLong) {
			jsonObj.put("value", longValue);
		} else if (isShort) {
			jsonObj.put("value", shortValue);
		} else if (isByte) {
			jsonObj.put("value", byteValue);
		} else if (isDouble) {
			jsonObj.put("value", doubleValue);
		} else if (isFloat) {
			jsonObj.put("value", floatValue);
		} else if (isString) {
			jsonObj.put("value", stringValue);
		} else if (isChar) {
			jsonObj.put("value", charValue);
		} else if (isBoolean) {
			jsonObj.put("value", booleanValue);
		}
		
		return jsonObj;
	}
	
	public String getType() {
		if (isInt) {
			return "int";
		} else if (isLong) {
			return "long";
		} else if (isShort) {
			return "short";
		} else if (isByte) {
			return "byte";
		} else if (isDouble) {
			return "double";
		} else if (isFloat) {
			return "float";
		} else if (isString) {
			return "String";
		} else if (isChar) {
			return "char";
		} else if (isBoolean) {
			return "boolean";
		}
		return "unknown";
	}
	
}
