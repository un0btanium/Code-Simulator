package de.unobtanium.codesimulator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import de.unobtanium.codesimulator.codedata.CodeData;
import de.unobtanium.codesimulator.codedata.CodeSnippet;
import de.unobtanium.codesimulator.codedata.SourceFile;
import de.unobtanium.codesimulator.compilerapi.MemoryClassLoader;
import de.unobtanium.codesimulator.compilerapi.MemoryJavaFileManager;
import de.unobtanium.codesimulator.compilerapi.StringJavaFileObject;
import de.unobtanium.codesimulator.steps.StepCollection;

public class CodeExecuter {
	
	public static void execute(CodeData codeData) {
		
		// IMPORT CODE INTO PROGRAM AND EXECUTE THE CODE. SAVE RESULTS IN SimulationData object
		compileAndExecuteCode(codeData);
		
	}

	private static void compileAndExecuteCode(CodeData codeData) {
		executeCode(compileCode(codeData));
	}

	public static MemoryClassLoader compileCode(CodeData codeData) {
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // copy tools.jar from jdk/lib in jre/lib folder
		
		if (compiler == null) {
			System.err.println("No Java Compiler available!"); // TODO throw Exception
		}
		
		MemoryClassLoader classLoader = new MemoryClassLoader();
		JavaFileManager fileManager = new MemoryJavaFileManager(compiler, classLoader);
		
		List<JavaFileObject> units = new ArrayList<>();
		for (SourceFile sourceFile : codeData.sourceFiles) {
			String code = (sourceFile.cu == null ? sourceFile.code : sourceFile.cu.toString());
			JavaFileObject javaFile = new StringJavaFileObject(sourceFile.packageString + "." + sourceFile.className, code);
			units.add(javaFile);
		}

		
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		
		CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, units); // TODO third argument is DiagnosticListener
		boolean didSuccessfullyCompile = task.call(); // TODO catch return boolean: returns true when successfully compiled, false otherwise
		
		if (!didSuccessfullyCompile) {
        	StepCollection.getInstance().addEmptyStep();
			for ( Diagnostic<?> diagnostic : diagnostics.getDiagnostics() ) {
				if (diagnostic.getSource() == null || !(diagnostic.getSource() instanceof StringJavaFileObject)) {
					StepCollection.getInstance().showError(-2, "Unknown compile error!");
					continue;
				}
				
				String className = ((StringJavaFileObject) diagnostic.getSource()).getNameAsString();

				for (SourceFile sourceFile : codeData.sourceFiles) {
					if (className.equals(sourceFile.packageString + "." + sourceFile.className)) {
						
						String identifier = null;
						int lineOffset = 0;
						if (diagnostic.getLineNumber() != Diagnostic.NOPOS) {
							for (CodeSnippet codeSnippet : codeData.codeSnippetsBySourceFiles.get(sourceFile)) {
								if (diagnostic.getLineNumber() >= codeSnippet.startAtLine && diagnostic.getLineNumber() <= codeSnippet.getEnd()) {
									identifier = codeSnippet.identifier;
									lineOffset = codeSnippet.startAtLine;
									break;
								}
							}
						}
						
						if (identifier != null) {
							
							String errorMessage = diagnostic.toString() + "\n";
							errorMessage = errorMessage.replaceFirst("" + diagnostic.getLineNumber(), "" + ((diagnostic.getLineNumber()-lineOffset)+1));
							
							StepCollection.getInstance().showError(-2, errorMessage, identifier, diagnostic.getLineNumber()-lineOffset);
						} else {
							StepCollection.getInstance().showError(-2, diagnostic.toString() + "\n");
						}
						break;
					}
				}
			}
			
			return null;
		}
		
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return classLoader;
	}
	
	public static void executeCode(MemoryClassLoader classLoader) {
		
		try {

//			String[] parameter = new String[0]; // TODO custom args?
			Class<?> mainClass = Class.forName("main.Main", true, classLoader);
			Method mainMethod = mainClass.getMethod("main", new Class[] { String[].class });
			mainMethod.invoke(null, new Object[] { new String[0] });
			
		} catch (InvocationTargetException e) { // TODO set error and export error
			StringBuffer sb = new StringBuffer();
			sb.append(e.getCause().toString());
			sb.append("\n");
			//TODO lines extracten
			for (StackTraceElement ste : e.getCause().getStackTrace()) {
				sb.append(ste.toString());
				sb.append("\n");
			}
			StepCollection.getInstance().showError(-2, e.getCause() == null ? "null" : sb.toString());
		} catch(Throwable e) {
			StepCollection.getInstance().showError(-2, e.getCause() == null ? "null" : e.getStackTrace().toString());
		}
	}
	
}
