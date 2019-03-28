package de.unobtanium.codesimulator.simulationdata;

public class SolveCalculation extends SimulationStep {
	
	private String result;
	
	public SolveCalculation(int id, long time, String result) {
		super(id, time);
		this.result = result;
	}
	
}
