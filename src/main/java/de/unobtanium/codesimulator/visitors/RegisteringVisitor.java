package de.unobtanium.codesimulator.visitors;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.unobtanium.codesimulator.CodeData;

public class RegisteringVisitor extends VoidVisitorAdapter<CodeData> {
	
	
	
	@Override
	public void visit(MethodCallExpr n, CodeData arg) {
		
		arg.registerNode(n);
		
		super.visit(n, arg);
	}
	
}
