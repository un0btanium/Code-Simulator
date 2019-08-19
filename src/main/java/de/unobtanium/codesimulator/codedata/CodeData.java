package de.unobtanium.codesimulator.codedata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;

public class CodeData {
	
	public List<SourceFile> sourceFiles;
	public Map<String, CodeSnippet> codeSnippets;
	
	public Map<Integer, NodeData> nodesByID;
	private int nodeIDcounter;
	
	public HashMap<SourceFile, ArrayList<CodeSnippet>> codeSnippetsBySourceFiles;
	
	public CodeData(List<SourceFile> sourceFiles, Map<String, CodeSnippet> codeSnippets) {
		this.sourceFiles = sourceFiles;
		this.codeSnippets = codeSnippets;
		
		nodesByID = new HashMap<>();
		nodeIDcounter = 0;
		
		// find comments
		// find comment text
		// compare comment text with code snippets
		//  replace comment with code snippet for final source code file
		// repeat for all comments
		// extra: relative lines for the code snippets for proper error line messages for the user
		
		// sort CodeSnippets by SourceFiles
		codeSnippetsBySourceFiles = new HashMap<>();
		for (CodeSnippet codeSnippet : codeSnippets.values()) {
			if (codeSnippet.sourceFile != null) {
				if (!codeSnippetsBySourceFiles.containsKey(codeSnippet.sourceFile)) {
					codeSnippetsBySourceFiles.put(codeSnippet.sourceFile, new ArrayList<>());
				}
				codeSnippetsBySourceFiles.get(codeSnippet.sourceFile).add(codeSnippet);
			}
		}
		
		// sort all CodeSnippet of each SourceFile by their occurence in the code (via line)
		for (ArrayList<CodeSnippet> codeSnippetList : codeSnippetsBySourceFiles.values()) {
			codeSnippetList.sort(new Comparator<CodeSnippet>() {
				@Override
				public int compare(CodeSnippet o1, CodeSnippet o2) {
					if (o1.startAtLine < o2.startAtLine) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
		
		// replace comment identifier with code
		for (SourceFile sourceFile : codeSnippetsBySourceFiles.keySet()) {
			int linesOffset = 0;
			for (CodeSnippet codeSnippet : codeSnippetsBySourceFiles.get(sourceFile)) {
				codeSnippet.startAtLine += linesOffset;
				sourceFile.code = sourceFile.code.replace("// " + codeSnippet.identifier, codeSnippet.replacementCode);
				linesOffset += codeSnippet.lines;
			}
		}
		
	}
	
	public void parseSourceFilesWithJavaParser() {
		// parse SourceFiles
		for (SourceFile sourceFile : sourceFiles) {
			sourceFile.cu = StaticJavaParser.parse(sourceFile.code); // TODO catch parse exceptions and create compiler errors for user (compile with javac?)
		}
	}
	
	
	/**
	 * Registers node if it is inside a CodeSnippet
	 * @param node
	 * @param sourceFile
	 */
	public void registerNode(Node node, SourceFile sourceFile) {
		
		if (sourceFile == null) {
			System.err.println("No SourceFile provided in .registerNode(Node, SourceFile)");
		}
		
		NodeData nodeData = new NodeData(nodeIDcounter, node);

		for (CodeSnippet codeSnippet : codeSnippetsBySourceFiles.get(sourceFile)) {
			if (nodeData.lineStart >= codeSnippet.startAtLine && nodeData.lineStart <= codeSnippet.getEnd()) {
				nodeData.setIdentifier(codeSnippet.identifier, codeSnippet.startAtLine);
				nodesByID.put(nodeIDcounter, nodeData);
				nodeIDcounter++; 
				break;
			}
		}
	}
	
	/**
	 * Registers the node no matter what. Useful for when you want to register a block statement right outside of a CodeSnippet.
	 * @param node
	 */
	public void registerNode(Node node) {
		NodeData nodeData = new NodeData(nodeIDcounter, node);
		nodesByID.put(nodeIDcounter, nodeData);
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
	

	public int getIdOfNodeOrAddNew(Node node) {
		for (Integer key : nodesByID.keySet()) {
			if (node == nodesByID.get(key).node) {
				return key.intValue();
			}
		}
		registerNode(node);
		return getIdOfNode(node);
	}


	public boolean existsCodeSnippetIdentifier(String commentString) {
		for (String codeSnippet : codeSnippets.keySet()) {
			if (codeSnippet.equals(commentString)) {
				return true;
			}
		}
		return false;
	}
}
