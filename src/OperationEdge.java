enum OperationType {
  ADD,
  SUBTRACT,
  MULTIPLY,
  //DIVIDE,
  UNDEFINED,
}

public class OperationEdge implements IOperationEdge {

  private RegisterNode source;
  private RegisterNode destination;
  private Weight weight;

  OperationEdge(RegisterNode source, RegisterNode destination, Weight weight) {
    this.source = source;
    this.destination = destination;
    this.weight = weight;
  }

  @Override
  public RegisterNode getDestination() {
    return destination;
  }

  @Override
  public RegisterNode getSource() {
    return source;
  }

  @Override
  public Weight getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "OperationEdge(" +
            "source: " + source +
            ", destination: " + destination +
            ", weight: " + weight +
            ')';
  }
}