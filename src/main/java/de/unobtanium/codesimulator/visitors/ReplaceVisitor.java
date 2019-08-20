package de.unobtanium.codesimulator.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
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
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import de.unobtanium.codesimulator.codedata.CodeData;

public class ReplaceVisitor extends ModifierVisitor<CodeData> {

	private int tempVarCounter = 0;
	private String currentVariableName = "unset";
	
	private Map<Integer, Integer> offset = new HashMap<>();
	
	
	private final int VERYLOW = 1;
	private final int LOW = 2;
	private final int MEDIUM = 3;
	private final int HIGH = 4;
	private final int VERYHIGH = 5;

	
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
		
		int blockStmtID = codeData.getIdOfNodeOrAddNew(blockStmt);
		offset.put(blockStmtID, offset.getOrDefault(blockStmtID, -1)+1); // sets offset to 0 or increments by 1
		
		int i = ((isPlacedBeforeNode) ? 0 : 1) + offset.get(blockStmtID);
		for (String statement : statements) {
			if (statement.startsWith(".")) {
				blockStmt.addStatement(index+i++, StaticJavaParser.parseStatement("de.unobtanium.codesimulator.steps.StepCollection.getInstance()" + statement));
			} else {
				blockStmt.addStatement(index+i++, StaticJavaParser.parseStatement(statement));
			}
		}
		
	}
	
	private Expression parseExpression(String expression) {
		if (expression.startsWith(".")) {
			return StaticJavaParser.parseExpression("de.unobtanium.codesimulator.steps.StepCollection.getInstance()" + expression);
		} else {
			return StaticJavaParser.parseExpression(expression);
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
						return parseExpression(".println(" + arg.getIdOfNodeOrAddNew(n) + ", \"\")");
					} else {
						return parseExpression(".println(" + arg.getIdOfNodeOrAddNew(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
					
				} else if (n.getNameAsString().equals("print")) {
					super.visit(n, arg);
		
					if (n.getArguments().size() == 0) {
						return parseExpression(".print(" + arg.getIdOfNodeOrAddNew(n) + ", \"\")");
					} else {
						return parseExpression(".print(" + arg.getIdOfNodeOrAddNew(n) + ", \"\" + (" + n.getArguments().get(0) + "))");
					}
				}
        	} else if (childString.equals("new Scanner(System.in)")) { // Todo check if type is a java.util.Scanner instead
        		if (n.getNameAsString().equals("nextLine")) {
        			insertStatements(n, arg, true, ".readLine(" + arg.getIdOfNodeOrAddNew(n) + ");");
        		}
        		// TODO .nextInt()
        		// TODO .nextDouble()
        		// TODO .nextFloat()
        	}  else if (childString.equals("GUI")) {
        		// TODO highlight 
        		insertStatements(n, arg, true, ".highlight("+ arg.getIdOfNode(n) + ");"); // TODO highlight Method tooltip info
        	} else {
        		if (arg.getIdOfNode(n) == -1) {
        			return super.visit(n, arg);
        		}
        		
        		if (arg.highlightingDetailLevelIndex >= VERYLOW) {
        			insertStatements(n, arg, true, ".highlight("+ arg.getIdOfNode(n) + ");"); // TODO highlight Method tooltip info
        		}
        		
        	}
        }
		
		super.visit(n, arg);
		return n;
	}
	
	
	
	@Override
	public Visitable visit(ForStmt n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			insertStatements(n, arg, true, ".highlight("+ arg.getIdOfNode(n) + ");"); // TODO highlight For Loop tooltip info
		}
		
		return super.visit(n, arg);
	}
	
	@Override
	public Visitable visit(ForEachStmt n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			insertStatements(n, arg, true, ".highlight("+ arg.getIdOfNode(n) + ");"); // TODO highlight ForEach Loop tooltip info
		}
		
		return super.visit(n, arg);
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

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}
		
		for (VariableDeclarator varDec : n.getVariables()) {
			if (varDec.getType().isPrimitiveType()) {
				
				if (arg.highlightingDetailLevelIndex >= MEDIUM) {
					if (varDec.getInitializer().isPresent()) {
						String tempVarName = getNewTempVarName();
						insertStatements(n, arg, true, varDec.getTypeAsString() + " " + tempVarName + " = " + varDec.getInitializer().get().accept(this, arg) + ";");
						insertStatements(n, arg, true, ".initializePrimitiveVariable(" + arg.getIdOfNode(n.getVariables().size() > 1 ? varDec : n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\", \"\" + (" + tempVarName + "));");
						varDec.setInitializer(tempVarName);
					} else {
						insertStatements(n, arg, true, ".declarePrimitiveVariable(" + arg.getIdOfNode(n.getVariables().size() > 1 ? varDec : n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\");");
					}
				}
				
			} else {
				if (varDec.getType().toString().equals("String")) { // TODO create check method for wrapper classes

					if (arg.highlightingDetailLevelIndex >= MEDIUM) {
						if (varDec.getInitializer().isPresent()) {
							String tempVarName = getNewTempVarName();
							insertStatements(n, arg, true, varDec.getTypeAsString() + " " + tempVarName + " = " + varDec.getInitializer().get().accept(this, arg) + ";");
							insertStatements(n, arg, true, ".initializePrimitiveVariable(" + arg.getIdOfNode(n.getVariables().size() > 1 ? varDec : n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\", \"\" + (" + tempVarName + "));");
							varDec.setInitializer(tempVarName);
						} else {
							insertStatements(n, arg, true, ".instance.declarePrimitiveVariable(" + arg.getIdOfNode(n.getVariables().size() > 1 ? varDec : n) + ", \"" + varDec.getNameAsString() + "\", \"" + varDec.getTypeAsString() + "\");");
						}
					}
					
				} else {

					if (arg.highlightingDetailLevelIndex >= LOW) {
						// TODO Object declaration handling
					}
//					System.err.println("No object declaration handling yet! In 'ReplaceVisitor.visit(VariableDeclarationExpr n, CodeData arg)'\n" + n.toString());
				}
			}
			visit(varDec, arg);
		}
		
		return n;
	}
	
	
	
	@Override
	public Visitable visit(AssignExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}
		
		super.visit(n, arg);
		if (arg.highlightingDetailLevelIndex >= MEDIUM) {
			n.setValue(parseExpression(".assign(" + arg.getIdOfNode(n) + ", \"" + n.getTarget().toString() + "\", " + n.getValue() + ")"));
		}
		return n;
	}
	
	
	
	@Override
	public Visitable visit(BinaryExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}
		
		super.visit(n, arg);
		if (arg.highlightingDetailLevelIndex >= HIGH) {
			return parseExpression(".highlightBinaryExpression(" + arg.getIdOfNode(n) + ", \"" + n.getOperator().asString() + "\", " + n + ")");
		} else {
			return n;
		}
	}
	

	
	@Override
	public Visitable visit(StringLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}
	
	@Override
	public Visitable visit(IntegerLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}
		
		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}
	
	@Override
	public Visitable visit(DoubleLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}

	@Override
	public Visitable visit(BooleanLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}
	
	@Override
	public Visitable visit(CharLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}
	
	@Override
	public Visitable visit(LongLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
		} else {
			return n;
		}
	}
	
	@Override
	public Visitable visit(NullLiteralExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= VERYHIGH) {
			return parseExpression(".highlightNull(" + arg.getIdOfNode(n) + ")");
		} else {
			return n;
		}
	}
	
	
	
