package de.unobtanium.codesimulator.steps;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import de.unobtanium.codesimulator.CodeSimulator;

public class StepCollection {
	
	private static StepCollection instance = null;
	
	public static StepCollection getInstance() {
		if (instance == null) {
			StepCollection.instance = new StepCollection();
		}
		return StepCollection.instance;
	}
	
	
	
	public List<Step> steps;
	public long startTime;
	
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	private StepCollection() {
		steps = new ArrayList<>();
		startTime = System.currentTimeMillis();
	}
	
	
	
	public void highlight(int id) {
//		System.out.println("highlight(" + id + ")");
		steps.add(new Highlight(id, getTime()));
	}
	
	
	
	public String highlight(int id, String value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public int highlight(int id, int value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public double highlight(int id, double value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public boolean highlight(int id, boolean value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public float highlight(int id, float value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public char highlight(int id, char value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public long highlight(int id, long value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}

	public short highlight(int id, short value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public byte highlight(int id, byte value) {
		steps.add(new HighlightValue(id, getTime(), value));
		return value;
	}
	
	public Object highlightNull(int id) {
		steps.add(new HighlightValue(id, getTime()));
		return null;
	}
	
	

	
	public String assign(int id, String name, String value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public int assign(int id, String name, int value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public double assign(int id, String name, double value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public boolean assign(int id, String name, boolean value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public float assign(int id, String name, float value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public char assign(int id, String name, char value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public long assign(int id, String name, long value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public short assign(int id, String name, short value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public byte assign(int id, String name, byte value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public int assign(int id, String name, Integer value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public double assign(int id, String name, Double value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public boolean assign(int id, String name, Boolean value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public float assign(int id, String name, Float value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public char assign(int id, String name, Character value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public long assign(int id, String name, Long value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}

	public short assign(int id, String name, Short value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	public byte assign(int id, String name, Byte value) {
		steps.add(new AssignVariable(id, getTime(), name, value));
		return value;
	}
	
	
	
	public void showHtmlGui(JSONArray gui) {
		int id = -1;
		if (steps.size() > 0) {
			id = steps.get(steps.size()-1).id;
		}
		steps.add(new HtmlGui(id, getTime(), gui));
	}
	
	
	
	public void solveCalculation(int id, String result) {
//		System.out.println("solveCalculation(" + id + ", "+ result + ")");
		steps.add(new SolveCalculation(id, getTime(), result));
	}
	
	
	
	public void println(int id, String message) {
//		System.out.println("println(" + id + ", " + message + ")");
		steps.add(new PrintLineToConsole(id, getTime(), message));
	}
	
	public void print(int id, String message) {
//		System.out.println("print(" + id + ", " + message + ")");
		steps.add(new PrintToConsole(id, getTime(), message));
	}
	
	
	
	public void readLine(int id) {
		steps.add(new ReadString(id, getTime()));
		CodeSimulator.exportCurrentState(true);
	}
	
	
	
	public void showError(int id, String errorMessage) {
		steps.add(new ShowError(id, getTime(), errorMessage));
	}

	
	
	public void declarePrimitiveVariable(int id, String variableName, String variableType) {
		// TODO
	}

	public void initializePrimitiveVariable(int id, String variableName, String variableType, String value) {
		// TODO
	}
	
}
