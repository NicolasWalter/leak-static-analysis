package analysis.stmtHandlers;

import soot.jimple.Stmt;

public class UnknownHandler implements StmtHandler {

    UnknownHandler() { }

    @Override
    public boolean handle(Stmt stmt) {
        return false;
    }

    @Override
    public boolean hasPossibleLeak() {
        return false;
    }
}
