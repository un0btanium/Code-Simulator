package de.unobtanium.codesimulator.compilerapi;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

	private final MemoryClassLoader classLoader;

	public MemoryJavaFileManager(JavaCompiler compiler, MemoryClassLoader classLoader) {
		super(compiler.getStandardFileManager(null, null, null));

		this.classLoader = classLoader;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
		MemJavaFileObject fileObject = new MemJavaFileObject(className);
		classLoader.addClassFile(fileObject);
		return fileObject;
	}
}