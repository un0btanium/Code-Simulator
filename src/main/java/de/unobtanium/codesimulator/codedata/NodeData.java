package de.unobtanium.codesimulator.codedata;

import org.json.JSONObject;

import com.github.javaparser.ast.Node;

public class NodeData {
	
	int id;
	Node node;
	
	int lineStart;
	int lineEnd;
	int columnStart;
	int columnEnd;
	
	public NodeData(int id, Node node) {
		this.id = id;
		this.node = node;
		
		lineStart = node.getBegin().get().line;
		lineEnd = node.getEnd().get().line;
		columnStart = node.getBegin().get().column;
		columnEnd = node.getEnd().get().column;
	}
	
	
	public JSONObject toJSONObject() {
		return new JSONObject()
				.put("id", id)
				.put("lineStart", lineStart)
				.put("lineEnd", lineEnd)
				.put("columnStart", columnStart)
				.put("columnEnd", columnEnd);
	}
	
}
