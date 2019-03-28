package de.unobtanium.codesimulator;

import java.util.ArrayList;
import java.util.List;

import de.unobtanium.codesimulator.simulationdata.Highlight;
import de.unobtanium.codesimulator.simulationdata.PrintLineToConsole;
import de.unobtanium.codesimulator.simulationdata.PrintToConsole;
import de.unobtanium.codesimulator.simulationdata.SimulationStep;
import de.unobtanium.codesimulator.simulationdata.SolveCalculation;

public class SimulationData {
	
	private static SimulationData instance = null;
	
	public static SimulationData getInstance() {
		if (instance == null) {
			SimulationData.instance = new SimulationData();
		}
		return SimulationData.instance;
		
	}
	
	
	public List<SimulationStep> simulationSteps;
	public long startTime;
	
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	private SimulationData() {
		simulationSteps = new ArrayList<>();
		startTime = System.currentTimeMillis();
	}
	
	public void highlight(int id) {
//		System.out.println("highlight(" + id + ")");
		simulationSteps.add(new Highlight(id, getTime()));
	}
	
	
	public void solveCalculation(int id, String result) {
//		System.out.println("solveCalculation(" + id + ", "+ result + ")");
		simulationSteps.add(new SolveCalculation(id, getTime(), result));
	}
	
	
	public void println(int id, String message) {
//		System.out.println("println(" + id + ", " + message + ")");
		simulationSteps.add(new PrintLineToConsole(id, getTime(), message));
	}
	
	public void print(int id, String message) {
//		System.out.println("print(" + id + ", " + message + ")");
		simulationSteps.add(new PrintToConsole(id, getTime(), message));
	}
	
	public void declarePrimitiveVariable(int id, String variableName, String variableType) {
		
	}

	public void initializePrimitiveVariable(int id, String variableName, String variableType, String value) {
		
	}
	
	
	
}
