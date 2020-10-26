public interface IOperationEdge {
    RegisterNode getDestination();

    RegisterNode getSource();

    Weight getWeight();
}
