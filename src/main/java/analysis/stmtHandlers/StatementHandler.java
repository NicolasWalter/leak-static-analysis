package analysis.stmtHandlers;

import analysis.lattice.Lattice;
import soot.jimple.*;
import java.util.Map;

public class StatementHandler implements StmtHandler{
    private Map<String, Lattice> abstractedLocals;
    StmtHandler handler;
    public StatementHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Stmt statement) {
        StmtHandler handler = createHandler(statement);
        this.handler = handler;
        return handler.handle(statement);
    }

    @Override
    public boolean hasPossibleLeak() {
        return this.handler.hasPossibleLeak();
    }

    @Override
    public boolean isReturningSensible() {
        return this.handler.isReturningSensible();
    }

    private StmtHandler createHandler(Stmt statement) {
        if (statement instanceof InvokeStmt) {
            return new InvokeStmtHandler(this.abstractedLocals);
        }
        if (statement instanceof DefinitionStmt) {
            return new DefinitionStmtHandler(this.abstractedLocals);
        }
        if (statement instanceof ReturnStmt) {
            return new ReturnStmtHandler(this.abstractedLocals);
        }
        return new UnknownHandler();
    }
}



