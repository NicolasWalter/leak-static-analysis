package analysis;

import analysis.lattice.Lattice;
import analysis.stmtHandlers.StatementHandler;
import soot.Body;
import soot.Local;
import soot.Unit;
import soot.jimple.Stmt;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis extends ForwardFlowAnalysis<Unit, Map<String, Lattice>> {
    private Map<String, Lattice> abstractedLocals;
    private Map<Integer, Lattice> methodParams;
    boolean hasPossibleLeak;
    boolean returningSensible;

    public Analysis(UnitGraph graph, Map<Integer, Lattice> methodParams) {
        super(graph);
        this.abstractedLocals = new HashMap<>();
        this.methodParams = methodParams;
        this.hasPossibleLeak = false;
        this.returningSensible = false;
        for (Local local : graph.getBody().getLocals()) {
            this.abstractedLocals.put(local.getName(), Lattice.BOTTOM);
        }
        doAnalysis();
    }

    public static Analysis newWithBody(Body body) {
        return new Analysis(new ExceptionalUnitGraph(body), new HashMap<>());
    }

    public static Analysis newWithBodyAndParams(Body body, Map<Integer, Lattice> methodParams) {
        return new Analysis(new ExceptionalUnitGraph(body), methodParams);
    }

    @Override
    protected void flowThrough(Map<String, Lattice> in, Unit node, Map<String, Lattice> out) {
        StatementHandler handler = new StatementHandler(in);
        handler.handle((Stmt) node);
        if (handler.hasPossibleLeak()) {
            int lineNumber = getLineNumberFromUnit(node);
            System.out.println("\n WARNING: possible leak on line: " + lineNumber + "\n");
            hasPossibleLeak = true;
        }
        this.returningSensible = handler.isReturningSensible();
        out.clear();
        out.putAll(in);
    }

    @Override
    protected Map<String, Lattice> newInitialFlow() {
        return new HashMap<>(abstractedLocals);
    }

    @Override
    protected void merge(Map<String, Lattice> in1, Map<String, Lattice> in2, Map<String, Lattice> out) {
        out.clear();
        out.putAll(in1);

        for (String variable : in2.keySet()) {
            Lattice currentValue = out.getOrDefault(variable, Lattice.BOTTOM);
            out.put(variable, Lattice.supreme(in2.get(variable), currentValue));
        }
    }

    @Override
    protected void copy(Map<String, Lattice> in, Map<String, Lattice> out) {
        out.clear();
        out.putAll(in);
    }

    public boolean hasPossibleLeak() {
        return hasPossibleLeak;
    }

    public boolean isReturningSensible(){
        return this.returningSensible;
    }

    public static int getLineNumberFromUnit(Unit unit) {
        List<Tag> tags = unit.getTags();
        for (Tag tag: tags) {
            if (tag instanceof LineNumberTag) {
                LineNumberTag castedTag = (LineNumberTag) tag;
                return castedTag.getLineNumber();
            }
        }
        return -1;
    }
}
