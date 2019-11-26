package analysis.stmtHandlers;

import soot.jimple.Stmt;

public interface StmtHandler {
    boolean handle(Stmt stmt);
    boolean hasPossibleLeak();
}
