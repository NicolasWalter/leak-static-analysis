package analysis.valueHandlers;

import soot.Value;

public interface ValueHandler {
    boolean handle(Value value);
    boolean hasPossibleLeak();
}
