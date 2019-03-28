package de.unobtanium.codesimulator.visitors;

import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import de.unobtanium.codesimulator.CodeData;

public class ReplaceVisitor extends ModifierVisitor<CodeData> {
	

	@Override
	public Visitable visit(MethodCallExpr n, CodeData arg) {
		
        List<Node> children = n.getChildNodes();
        
        if (children.size() > 0) {
        	String childString = children.get(0).toString();
        	
        	if (childString.equals("System.out")) {
				if (n.getNameAsString().equals("println")) {
					super.visit(n, arg);
					
					if (n.getArguments().size() == 0) {
						return JavaParser.parseExpression("de.unobtanium.codesimulator.SimulationData.getInstance().println(" + arg.getIdOfNode(n) + ", \"\")");
					} else {
						return JavaParser.parseExpression("de.unobtanium.codesimulator.SimulationData.getInstance().println(" + arg.getIdOfNode(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
					
				} else if (n.getNameAsString().equals("print")) {
					super.visit(n, arg);
		
					if (n.getArguments().size() == 0) {
						return JavaParser.parseExpression("de.unobtanium.codesimulator.SimulationData.getInstance().print(" + arg.getIdOfNode(n) + ", \"\")");
					} else {
						return JavaParser.parseExpression("de.unobtanium.codesimulator.SimulationData.getInstance().print(" + arg.getIdOfNode(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
				}
        	}
        }
		
		super.visit(n, arg);
		return n;
	}
	
}
