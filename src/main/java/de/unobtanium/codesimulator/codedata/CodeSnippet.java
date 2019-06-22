package de.unobtanium.codesimulator.codedata;

public class CodeSnippet {
	

	String identifier;
	String replacementCode;
	
	SourceFile sourceFile;
	int startAtLine;
	int lines;
	
	public CodeSnippet(String identifier, String code) {
		this.identifier = identifier;
		this.replacementCode = code;
		lines = replacementCode.lines().toArray().length-1;
	}
	
	public int getEnd() {
		return startAtLine+lines;
	}
	
	
	
}
