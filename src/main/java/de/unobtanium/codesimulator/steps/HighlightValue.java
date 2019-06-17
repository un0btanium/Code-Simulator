package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public class HighlightValue extends Step {
	
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
	
	public HighlightValue(int id, long time, int value) {
		super(id, time);
		this.isInt = true;
		this.intValue = value;
	}
	
	public HighlightValue(int id, long time, long value) {
		super(id, time);
		this.isLong = true;
		this.longValue = value;
	}
	
	public HighlightValue(int id, long time, short value) {
		super(id, time);
		this.isShort = true;
		this.shortValue = value;
	}
	
	public HighlightValue(int id, long time, byte value) {
		super(id, time);
		this.isByte = true;
		this.byteValue = value;
	}
	
	public HighlightValue(int id, long time, double value) {
		super(id, time);
		this.isDouble = true;
		this.doubleValue = value;
	}
	
	public HighlightValue(int id, long time, float value) {
		super(id, time);
		this.isFloat = true;
		this.floatValue = value;
	}
	
	public HighlightValue(int id, long time, String value) {
		super(id, time);
		this.isString = true;
		this.stringValue = value;
	}

	public HighlightValue(int id, long time, char value) {
		super(id, time);
		this.isChar = true;
		this.charValue = value;
	}
	
	public HighlightValue(int id, long time, boolean value) {
		super(id, time);
		this.isBoolean = true;
		this.booleanValue = value;
	}
	
	
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("type", "highlightValue");
		jsonObj.put("id", id);
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
