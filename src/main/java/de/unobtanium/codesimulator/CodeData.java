package de.unobtanium.codesimulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.Node;

public class CodeData {

	List<SourceFile> sourceFiles;
	Map<String, CodeSnippet> codeSnippets;
	
	Map<Integer, NodeData> nodesByID;
	int nodeIDcounter;
	
	public CodeData(List<SourceFile> sourceFiles, Map<String, CodeSnippet> codeSnippets) {
		this.sourceFiles = sourceFiles;
		this.codeSnippets = codeSnippets;
		
		// TODO find comments
		// TODO find comment text
		// TODO compare comment text with code snippets
		// TODO replace comment with code snippet for final source code file
		// TODO repeat for all comments
		// TODO extra: relative lines for the code snippets for proper error line messages for the user
		
		
		
		nodesByID = new HashMap<>();
		nodeIDcounter = 0;
	}
	
	
	public void registerNode(Node node) {
		nodesByID.put(nodeIDcounter, new NodeData(nodeIDcounter, node));
		nodeIDcounter++;
	}


	
	public int getIdOfNode(Node node) {
		for (Integer key : nodesByID.keySet()) {
			if (node == nodesByID.get(key).node) { 
				return key.intValue();
			}
		}
		return -1;
	}
}
