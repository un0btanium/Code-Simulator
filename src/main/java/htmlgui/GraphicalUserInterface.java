package htmlgui;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;

import de.unobtanium.codesimulator.App;
import de.unobtanium.codesimulator.SimulationData;

public class GraphicalUserInterface {
	
	private static ArrayList<ArrayList<GUIElement>> guiElements;
	private static int rowIndex;
	
	static {
		reset();
	}
	
	private static void reset() {
		guiElements = new ArrayList<>();
		guiElements.add(new ArrayList<>());
		rowIndex = 0;
	}
	
	public static void addRow() {
		guiElements.add(new ArrayList<>());
		rowIndex++;
	}

	public static void printGUI() {
		JSONArray jsonArrayRows = new JSONArray();
		for (ArrayList<GUIElement> row : guiElements) {
			JSONArray jsonArrayColumns = new JSONArray();
			for (GUIElement guiElement : row) {
				jsonArrayColumns.put(guiElement.toJSONObject());
			}
			jsonArrayRows.put(jsonArrayColumns);
		}
		SimulationData.getInstance().showHtmlGui(jsonArrayRows);
		reset();
	}

	public static int printAndReadGUI() {
		JSONArray jsonArrayRows = new JSONArray();
		for (ArrayList<GUIElement> row : guiElements) {
			JSONArray jsonArrayColumns = new JSONArray();
			for (GUIElement guiElement : row) {
				jsonArrayColumns.put(guiElement.toJSONObject());
			}
			jsonArrayRows.put(jsonArrayColumns);
		}
		SimulationData.getInstance().showHtmlGui(jsonArrayRows);
		reset();
		App.exportCurrentStateGui();
		Scanner scanner = new Scanner(System.in);
    	try {
        	return scanner.nextInt();
    	} catch (Throwable t) {
        	return -1;
    	} finally {
    		scanner.close();
    	}
	}
	
	public static void addButton(int id, String labelText) {
		guiElements.get(rowIndex).add(new GUIButton(id, labelText));
	}
	
	public static void addButton(int id, String labelText, int red, int green, int blue, boolean isBorderRounded) {
		guiElements.get(rowIndex).add(new GUIButton(id, labelText, red, green, blue, isBorderRounded));
	}

	public static void addSquare(String labelText, int red, int green, int blue, boolean isBorderRounded) {
		guiElements.get(rowIndex).add(new GUISquare(labelText, red, green, blue, isBorderRounded));
	}
	
	public static void addSquare(String labelText) {
		guiElements.get(rowIndex).add(new GUISquare(labelText));
	}
	
}
