package analysis.lattice;

public enum Lattice {
    BOTTOM("bottom"), NOT_SENSIBLE("not_sensible"), SENSIBLE("sensible"), TOP("top");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    Lattice(String name) {
        this.name = name;
    }

    public static Lattice supreme(Lattice val1, Lattice val2) {
        if (val1.equals(TOP) || val2.equals(TOP)) {
            return TOP;
        }
        if (val1.equals(SENSIBLE) || val2.equals(SENSIBLE)) {
            return SENSIBLE;
        }
        if (val1.equals(NOT_SENSIBLE) || val2.equals(NOT_SENSIBLE)) {
            return NOT_SENSIBLE;
        }
        return BOTTOM;
    }

    public static boolean isSensible(Lattice val) {
        return val == SENSIBLE || val == TOP;
    }
}
