package de.unobtanium.codesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import de.unobtanium.codesimulator.codedata.CodeData;
import de.unobtanium.codesimulator.codedata.CodeSnippet;
import de.unobtanium.codesimulator.codedata.SourceFile;
import de.unobtanium.codesimulator.exporter.NodeExporter;
import de.unobtanium.codesimulator.exporter.StepExporter;
import de.unobtanium.codesimulator.steps.StepCollection;
import de.unobtanium.codesimulator.visitors.RegisteringVisitor;
import de.unobtanium.codesimulator.visitors.ReplaceVisitor;

public class CodeSimulator {
	
	private static CodeData codeData;
	
    public static void main( String[] args ) {

    	
//		System.out.println("OUTPUT:");
//    	for (String str : args) {
//    		System.out.println(str);
//    	}
    	
    	if (args.length != 1) {
    		JSONObject result = new JSONObject();
    		result.put("error", "Something went wrong! No argument with code passed!");
    		System.out.println(result.toString());
    		return;
    	}
    	
    	
    	// GET ARGUMENTS IN JSON FORMAT
    	JSONObject data = new JSONObject(args[0]);
    	
    	
    	// GET CODE SNIPPETS (WRITTEN BY USER SOLVING THE EXERCISE)
    	Map<String, CodeSnippet> codeSnippets = new HashMap<>();
    	
    	JSONObject jsonCodeSnippets = data.getJSONObject("code_snippets");
    	for (String key : jsonCodeSnippets.keySet()) {
    		JSONObject obj = jsonCodeSnippets.getJSONObject(key);
    		String code = obj.getString("code");
    		codeSnippets.put(key, new CodeSnippet(key, code));
    	}
    	
    	
    	
    	// GET CODE TEMPLATE (WRITTEN BY EXERCISE CREATOR)
    	List<SourceFile> sourceFiles = new ArrayList<>();
    	
    	JSONArray jsonSourceFiles = data.getJSONArray("source_files");
    	for (int i = 0; i < jsonSourceFiles.length(); i++) {
    		JSONObject obj = jsonSourceFiles.getJSONObject(i);
    		String packageString = obj.getString("package");
    		String className = obj.getString("name");
    		String code = obj.getString("code");
    		sourceFiles.add(new SourceFile(packageString, className, code, codeSnippets));
    	}
    	
    	// SAVE DATA IN CodeData OBJECT
    	codeData = new CodeData(sourceFiles, codeSnippets);
    	
    	// REGISTER NODES, GIVE THEM UNIQUE IDs AND SAVE THEIR POSITION DATA
    	for (SourceFile sourceFile : sourceFiles) {
        	new RegisteringVisitor().visit(sourceFile.cu, codeData);
    	}
    	
    	// REPLACE PRINT TO CONSOLE COMMANDS
    	for (SourceFile sourceFile : sourceFiles) {
        	new ReplaceVisitor().visit(sourceFile.cu, codeData);
//        	System.out.println(v.toString());
//        	System.out.println(sourceFile.cu);
    	}
    	
    	// IMPORT AND EXECUTE CODE
    	CodeExecuter.execute(codeData);
    	
    	// ADD EMPTY STEP AT THE END
    	StepCollection.getInstance().addEmptyStep();
    	
		// DEBUG: WRITE COMMANDS TO CONSOLE
//		for (SimulationStep step : SimulationData.getInstance().simulationSteps) {
//			System.out.println(step.toString());
//		}
    	
    	exportCurrentState(false);

    }
    
    public static void exportCurrentState(boolean isReadIn) {
    	JSONArray steps = StepExporter.export();
    	JSONArray nodeData = NodeExporter.export(codeData);
    	

    	JSONObject result = new JSONObject();
    	result.put("steps", steps);
    	result.put("node_data", nodeData);
    	result.put("isReadIn", isReadIn);
    	
//    	String sourcecode = "";
//    	for (SourceFile sourceFile : codeData.sourceFiles) {
//    		sourcecode += sourceFile.cu.toString() + "\n\n\n\n\n";
//    	}
//    	result.put("sourcecode", sourcecode);
    	
    	System.out.println(result.toString());
    }

	public static void exportCurrentStateGui() {
    	JSONArray steps = StepExporter.export();
    	JSONArray nodeData = NodeExporter.export(codeData);
    	

    	JSONObject result = new JSONObject();
    	result.put("steps", steps);
    	result.put("node_data", nodeData);
    	result.put("isGuiReadIn", true);
    	

//    	String sourcecode = "";
//    	for (SourceFile sourceFile : codeData.sourceFiles) {
//    		sourcecode += sourceFile.cu.toString() + "\n\n\n\n\n";
//    	}
//    	result.put("sourcecode", sourcecode);
    	
    	System.out.println(result.toString());
	}
    
}
