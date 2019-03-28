package de.unobtanium.codesimulator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import de.unobtanium.codesimulator.compilerapi.MemoryClassLoader;
import de.unobtanium.codesimulator.compilerapi.MemoryJavaFileManager;
import de.unobtanium.codesimulator.compilerapi.StringJavaFileObject;

public class CodeExecuter {
	
	public static void execute(CodeData codeData) {
		
		// IMPORT CODE INTO PROGRAM AND EXECUTE THE CODE. SAVE RESULTS IN SimulationData object
		importAndExecuteCode(codeData);
		
	}

	private static void importAndExecuteCode(CodeData codeData) {
		executeCode(importCode(codeData));
	}

	private static MemoryClassLoader importCode(CodeData codeData) {
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // copy tools.jar from jdk/lib in jre/lib folder
		
		if (compiler == null) {
			System.err.println("No Java Compiler available!");
		}
		
		MemoryClassLoader classLoader = new MemoryClassLoader();
		JavaFileManager fileManager = new MemoryJavaFileManager(compiler, classLoader);
		
		List<JavaFileObject> units = new ArrayList<>();
		for (SourceFile sourceFile : codeData.sourceFiles) {
			JavaFileObject javaFile = new StringJavaFileObject(sourceFile.packageString + "." + sourceFile.className, sourceFile.cu.toString());
			units.add(javaFile);
		}
		
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, units);
		task.call();
		
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return classLoader;
	}
	
	private static void executeCode(MemoryClassLoader classLoader) {
		
		try {
			Class.forName("main.Main", true, classLoader).getMethod("main").invoke(new String[0]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
