package de.unobtanium.codesimulator.simulator;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.CodeData;
import de.unobtanium.codesimulator.SimulationData;
import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class MainSimulator {
	
	private CodeData codeData;
	
	private ConsoleSimulator consoleSimulator;
	private SourceCodeSimulator sourceCodeSimulator;
	private MemorySimulator memorySimulator;
	private ProgressBarSimulator progressBarSimulator;

	public MainSimulator(CodeData codeData) {
		this.codeData = codeData;
		
		consoleSimulator = new ConsoleSimulator();
		sourceCodeSimulator = new SourceCodeSimulator(codeData);
		memorySimulator = new MemorySimulator();
		progressBarSimulator = new ProgressBarSimulator();
	}
	
	public String simulate() {
		
		progressBarSimulator.setMax(SimulationData.getInstance().simulationSteps.size());
		
		JSONArray jsonArray = new JSONArray();
		for (SimulationStep simulationStep : SimulationData.getInstance().simulationSteps) {

			consoleSimulator.processStep(simulationStep);
			sourceCodeSimulator.processStep(simulationStep);
			memorySimulator.processStep(simulationStep);
			progressBarSimulator.processStep(simulationStep);
			
			JSONObject jsonStep = new JSONObject();
			
			jsonStep.put("code", sourceCodeSimulator.getSourceCode()); // TODO run through HighlightVisitor
			jsonStep.put("progressbar", progressBarSimulator.getProgressBar()); // TODO create class ProgressBarEntry and create a list to push onto
			jsonStep.put("console", consoleSimulator.getConsole()); // 
			jsonStep.put("memory", memorySimulator.getMemory());
			
			jsonArray.put(jsonStep);
			
		}
		
		// FINALE STEP
		consoleSimulator.processStep(null);
		sourceCodeSimulator.processStep(null);
		memorySimulator.processStep(null);
		progressBarSimulator.processStep(null);
		JSONObject jsonStep = new JSONObject();
		jsonStep.put("code", sourceCodeSimulator.getSourceCode().replaceAll("[\n]", "<br>")); // TODO run through HighlightVisitor
		jsonStep.put("progressbar", progressBarSimulator.getProgressBar()); // TODO create class ProgressBarEntry and create a list to push onto
		jsonStep.put("console", consoleSimulator.getConsole()); // 
		jsonStep.put("memory", memorySimulator.getMemory());
		jsonArray.put(jsonStep);

		
		return jsonArray.toString();
	}
	
	
	
	
}
