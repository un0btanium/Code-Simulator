package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONObject;

public abstract class SimulationStep {

	public int id;
	public long time;
	
	public SimulationStep(int id, long time) {
		this.id = id;
		this.time = time;
	}
	
	
	public abstract JSONObject asJSONObject();
	
}
