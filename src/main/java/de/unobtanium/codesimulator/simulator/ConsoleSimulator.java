package de.unobtanium.codesimulator.simulator;

import de.unobtanium.codesimulator.simulationdata.PrintLineToConsole;
import de.unobtanium.codesimulator.simulationdata.PrintToConsole;
import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class ConsoleSimulator {
	
	private StringBuilder consoleOutput;
	private String lastConsoleOutput;
	
	public ConsoleSimulator() {
		consoleOutput = new StringBuilder();
		lastConsoleOutput = null;
	}
	
	
	
	public void processStep(SimulationStep simulationStep) {
		if (simulationStep instanceof PrintToConsole) {
			lastConsoleOutput = ((PrintToConsole) simulationStep).message;
		} else if (simulationStep instanceof PrintLineToConsole) {
			lastConsoleOutput = ((PrintLineToConsole) simulationStep).message;
		}
	}
	
	
	
	public String getConsole() {
		String finalOutput = consoleOutput.toString();
		
		if (lastConsoleOutput != null) {
			finalOutput = finalOutput + ("<span style=\"color:#666666\">" + lastConsoleOutput + "</span>").replaceAll("[\n]", "<br>"); // for current step
			consoleOutput.append(("<span>" + lastConsoleOutput + "</span>").replaceAll("[\n]", "<br>")); // for next step
			lastConsoleOutput = null; // reset
		}
		
		return finalOutput;
	}
	
}
