package de.unobtanium.codesimulator.simulationdata;

import org.json.JSONArray;
import org.json.JSONObject;

public class HtmlGui extends SimulationStep {
	
	private JSONArray gui;
	
	public HtmlGui(int id, long time, JSONArray gui) {
		super(id, time);
		this.gui = gui;
	}
	
	@Override
	public JSONObject asJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "htmlGui");
		jsonObj.put("id", id);
		jsonObj.put("guiElements", gui);
		return jsonObj;
	}
	
}
