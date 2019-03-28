package de.unobtanium.codesimulator.simulationdata;

public abstract class SimulationStep {

	public int id;
	public long time;
	
	public SimulationStep(int id, long time) {
		this.id = id;
		this.time = time;
	}
	
	//public JSONObject getDataAsJSON();
	
}
