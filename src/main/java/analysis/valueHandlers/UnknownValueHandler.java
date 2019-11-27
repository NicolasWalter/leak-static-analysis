package analysis.valueHandlers;

import soot.Value;

public class UnknownValueHandler implements ValueHandler {
    @Override
    public boolean handle(Value value) {
        return false;
    }

    @Override
    public boolean hasPossibleLeak() {
        return false;
    }
}
