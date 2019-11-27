package analysis.stmtHandlers;

import analysis.lattice.Lattice;
import analysis.valueHandlers.ValHandler;
import analysis.valueHandlers.ValueHandler;
import soot.Value;
import soot.jimple.ReturnStmt;
import soot.jimple.Stmt;

import java.util.Map;

public class ReturnStmtHandler implements StmtHandler{
    private Map<String, Lattice> abstractedLocals;
    boolean returningSensible;

    ReturnStmtHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }
    @Override
    public boolean handle(Stmt stmt) {
        ReturnStmt statement = (ReturnStmt) stmt;
        Value value = statement.getOp();
        ValueHandler valHandler = new ValHandler(this.abstractedLocals);
        boolean returningSensible = valHandler.handle(value);
        this.returningSensible = returningSensible;
        return returningSensible;
    }

    @Override
    public boolean hasPossibleLeak() {
        return false;
    }

    @Override
    public boolean isReturningSensible(){
        return this.returningSensible;
    }
}
