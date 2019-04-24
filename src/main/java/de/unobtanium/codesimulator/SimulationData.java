package de.unobtanium.codesimulator;

import java.util.ArrayList;
import java.util.List;

import de.unobtanium.codesimulator.simulationdata.AssignVariable;
import de.unobtanium.codesimulator.simulationdata.Highlight;
import de.unobtanium.codesimulator.simulationdata.HighlightValue;
import de.unobtanium.codesimulator.simulationdata.PrintLineToConsole;
import de.unobtanium.codesimulator.simulationdata.PrintToConsole;
import de.unobtanium.codesimulator.simulationdata.ReadString;
import de.unobtanium.codesimulator.simulationdata.ShowError;
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
	
	public int highlight(int id, int value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public long highlight(int id, long value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public short highlight(int id, short value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public byte highlight(int id, byte value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public double highlight(int id, double value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public float highlight(int id, float value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public String highlight(int id, String value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public char highlight(int id, char value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public boolean highlight(int id, boolean value) {
		simulationSteps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	
	
	
	public int assign(int id, String name, int value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public long assign(int id, String name, long value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public short assign(int id, String name, short value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public byte assign(int id, String name, byte value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public double assign(int id, String name, double value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public float assign(int id, String name, float value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public String assign(int id, String name, String value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public char assign(int id, String name, char value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public boolean assign(int id, String name, boolean value) {
		simulationSteps.add(new AssignVariable(id, getTime(), name, value));
		return value;
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
	
	public void readLine(int id) {
		simulationSteps.add(new ReadString(id, getTime()));
		App.exportCurrentState(true);
	}
	
	public void showError(int id, String errorMessage) {
		simulationSteps.add(new ShowError(id, getTime(), errorMessage));
	}

	
	public void declarePrimitiveVariable(int id, String variableName, String variableType) {
		
	}

	public void initializePrimitiveVariable(int id, String variableName, String variableType, String value) {
		
	}
	
	
	
}
