package de.unobtanium.codesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.javaparser.ast.ImportDeclaration;

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
    	codeData = new CodeData(sourceFiles, codeSnippets, data.getInt("highlightingDetailLevelIndex"));
    	
    	// if raw code cant be compiled exit program (errors are already saved as ErrorStep objects)
    	if (CodeExecuter.compileCode(codeData) == null) {
        	StepCollection.getInstance().addEmptyStep();
        	exportCurrentState(false);
        	return;
    	}
    	
    	
    	// TODO check if raw source code compiles
    	// TODO if not explore 

    	codeData.parseSourceFilesWithJavaParser();

    	// add StepCollection import to each source file
    	String importStr = "de.unobtanium.codesimulator.steps.StepCollection";
    	for (SourceFile sourceFile : sourceFiles) {
    		boolean hasImport = false;
    		for (ImportDeclaration id : sourceFile.cu.getImports()) {
    			if (id.getName().asString().equals(importStr)) {
    				hasImport = true;
    				break;
    			}
    		}
    		if (!hasImport) {
    			sourceFile.cu.addImport(importStr);
    		}
    	}
    	
    	// REGISTER NODES, GIVE THEM UNIQUE IDs AND SAVE THEIR POSITION DATA
    	for (SourceFile sourceFile : sourceFiles) {
    		RegisteringVisitor registeringVisitor= new RegisteringVisitor();
    		registeringVisitor.sourceFile = sourceFile;
    		registeringVisitor.visit(sourceFile.cu, codeData);
    	}
    	
    	// REPLACE COMMANDS WITH CUSTOM DEBUG COMMANDS
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
    	JSONObject nodeData = NodeExporter.export(codeData);
    	

    	JSONObject result = new JSONObject();
    	result.put("steps", steps);
    	result.put("node_data", nodeData);
    	result.put("isReadIn", isReadIn);
    	result.put("isUsingMathRandom", StepExporter.isUsingMathRandom);
    	
//    	String sourcecode = "";
//    	for (SourceFile sourceFile : codeData.sourceFiles) {
//    		sourcecode += sourceFile.cu.toString() + "\n\n\n\n\n";
//    	}
//    	result.put("sourcecode", sourcecode);
    	
    	System.out.println(result.toString());
    }

	public static void exportCurrentStateGui() {
    	JSONArray steps = StepExporter.export();
    	JSONObject nodeData = NodeExporter.export(codeData);
    	

    	JSONObject result = new JSONObject();
    	result.put("steps", steps);
    	result.put("node_data", nodeData);
    	result.put("isGuiReadIn", true);
    	result.put("isUsingMathRandom", StepExporter.isUsingMathRandom);
    	

//    	String sourcecode = "";
//    	for (SourceFile sourceFile : codeData.sourceFiles) {
//    		sourcecode += sourceFile.cu.toString() + "\n\n\n\n\n";
//    	}
//    	result.put("sourcecode", sourcecode);
    	
    	System.out.println(result.toString());
	}
    
}
