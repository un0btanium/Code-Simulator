package de.unobtanium.codesimulator.codedata;

public class CodeSnippet {
	

	public String identifier;
	String replacementCode;
	
	SourceFile sourceFile;
	public int startAtLine;
	int lines;
	
	public CodeSnippet(String identifier, String code) {
		this.identifier = identifier;
		this.replacementCode = code.stripTrailing();
		lines = replacementCode.lines().toArray().length-1;
	}
	
	public int getEnd() {
		return startAtLine+lines;
	}
	
	
	
}
