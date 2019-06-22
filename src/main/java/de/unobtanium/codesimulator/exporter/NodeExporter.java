package de.unobtanium.codesimulator.exporter;

import org.json.JSONObject;

import de.unobtanium.codesimulator.codedata.CodeData;
import de.unobtanium.codesimulator.steps.StepCollection;

public class NodeExporter {

	/**
	 * Exports only the nodes which need to be highlighted
	 * @param codeData
	 * @return The json object with all node data. Keys are the IDs of the nodes.
	 */
	public static JSONObject export(CodeData codeData) {
		
		JSONObject jsonObj = new JSONObject();
		
		
//		for (int i : StepCollection.getInstance().usedNodeIds) {
//			System.out.println(i);
//		}
//		System.out.println("-");
//		for (Step step : StepCollection.getInstance().steps) {
//			System.out.println(step.id);
//		}
		
		for (Integer integer : StepCollection.getInstance().usedNodeIds) {
			if (codeData.nodesByID.containsKey(integer)) {
				jsonObj.put(""+integer.intValue(), codeData.nodesByID.get(integer).toJSONObject());
			}
			
		}
		
		return jsonObj;
		
	}
	
	
}
