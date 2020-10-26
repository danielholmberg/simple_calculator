public class Weight implements IWeight {
    private OperationType type;
    private double value;

    Weight(OperationType type) {
        this.type = type;
    }

    Weight(OperationType type, double value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public OperationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Weight(" +
                "type: " + type +
                ", value: " + value +
                ')';
    }
}
