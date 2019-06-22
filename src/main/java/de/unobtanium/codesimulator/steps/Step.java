package de.unobtanium.codesimulator.steps;

import org.json.JSONObject;

public abstract class Step {

	public int id;
	public long time;
	
	public Step(int id, long time) {
		this.id = id;
		this.time = time;
		StepCollection.getInstance().addNodeIDtoUsed(id);
	}
	
	
	public abstract JSONObject asJSONObject();
	
}
