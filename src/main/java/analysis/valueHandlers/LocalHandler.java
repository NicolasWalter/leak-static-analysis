package analysis.valueHandlers;

import analysis.lattice.Lattice;
import soot.Local;
import soot.Value;

import java.util.Map;

public class LocalHandler implements ValueHandler {
    Map<String, Lattice> abstractedLocals;

    public LocalHandler(Map<String, Lattice> abstractedLocals) {
        this.abstractedLocals = abstractedLocals;
    }

    @Override
    public boolean handle(Value value) {
        System.out.println("localexpr");
        Local local = (Local) value;
        return Lattice.isSensible(this.abstractedLocals.getOrDefault(local.getName(), Lattice.BOTTOM));
    }

    @Override
    public boolean hasPossibleLeak() {
        return false;
    }
}
