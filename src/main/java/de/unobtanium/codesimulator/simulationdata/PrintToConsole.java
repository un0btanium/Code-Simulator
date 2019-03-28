package de.unobtanium.codesimulator.simulationdata;

public class PrintToConsole extends SimulationStep {

	public String message;
	
	public PrintToConsole(int id, long time, String message) {
		super(id, time);
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "PrintToConsole: " + id + " " + time + " " + message;
	}
	
}
