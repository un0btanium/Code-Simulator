package de.unobtanium.codesimulator.compilerapi;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

class MemJavaFileObject extends SimpleJavaFileObject {

	private final ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
	private final String className;

	MemJavaFileObject(String className) {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
		this.className = className;
	}

	String getClassName() {
		return className;
	}

	byte[] getClassBytes() {
		return baos.toByteArray();
	}

	@Override
	public OutputStream openOutputStream() {
		return baos;
	}
}