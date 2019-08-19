package de.unobtanium.codesimulator.compilerapi;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StringJavaFileObject extends SimpleJavaFileObject {
	
	private final String name;
	private final CharSequence code;
	
	public StringJavaFileObject(String name, CharSequence code) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.name = name;
		this.code = code;
	}
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}
	
	public String getNameAsString() {
		return this.name;
	}
}