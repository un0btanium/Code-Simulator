package de.unobtanium.codesimulator.codedata;

import java.util.Map;

import com.github.javaparser.JavaParser;
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
		
		for (String key : codeSnippets.keySet()) {
			this.code = this.code.replace("// " + codeSnippets.get(key).identifier, codeSnippets.get(key).code);
		}
		
		for (String key : codeSnippets.keySet()) {
			this.code = this.code.replace("//" + codeSnippets.get(key).identifier, codeSnippets.get(key).code);
		}
		
//		System.out.println(this.code);
		
		this.cu = JavaParser.parse(this.code);

//		System.out.println(cu.toString(new PrettyPrinterConfiguration().setTabWidth(4)));
		
	}
	
}
