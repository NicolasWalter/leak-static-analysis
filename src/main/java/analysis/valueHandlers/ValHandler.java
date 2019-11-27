package analysis.valueHandlers;

import analysis.lattice.Lattice;
import soot.Local;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.InvokeExpr;

import java.util.Map;

public class ValHandler implements ValueHandler {

    private Map<String, Lattice> abstractedLocals;
    private ValueHandler handler;
    public ValHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Value value) {
        ValueHandler handler = createHandler(value);
        this.handler = handler;
        return handler.handle(value);
    }

    @Override
    public boolean hasPossibleLeak(){
        return this.handler.hasPossibleLeak();
    }
    private ValueHandler createHandler(Value value) {
        if (value instanceof BinopExpr) {
            return new BinOpExprHandler(this.abstractedLocals);
        }
        if (value instanceof InvokeExpr) {
            return new InvokeExprHandler(this.abstractedLocals);
        }
        if (value instanceof Local) {
            return new LocalHandler(this.abstractedLocals);
        }
        return new UnknownValueHandler();
    }
}
