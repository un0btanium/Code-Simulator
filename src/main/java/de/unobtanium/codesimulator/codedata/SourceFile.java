package de.unobtanium.codesimulator.codedata;

import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;

public class SourceFile {
	
	
	public String packageString;
	public String className;
	public String code;
	
	public CompilationUnit cu;
	
	public SourceFile(String packageString, String className, String code, Map<String, CodeSnippet> codeSnippets) {
		this.packageString = packageString;
		this.className = className;
		this.code = code;
		
		// register this SourceFile on all CodeSnippets if the code contains the identifier
		for (String key : codeSnippets.keySet()) {
			CodeSnippet codeSnippet = codeSnippets.get(key);
			if (codeSnippet.sourceFile != null) {
				continue; // skip this code snippet because its already found, duplicate identifier ( TODO log error)
			}
			
			int index = this.code.indexOf("// " + codeSnippet.identifier);
			if (index != -1) {
//				if (codeSnippet.sourceFile != null) {
//					System.err.println("The code snippet '" + codeSnippet + "' occurs more than once! That's not allowed!");
//				}
				codeSnippet.sourceFile = this;
				
				String[] lineStrings = this.code.lines().toArray(String[]::new);
				int line = 1;
				for (String str : lineStrings) {
					if (str.contains("// " + codeSnippet.identifier)) {
						codeSnippet.startAtLine = line;
						break;
					}
					line++;
				}
			}
		}
	}
	
}
