package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class AssignVariable extends Step {
	
	public String variableName;

	
	public String stringValue;
	
	public int intValue;
	public double doubleValue;
	public boolean booleanValue;
	public float floatValue;
	public char charValue;
	public long longValue;
	public short shortValue;
	public byte byteValue;

	public Integer intWrapperValue;
	public Double doubleWrapperValue;
	public Boolean booleanWrapperValue;
	public Float floatWrapperValue;
	public Character charWrapperValue;
	public Long longWrapperValue;
	public Short shortWrapperValue;
	public Byte byteWrapperValue;


	public boolean isString = false;
	
	public boolean isInt = false;
	public boolean isDouble = false;
	public boolean isBoolean = false;
	public boolean isFloat = false;
	public boolean isChar = false;
	public boolean isLong = false;
	public boolean isShort = false;
	public boolean isByte = false;

	public boolean isIntWrapper = false;
	public boolean isDoubleWrapper = false;
	public boolean isBooleanWrapper = false;
	public boolean isFloatWrapper = false;
	public boolean isCharWrapper = false;
	public boolean isLongWrapper = false;
	public boolean isShortWrapper = false;
	public boolean isByteWrapper = false;
	
	
	// TODO different operators!
	
	public AssignVariable(int id, long time, String variableName, String value) {
		super(id, time);
		this.variableName = variableName;
		this.isString = true;
		this.stringValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, int value) {
		super(id, time);
		this.variableName = variableName;
		this.isInt = true;
		this.intValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, double value) {
		super(id, time);
		this.variableName = variableName;
		this.isDouble = true;
		this.doubleValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, boolean value) {
		super(id, time);
		this.variableName = variableName;
		this.isBoolean = true;
		this.booleanValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, float value) {
		super(id, time);
		this.variableName = variableName;
		this.isFloat = true;
		this.floatValue = value;
	}

	public AssignVariable(int id, long time, String variableName, char value) {
		super(id, time);
		this.variableName = variableName;
		this.isChar = true;
		this.charValue = value;
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
	
	public AssignVariable(int id, long time, String variableName, Integer value) {
		super(id, time);
		this.variableName = variableName;
		this.isIntWrapper = true;
		this.intWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Double value) {
		super(id, time);
		this.variableName = variableName;
		this.isDoubleWrapper = true;
		this.doubleWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Boolean value) {
		super(id, time);
		this.variableName = variableName;
		this.isBooleanWrapper = true;
		this.booleanWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Float value) {
		super(id, time);
		this.variableName = variableName;
		this.isFloatWrapper = true;
		this.floatWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Character value) {
		super(id, time);
		this.variableName = variableName;
		this.isCharWrapper = true;
		this.charWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Long value) {
		super(id, time);
		this.variableName = variableName;
		this.isLongWrapper = true;
		this.longWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Short value) {
		super(id, time);
		this.variableName = variableName;
		this.isShortWrapper = true;
		this.shortWrapperValue = value;
	}
	
	public AssignVariable(int id, long time, String variableName, Byte value) {
		super(id, time);
		this.variableName = variableName;
		this.isByteWrapper = true;
		this.byteWrapperValue = value;
	}

	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("type", "variableAssignment");
		jsonObj.put("id", id);
		jsonObj.put("name", variableName);
		jsonObj.put("valueType", getType());
		
		if (isString) {
			jsonObj.put("value", stringValue);
		} else if (isInt) {
			jsonObj.put("value", intValue);
		} else if (isDouble) {
			jsonObj.put("value", doubleValue);
		} else if (isBoolean) {
			jsonObj.put("value", booleanValue);
		} else if (isFloat) {
			jsonObj.put("value", floatValue);
		} else if (isChar) {
			jsonObj.put("value", charValue);
		} else if (isLong) {
			jsonObj.put("value", longValue);
		} else if (isShort) {
			jsonObj.put("value", shortValue);
		} else if (isByte) {
			jsonObj.put("value", byteValue);
		} else if (isIntWrapper) {
			jsonObj.put("value", intValue);
		} else if (isDoubleWrapper) {
			jsonObj.put("value", doubleValue);
		} else if (isBooleanWrapper) {
			jsonObj.put("value", booleanValue);
		} else if (isFloatWrapper) {
			jsonObj.put("value", floatValue);
		} else if (isCharWrapper) {
			jsonObj.put("value", charValue);
		} else if (isLongWrapper) {
			jsonObj.put("value", longValue);
		} else if (isShortWrapper) {
			jsonObj.put("value", shortValue);
		} else if (isByteWrapper) {
			jsonObj.put("value", byteValue);
		}
		
		return jsonObj;
	}
	
	public String getType() {
		if (isString) {
			return "String";
		} else if (isInt) {
			return "int";
		} else if (isDouble) {
			return "double";
		} else if (isBoolean) {
			return "boolean";
		} else if (isFloat) {
			return "float";
		} else if (isChar) {
			return "char";
		} else if (isLong) {
			return "long";
		} else if (isShort) {
			return "short";
		} else if (isByte) {
			return "byte";
		} else if (isIntWrapper) {
			return "Integer";
		} else if (isDoubleWrapper) {
			return "Double";
		} else if (isBooleanWrapper) {
			return "Boolean";
		} else if (isFloatWrapper) {
			return "Float";
		} else if (isCharWrapper) {
			return "Character";
		} else if (isLongWrapper) {
			return "Long";
		} else if (isShortWrapper) {
			return "Short";
		} else if (isByteWrapper) {
			return "Byte";
		}
		return "unknown";
	}
	
}
