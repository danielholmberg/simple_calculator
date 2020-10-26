public interface ICalculatorGraph {
    /**
     * Responsible for adding a new <OperationEdge> between <source> and <destination>.
     *
     * @param source - Source node
     * @param dest - Destination node
     * @param weight - Weight of the edge (OperationType & value)
     */
    void addEdge(RegisterNode source, RegisterNode dest, Weight weight);

    /**
     * Responsible for adding a new <RegisterNode> to the <CalculatorGraph>.
     *
     * @param node - Should not already exist in the <CalculatorGraph>
     */
    void addNode(RegisterNode node);

    /**
     * Searches for the <RegisterNode> in the <CalculatorGraph> and returns it if it exists,
     * otherwise creates it depending on the <autoCreateNode>-flag.
     *
     * @param id - Id of the <RegisterNode> we want to retrieve
     * @param autoCreateNode - Flag for determining if we should create a new <RegisterNode> and add it to the <CalculatorGraph> or not
     * @return An existing <RegisterNode>, or Null if <autoCreateNode> is false and the <id> does not correspond to an existing <RegisterNode>
     */
    RegisterNode getRegisterNode(String id, boolean autoCreateNode);

    /**
     * Determines if the source <RegisterNode> has an <OperationEdge> to the destination <RegisterNode>.
     *
     * @param source
     * @param destination
     * @return True if there is an <OperationEdge> present, False otherwise.
     */
    boolean hasEdge(RegisterNode source, RegisterNode destination);

    /**
     * Depth First Search algorithm function.
     *
     * @param source - Starting <RegisterNode>
     */
    void depthFirstSearch(RegisterNode source);

    /**
     * Resets the state of the Depth First Search algorithm.
     */
    void resetDepthFirstSearch();

    /**
     * Lazy evaluates the value for the incoming source <RegisterNode>.
     *
     * @param source
     * @return The value for the incoming source <RegisterNode>
     */
    double calculateValue(RegisterNode source);

    /**
     * Parses the incoming String for a supported <OperationType>.
     *
     * @param operationParam
     * @return A value of <OperationType>
     */
    OperationType parseOperationType(String operationParam);
}
