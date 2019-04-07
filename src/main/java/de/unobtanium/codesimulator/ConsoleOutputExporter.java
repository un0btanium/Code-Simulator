package de.unobtanium.codesimulator;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class ConsoleOutputExporter {

	public static JSONArray exportConsoleOutput() {
		
		JSONArray jsonArray = new JSONArray();
		
		long previousTime = 0;
		
		for (SimulationStep step : SimulationData.getInstance().simulationSteps) {
			JSONObject jsonObj = step.asJSONObject();
			jsonObj.put("delay", step.time-previousTime);
			previousTime = step.time;
			jsonArray.put(jsonObj);
		}
		
		return jsonArray;
		
	}
	
}
