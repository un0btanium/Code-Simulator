package de.unobtanium.codesimulator.codedata;

import org.json.JSONObject;

import com.github.javaparser.ast.Node;

public class NodeData {
	
	int id;
	Node node;
	
	String identifier;
	
	int lineStart;
	int lineEnd;
	int columnStart;
	int columnEnd;
	
	JSONObject jsonObject;
	
	public NodeData(int id, Node node) {
		this.id = id;
		this.node = node;
		
		lineStart = node.getBegin().get().line;
		lineEnd = node.getEnd().get().line;
		
		columnStart = node.getBegin().get().column;
		
		if (lineStart == lineEnd) {
			columnEnd = node.getEnd().get().column;
		} else {
			String[] lines = node.toString().lines().toArray(String[]::new);
			columnEnd = -1;
			for (String str : lines) {
				int length = str.length();
				if (columnEnd < length) {
					columnEnd = length;
				}
			}
		}

		this.jsonObject = new JSONObject()
			.put("id", id)
			.put("columnStart", columnStart)
			.put("columnEnd", columnEnd)
			.put("info", new JSONObject());
	}
	
	
	public JSONObject toJSONObject() {
		return jsonObject;
	}
	
	public void setIdentifier(String identifier, int lineOffset) {
		this.identifier = identifier;
		
		lineStart = lineStart-lineOffset;
		lineEnd = lineEnd-lineOffset;
		
		jsonObject
			.put("identifier", identifier)
			.put("lineStart", lineStart)
			.put("lineEnd", lineEnd);
	}
	
	public void addAdditionalInfo(String key, String value) {
		if (jsonObject.getJSONObject("info").isNull(key)) {
			return; // key already set once
		}
		jsonObject.getJSONObject("info").put(key, value);
	}
	
}
