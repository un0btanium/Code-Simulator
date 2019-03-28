package de.unobtanium.codesimulator.simulator;

import de.unobtanium.codesimulator.CodeData;
import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class SourceCodeSimulator {
	
	CodeData codeData;
	
	public SourceCodeSimulator(CodeData codeData) {
		this.codeData = codeData;
	}

	public void processStep(SimulationStep simulationStep) {
		// TODO
	}

	public String getSourceCode() {
		return "TODO in SourceCodeSimulator"; // TODO
//		return codeData.codeOfMainMethod.replaceAll("[\n]", "<br>");
	}
	
}
