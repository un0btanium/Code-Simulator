package de.unobtanium.codesimulator.simulator;

import de.unobtanium.codesimulator.simulationdata.SimulationStep;

public class ProgressBarSimulator {
	
	private int max;
	private int counter;
	
	public ProgressBarSimulator() {
		max = 0;
		counter = 0;
	}

	public void processStep(SimulationStep simulationStep) {
		counter++;
	}

	public String getProgressBar() {
		return "<div class=\"progress\" style=\"height: 20px;\"><div class=\"progress-bar\" style=\"width: " + (((double)counter/max)*100) + "%\" role=\"progressbar\" aria-valuenow=\"" + counter + "\" aria-valuemin=\"0\" aria-valuemax=\"" + max + "\" ata-toggle=\"tooltip\" data-placement=\"bottom\" data-html=\"true\" title=\"TooltipTextTODO\">" + counter + "/" + max + "</div></div>";
	}

	public void setMax(int max) {
		this.max = max+1;
	}
	
	
}
