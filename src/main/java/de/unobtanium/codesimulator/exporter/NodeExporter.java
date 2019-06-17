package de.unobtanium.codesimulator.exporter;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.codedata.CodeData;

public class NodeExporter {

	public static JSONArray export(CodeData codeData) {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for (Integer integer : codeData.nodesByID.keySet()) {
			
			jsonArray.put(codeData.nodesByID.get(integer).toJSONObject());
			jsonObj.put(""+integer.intValue(), codeData.nodesByID.get(integer).toJSONObject());
			
		}
		
		return jsonArray;
		
	}
	
	
}
