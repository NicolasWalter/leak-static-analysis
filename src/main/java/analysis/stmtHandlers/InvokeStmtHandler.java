package analysis.stmtHandlers;

import analysis.lattice.Lattice;
import analysis.valueHandlers.ValHandler;
import analysis.valueHandlers.ValueHandler;
import soot.Value;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;

import java.util.Map;

public class InvokeStmtHandler implements StmtHandler {
    private Map<String, Lattice> abstractedLocals;
    boolean possibleLeak = false;
    InvokeStmtHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Stmt stmt) {
        InvokeStmt statement = (InvokeStmt) stmt;
        Value value = statement.getInvokeExpr();
        ValueHandler valHandler = new ValHandler(this.abstractedLocals);
        boolean result = valHandler.handle(value);
        this.possibleLeak = valHandler.hasPossibleLeak();
        return result;
    }

    @Override
    public boolean hasPossibleLeak() {
        return possibleLeak;
    }
}
