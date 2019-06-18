package de.unobtanium.codesimulator.visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.unobtanium.codesimulator.codedata.CodeData;

public class RegisteringVisitor extends VoidVisitorAdapter<CodeData> {
	
	
	
	@Override
	public void visit(MethodCallExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(ForStmt n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	

	@Override
	public void visit(BlockStmt n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(VariableDeclarationExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(VariableDeclarator n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(AssignExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(BinaryExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	

	
	@Override
	public void visit(StringLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(IntegerLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(DoubleLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(BooleanLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(CharLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(LongLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(NullLiteralExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(NameExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
	
	
	
	@Override
	public void visit(UnaryExpr n, CodeData arg) {
		arg.registerNode(n);
		super.visit(n, arg);
	}
}
