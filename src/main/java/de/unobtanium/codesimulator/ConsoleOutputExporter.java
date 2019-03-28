package de.unobtanium.codesimulator;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.simulationdata.PrintLineToConsole;
import de.unobtanium.codesimulator.simulationdata.PrintToConsole;
import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class ConsoleOutputExporter {

	public static JSONArray exportConsoleOutput() {
		
		JSONArray jsonArray = new JSONArray();
		
		long delay = 0;
		
		for (SimulationStep step : SimulationData.getInstance().simulationSteps) {
			if (step instanceof PrintLineToConsole) {
				JSONObject jsonObj = new JSONObject();
				PrintLineToConsole s = (PrintLineToConsole) step;
				jsonObj.put("id", s.id);
				jsonObj.put("delay", delay);
				delay = Math.max(0, s.time-delay);
				jsonObj.put("message", s.message);
				jsonArray.put(jsonObj);
//				System.out.print(((PrintLineToConsole) step).toString());
			} else if (step instanceof PrintToConsole) {
				JSONObject jsonObj = new JSONObject();
				PrintToConsole s = (PrintToConsole) step;
				jsonObj.put("id", s.id);
				jsonObj.put("delay", delay);
				delay = Math.max(0, s.time-delay);
				jsonObj.put("message", s.message);
				jsonArray.put(jsonObj);
//				System.out.print(((PrintToConsole) step).toString());
			}
		}
		
		return jsonArray;
		
	}
	
}
