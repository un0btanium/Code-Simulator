package de.unobtanium.codesimulator.steps;

import java.util.ArrayList;
import java.util.HashSet;
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
	public HashSet<Integer> usedNodeIds;
	
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	private StepCollection() {
		steps = new ArrayList<>();
		startTime = System.currentTimeMillis();
		usedNodeIds = new HashSet<>();
	}
	
	
	public void addNodeIDtoUsed(int id) {
		if (id >= 0) {
			usedNodeIds.add(id);
		}
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
	
	
	
	public String highlightBinaryExpression(int id, String operator, String value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}
	
	public int highlightBinaryExpression(int id, String operator, int value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}
	
	public double highlightBinaryExpression(int id, String operator, double value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}

	public boolean highlightBinaryExpression(int id, String operator, boolean value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}
	
	public float highlightBinaryExpression(int id, String operator, float value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}

	public char highlightBinaryExpression(int id, String operator, char value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}

	public long highlightBinaryExpression(int id, String operator, long value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}

	public short highlightBinaryExpression(int id, String operator, short value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}
	
	public byte highlightBinaryExpression(int id, String operator, byte value) {
		steps.add(new HighlightBinaryExpression(id, getTime(), operator, value));
		return value;
	}
	
	
	
	
	public int highlightUnaryExpression(int id, boolean isPostfix, int valueBefore, int valueAfter, int value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public double highlightUnaryExpression(int id, boolean isPostfix, double valueBefore, double valueAfter, double value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public float highlightUnaryExpression(int id, boolean isPostfix, float valueBefore, float valueAfter, float value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public char highlightUnaryExpression(int id, boolean isPostfix, char valueBefore, char valueAfter, char value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public long highlightUnaryExpression(int id, boolean isPostfix, long valueBefore, long valueAfter, long value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public short highlightUnaryExpression(int id, boolean isPostfix, short valueBefore, short valueAfter, short value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
	}
	
	public byte highlightUnaryExpression(int id, boolean isPostfix, byte valueBefore, byte valueAfter, byte value) {
		steps.add(new HighlightUnaryExpression(id, getTime(), isPostfix, valueBefore, valueAfter));
		return value;
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
	
	public void showError(int id, String errorMessage, String identifier, long line) {
		steps.add(new ShowError(id, getTime(), errorMessage, identifier, line));
	}

	
	
	public void declarePrimitiveVariable(int id, String variableName, String variableType) {
		steps.add(new DeclarePrimitiveVariable(id, getTime(), variableName, variableType));
	}

	public void initializePrimitiveVariable(int id, String variableName, String variableType, String value) {
		steps.add(new InitializePrimitiveVariable(id, getTime(), variableName, variableType, value));
	}

	public void addEmptyStep() {
		steps.add(new EmptyStep());
	}
	
}
