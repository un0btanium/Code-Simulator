package de.unobtanium.codesimulator;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class StepExporter {

	public static JSONArray export() {
		
		JSONArray jsonArray = new JSONArray();
		
		long previousTime = 0;
		int size = SimulationData.getInstance().simulationSteps.size();
		int i = 1;
		for (SimulationStep step : SimulationData.getInstance().simulationSteps) {
			JSONObject jsonObj = step.asJSONObject();
			jsonObj.put("delay", step.time-previousTime);
			previousTime = step.time;
			
			// mark read command as already executed
			if (jsonObj.getString("type").equals("readString")) {
				if (i != size) {
					jsonObj.put("done", true);
				}
			}
			
			jsonArray.put(jsonObj);
			i++;
		}
		
		return jsonArray;
		
	}
	
}
