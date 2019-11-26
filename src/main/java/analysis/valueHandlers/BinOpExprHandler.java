package analysis.valueHandlers;

import analysis.lattice.Lattice;
import soot.Value;
import soot.jimple.BinopExpr;

import java.util.Map;

public class BinOpExprHandler implements ValueHandler {
    Map<String, Lattice> abstractedLocals;

    public BinOpExprHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Value value) {
        System.out.println("binopexpr");
        BinopExpr expr = (BinopExpr) value;
        Value firstOperand = expr.getOp1();
        Value secondOperand = expr.getOp2();
        return (new ValHandler(this.abstractedLocals).handle(firstOperand)) ||
                (new ValHandler(this.abstractedLocals).handle(secondOperand));
    }

    @Override
    public boolean hasPossibleLeak() {
        return false;
    }
}
