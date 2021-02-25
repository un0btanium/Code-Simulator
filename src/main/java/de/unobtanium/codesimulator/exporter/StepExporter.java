package de.unobtanium.codesimulator.exporter;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.steps.Step;
import de.unobtanium.codesimulator.steps.StepCollection;

public class StepExporter {
	
	public static boolean isUsingMathRandom = false;

	public static JSONArray export() {
		
		JSONArray jsonArray = new JSONArray();
		
		long previousTime = 0;
		int size = StepCollection.getInstance().steps.size();
		int i = 1;
		for (Step step : StepCollection.getInstance().steps) {
			JSONObject jsonObj = step.asJSONObject();
			
			long delay = Math.max(0, step.time-previousTime);
			if (jsonObj.getString("type").equals("console")) {
				delay = 4;
			}
			previousTime = step.time;
			jsonObj.put("delay", delay);
			
			// mark read command as already executed
			if (jsonObj.getString("type").equals("readString")) {
				if (i != size) {
					jsonObj.put("done", true); // TODO cleanup: just use the last step
				}
			}
			
			
			if (jsonObj.getString("type").equals("random")) {
				isUsingMathRandom = true;
			}
			
			jsonArray.put(jsonObj);
			i++;
		}
		
		return jsonArray;
		
	}
	
	
	
}