//	@Override
//	public Visitable visit(NameExpr n, CodeData arg) {
//		return parseExpression(".highlight(" + arg.getIdOfNode(n) + ", " + n + ")");
//	}
	
	
	
	
	@Override
	public Visitable visit(UnaryExpr n, CodeData arg) {

		if (arg.getIdOfNode(n) == -1) {
			return super.visit(n, arg);
		}

		if (arg.highlightingDetailLevelIndex >= HIGH) {
			if (n.getOperator().isPostfix()) {
				if (n.getOperator().asString().equals("++")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", true, " + n.getExpression() + ", " + n.getExpression() + "+1" +  ", " + n.toString() + ")");
				} else if (n.getOperator().asString().equals("--")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", true, " + n.getExpression() + ", " + n.getExpression() + "-1" +  ", " + n.toString() + ")");
				}
			} else { // isPrefix
				if (n.getOperator().asString().equals("++")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", false, " + n.getExpression() + ", " + n.getExpression() + "+1" +  ", " + n.toString() + ")");
				} else if (n.getOperator().asString().equals("--")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", false, " + n.getExpression() + ", " + n.getExpression() + "-1" +  ", " + n.toString() + ")");
				} else if (n.getOperator().asString().equals("-")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", false, " + n.getExpression() + ", " + "-" + n.getExpression() +  ", " + n.toString() + ")");
				} else if (n.getOperator().asString().equals("+")) {
					return parseExpression(".highlightUnaryExpression(" + arg.getIdOfNode(n) + ", false, " + n.getExpression() + ", " + "+" + n.getExpression() +  ", " + n.toString() + ")");
				}
			}
		}
		
		return super.visit(n, arg);
	}
	
}
