package de.unobtanium.codesimulator.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import de.unobtanium.codesimulator.codedata.CodeData;

public class ReplaceVisitor extends ModifierVisitor<CodeData> {
	

	private int tempVarCounter = 0;
	private String currentVariableName = "unset";
	
	private Map<Integer, Integer> offset = new HashMap<>();
	

	private String getNewTempVarName() {
		currentVariableName = "codeSimulatorTempVariable" + tempVarCounter;
		tempVarCounter++;
		return currentVariableName;
	}
	
	private String getCurrentTempVarName() {
		return currentVariableName;
	}
	
	private void insertStatements(Node n, CodeData codeData, boolean isPlacedBeforeNode, String... statements) {
		
		Node previousNode = null;
		Node node = n;
		
		while (!(node instanceof BlockStmt)) { // TODO check if this does find block statements if braces are not written
			previousNode = node;
			node = node.getParentNode().get();
			if (node instanceof CompilationUnit) {
//				System.err.println("No block statement found!"); TODO
				return;
			}
		}
		
		BlockStmt blockStmt = (BlockStmt) node;
		int index = blockStmt.getChildNodes().indexOf(previousNode);
		
		int blockStmtID = codeData.getIdOfNode(blockStmt);
		offset.put(blockStmtID, offset.getOrDefault(blockStmtID, -1)+1); // sets offset to 0 or increments by 1
		
		int i = ((isPlacedBeforeNode) ? 0 : 1) + offset.get(blockStmtID);
		for (String statement : statements) {
			if (statement.startsWith(".")) {
				blockStmt.addStatement(index+i++, JavaParser.parseStatement("de.unobtanium.codesimulator.steps.StepCollection.getInstance()" + statement));
			} else {
				blockStmt.addStatement(index+i++, JavaParser.parseStatement(statement));
			}
		}
		
	}
	
	private Expression parseExpression(String expression) {
		if (expression.startsWith(".")) {
			return JavaParser.parseExpression("de.unobtanium.codesimulator.steps.StepCollection.getInstance()" + expression);
		} else {
			return JavaParser.parseExpression(expression);
		}
	}
	
	

	@Override
	public Visitable visit(MethodCallExpr n, CodeData arg) {
		
        List<Node> children = n.getChildNodes();
        
        if (children.size() > 0) {
        	String childString = children.get(0).toString();
        	
        	if (childString.equals("de.unobtanium.codesimulator.steps.StepCollection")) {
        		return n;
        	}
        	
//        	System.out.println(childString);
//        	System.out.println(n.getNameAsString());

        	if (childString.equals("System.out")) {
				if (n.getNameAsString().equals("println")) {
					super.visit(n, arg);
					
					if (n.getArguments().size() == 0) {
						return parseExpression(".println(" + arg.getIdOfNode(n) + ", \"\")");
					} else {
						return parseExpression(".println(" + arg.getIdOfNode(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
					
				} else if (n.getNameAsString().equals("print")) {
					super.visit(n, arg);
		
					if (n.getArguments().size() == 0) {
						return parseExpression(".print(" + arg.getIdOfNode(n) + ", \"\")");
					} else {
						return parseExpression(".print(" + arg.getIdOfNode(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
				}
        	} else if (childString.equals("new Scanner(System.in)")) { // Todo check if type is a java.util.Scanner instead
        		if (n.getNameAsString().equals("nextLine")) {
        			insertStatements(n, arg, true, ".readLine(" + arg.getIdOfNode(n) + ");");
        		}
        		// TODO .nextInt()
        		// TODO .nextDouble()
        		// TODO .nextFloat()
        	}
        }
		
		super.visit(n, arg);
		return n;
	}
	
	
	
	@Override
	public Visitable visit(ForStmt n, CodeData arg) {


		insertStatements(n, arg, true, "", ".highlight("+ arg.getIdOfNode(n) + ");");
		super.visit(n, arg);
		
//		for(int a=3, b=5; a<99; a++, b++)
		
//		NodeList<Expression> initialization = modifyList(n.getInitialization(), arg);
//		Expression compare = n.getCompare().map(s -> (Expression) s.accept(this, arg)).orElse(null);
//		NodeList<Expression> update = modifyList(n.getUpdate(), arg);
//		Statement body = (Statement) n.getBody().accept(this, arg);
//		n.setInitialization(initialization);
//		n.setCompare(compare);
//		n.setUpdate(update);
//		n.setBody(body);
        
		return n;
	}
	
	
	
    @SuppressWarnings({ "unchecked" })
	private <N extends Node> NodeList<N> modifyList(NodeList<N> list, CodeData arg) {
        return (NodeList<N>) list.accept(this, arg);
    }
    
	private <N extends Node> NodeList<N> modifyList(Optional<NodeList<N>> list, CodeData arg) {
        return list.map(ns -> modifyList(ns, arg)).orElse(null);
    }
	
	
	
	@Override
	public Visitable visit(VariableDeclarationExpr n, CodeData arg) {
		super.visit(n, arg);
		
		for (VariableDeclarator varDec : n.getVariables()) {
			if (varDec.getType().isPrimitiveType()) {
				if (varDec.getInitializer().isPresent()) {
					insertStatements(n, arg, true, ".initializePrimitiveVariable(" + arg.getIdOfNode(n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\", \"\" + (" + varDec.getInitializer().get() + "));");
				} else {
					insertStatements(n, arg, true, ".declarePrimitiveVariable(" + arg.getIdOfNode(n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\");");
				}
			} else {
				if (varDec.getType().toString().equals("String")) { // TODO create check method for wrapper classes
					if (varDec.getInitializer().isPresent()) {
						insertStatements(n, arg, true, ".initializePrimitiveVariable(" + arg.getIdOfNode(n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\", \"\" + (" + varDec.getInitializer().get() + "));");
					} else {
						insertStatements(n, arg, true, ".instance.declarePrimitiveVariable(" + arg.getIdOfNode(n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\");");
					}
				} else {
//					System.err.println("No object declaration handling yet! In 'ReplaceVisitor.visit(VariableDeclarationExpr n, CodeData arg)'\n" + n.toString());
				}
			}
		}
		
		return n;
	}
	
	

	@Override
	public Visitable visit(VariableDeclarator n, CodeData arg) {
		return super.visit(n, arg);
	}
	
	
	
	@Override
	public Visitable visit(AssignExpr n, CodeData arg) {
		super.visit(n, arg);
		n.setValue(parseExpression(".assign(" + arg.getIdOfNode(n) + ", \"" + n.getTarget() + "\", " + n.getValue() + ")"));
		return n;
	}
	
	
	
	@Override
	public Visitable visit(BinaryExpr n, CodeData arg) {
		super.visit(n, arg);
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	

	
	@Override
	public Visitable visit(StringLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	
	@Override
	public Visitable visit(IntegerLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	
	@Override
	public Visitable visit(DoubleLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}

	@Override
	public Visitable visit(BooleanLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	
	@Override
	public Visitable visit(CharLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	
	@Override
	public Visitable visit(LongLiteralExpr n, CodeData arg) {
		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
	}
	
	@Override
	public Visitable visit(NullLiteralExpr n, CodeData arg) {
		return parseExpression(".highlightNull(" + arg.getIdOfNode(n) + ")");
	}
	
	
	
	@Override
	public Visitable visit(UnaryExpr n, CodeData arg) {
		return super.visit(n, arg);
	}
	
}
