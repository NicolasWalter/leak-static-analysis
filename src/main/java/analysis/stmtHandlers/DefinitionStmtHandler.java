package analysis.stmtHandlers;

import analysis.lattice.Lattice;
import analysis.valueHandlers.ValHandler;
import analysis.valueHandlers.ValueHandler;
import soot.Local;
import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.Stmt;

import java.util.Map;

public class DefinitionStmtHandler implements StmtHandler {
    private Map<String, Lattice> abstractedLocals;
    boolean possibleLeak = false;

    DefinitionStmtHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }
    @Override
    public boolean handle(Stmt stmt) {
        DefinitionStmt statement = (DefinitionStmt) stmt;
        Value leftOp = statement.getLeftOp();
        Value rightOp = statement.getRightOp();
        ValueHandler valHandler = new ValHandler(abstractedLocals);
        boolean possibleLeak = valHandler.handle(rightOp);
        this.possibleLeak = valHandler.hasPossibleLeak();
        if (leftOp instanceof Local && possibleLeak) {
            abstractedLocals.put(((Local) leftOp).getName(), Lattice.SENSIBLE);
        }

        return false;
    }

    @Override
    public boolean hasPossibleLeak() {
        return this.possibleLeak;
    }

    @Override
    public boolean isReturningSensible() {
        return false;
    }
}
