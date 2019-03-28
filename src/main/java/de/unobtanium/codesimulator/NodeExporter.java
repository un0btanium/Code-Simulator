package de.unobtanium.codesimulator;

import org.json.JSONArray;
import org.json.JSONObject;

public class NodeExporter {

	public static JSONArray exportNodes(CodeData codeData) {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for (Integer integer : codeData.nodesByID.keySet()) {
			
			jsonArray.put(codeData.nodesByID.get(integer).toJSONObject());
			jsonObj.put(""+integer.intValue(), codeData.nodesByID.get(integer).toJSONObject());
			
		}
		
		return jsonArray;
		
	}
	
	
}
