package analysis.valueHandlers;

import analysis.lattice.Lattice;
import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvokeExprHandler implements ValueHandler {
    Map<String, Lattice> abstractedLocals;
    boolean possibleLeak = false;

    public InvokeExprHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Value value) {
        System.out.println("invokeexpr");
        InvokeExpr invoke = (InvokeExpr) value;
        SootMethod method = invoke.getMethod();
        if (method.getName().equals("markAsSensible") && method.getDeclaringClass().getName().equals("analysis.SensitivityConverter")) {
            return markAsSensible(invoke);
        }

        if (method.getName().equals("println") && method.getDeclaringClass().getName().equals("java.io.PrintStream")) {
            return possibleLeak(invoke);
        }
       return false;
    }

    public boolean markAsSensible(InvokeExpr invoke) {
        List<Value> args = invoke.getArgs();
        List<Local> localsArgs = args.stream().filter(arg -> arg instanceof Local).map(arg -> (Local) arg).collect(Collectors.toList());
        for(Local localArg : localsArgs){
            this.abstractedLocals.put(localArg.getName(), Lattice.TOP);
        }
        return true;
    }

    public boolean possibleLeak(InvokeExpr invoke) {
        List<Value> args = invoke.getArgs();
        Optional<Boolean> sensibleArgument = args.stream().
                map(arg -> new ValHandler(this.abstractedLocals).handle(arg))
                .filter(res -> res) // if res is true
                .findFirst();

        if (sensibleArgument.isPresent()) {
            this.possibleLeak = true;
            return true;
        }
        return false;
    }

    public boolean hasPossibleLeak(){
        return this.possibleLeak;
    }
}
