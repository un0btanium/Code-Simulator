package de.unobtanium.codesimulator;

import java.io.IOException;
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
							StepCollection.getInstance().showError(-2, diagnostic.toString() + "\n", identifier, diagnostic.getLineNumber()-lineOffset);
						} else {
							StepCollection.getInstance().showError(-2, diagnostic.toString() + "\n");
						}
						break;
					}
				}
				
				
//				System.out.printf( "Kind: %s%n", diagnostic.getKind() );
//				System.out.printf( "Quelle: %s%n", diagnostic.getSource() );
//				System.out.printf( "Code und Nachricht: %s: %s%n", diagnostic.getCode(), diagnostic.getMessage( null ) );
//				System.out.printf( "Zeile: %s%n", diagnostic.getLineNumber() );
//				System.out.printf( "Position/Spalte: %s/%s%n", diagnostic.getPosition(), diagnostic.getColumnNumber() );
//				System.out.printf( "Startpostion/Endposition: %s/%s%n", diagnostic.getStartPosition(), diagnostic.getEndPosition() );
//				System.out.println();
//				System.out.println(diagnostic.toString());
//				System.out.println();
//				System.out.println(diagnostic.getSource());
//				System.out.println();
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
			
		} catch (Throwable e) { // TODO set error and export error
			StepCollection.getInstance().showError(-2, e.toString());
		}
	}
	
}
