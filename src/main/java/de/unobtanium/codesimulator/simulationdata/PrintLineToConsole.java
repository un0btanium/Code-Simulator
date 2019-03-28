package de.unobtanium.codesimulator.simulationdata;

public class PrintLineToConsole extends SimulationStep {

	public  String message;
	
	public PrintLineToConsole(int id, long time, String message) {
		super(id, time);
		this.message = message + "\n";
	}
	
	@Override
	public String toString() {
		return "PrintLineToConsole: " + id + " " + time + " " + message;
	}
	
}
