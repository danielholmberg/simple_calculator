import java.util.*;

public class CalculatorGraph implements ICalculatorGraph {

    private Map<RegisterNode, ArrayList<RegisterNode>> adjacencyMap;
    private ArrayList<RegisterNode> depthFirstSearchResult;

    CalculatorGraph() {
        adjacencyMap = new HashMap<>();
        depthFirstSearchResult = new ArrayList<>();
    }

    @Override
    public void addEdge(RegisterNode source, RegisterNode dest, Weight weight) {
        adjacencyMap.get(source).add(dest);
        source.addEdge(new OperationEdge(source, dest, weight));
    }

    @Override
    public void addNode(RegisterNode node) {
        ArrayList<RegisterNode> nodeList = new ArrayList<RegisterNode>();
        adjacencyMap.put(node, nodeList);
    }

    @Override
    public RegisterNode getRegisterNode(String id, boolean autoCreateNode) {
        for(Map.Entry<RegisterNode, ArrayList<RegisterNode>> entry : adjacencyMap.entrySet()) {
            RegisterNode node = entry.getKey();
            ArrayList<RegisterNode> adjacentNodes = entry.getValue();
            if(node.getId().equals(id)) return node;
            for(RegisterNode adjacentNode : adjacentNodes) {
                if(adjacentNode.getId().equals(id)) return adjacentNode;
            }
        }

        if(autoCreateNode) {
            RegisterNode newNode = new RegisterNode(id);
            addNode(newNode);
            return newNode;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasEdge(RegisterNode source, RegisterNode destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

    /**
     * Helper function for recursive Depth First Search algorithm.
     *
     * @param source - Starting Node
     */
    private void depthFirstSearchUtil(RegisterNode source) {
        source.visit();
        if(!depthFirstSearchResult.contains(source))
            depthFirstSearchResult.add(source);

        for(OperationEdge operationEdge : source.getEdges()) {
            RegisterNode adjacentNode = operationEdge.getDestination();
            if(!adjacentNode.isVisited()) depthFirstSearchUtil(adjacentNode);
        }
    }

    @Override
    public void depthFirstSearch(RegisterNode source) {
        depthFirstSearchUtil(source);
        //printDFSTraversal();
    }

    @Override
    public void resetDepthFirstSearch(){
        depthFirstSearchResult = new ArrayList<>();

        for(RegisterNode node : adjacencyMap.keySet()) {
            node.unVisit();
        }
    }

    /**
     * Reverses the order of the calculated traversal from the most
     * recently performed Depth First Search algorithm.
     *
     * @return List with reverse traversal order
     */
    public ArrayList<RegisterNode> getDepthFirstSearchReverseResult() {
        Collections.reverse(depthFirstSearchResult);
        return depthFirstSearchResult;
    }

    /**
     * Prints each (node) and all of its directed edges, e.g. [ (edge#1) (edge#2)... ]
     */
    public void printEdges() {
        for (RegisterNode node : adjacencyMap.keySet()) {
            ArrayList<RegisterNode> adjacentList = adjacencyMap.get(node);

            if(adjacentList.isEmpty()) System.out.print("The (" + node.getId() + ") Node has no outgoing Edges");
            else System.out.print("The (" + node.getId() + ") Node has an Edges pointing at [");

            for (RegisterNode adjacentNode : adjacentList) {
                System.out.print(" (" + adjacentNode.getId() + ")");
            }

            System.out.println(" ]");
        }
    }

    /**
     * Executes the incoming <OperationEdge>.
     *
     * @param edge
     */
    private void quickMath(OperationEdge edge) {
        RegisterNode source = edge.getSource();
        RegisterNode destination = edge.getDestination();

        Weight weight = edge.getWeight();
        OperationType type = weight.getType();
        boolean sourceIsDestination = source.getId().equals(destination.getId());
        double edgeValue = sourceIsDestination ? weight.getValue() : destination.getLazyValue();

        switch (type) {
            case ADD:
                source.setLazyValue(source.getLazyValue() + edgeValue);
                break;
            case SUBTRACT:
                source.setLazyValue(source.getLazyValue() - edgeValue);
                break;
            case MULTIPLY:
                source.setLazyValue(source.getLazyValue() * edgeValue);
                break;
            /*case DIVIDE:
                source.setLazyValue(source.getLazyValue() / edgeValue);
                break;*/
            default:
                System.out.println(type + " is not a supported operation!");
        }
    }

    @Override
    public double calculateValue(RegisterNode source) {
        double lazyValue;

        depthFirstSearch(source);
        for(RegisterNode node : getDepthFirstSearchReverseResult()) {
            ArrayDeque<OperationEdge> tempDeque = new ArrayDeque<>(node.getEdges());
            while (!tempDeque.isEmpty()) {
                OperationEdge edge = tempDeque.pop();
                quickMath(edge);
            }
        }

        lazyValue = source.getLazyValue();
        source.resetLazyValue();

        return lazyValue;
    }

    @Override
    public OperationType parseOperationType(String operationParam) {
        OperationType type;
        switch (operationParam) {
            case "add":
                type = OperationType.ADD;
                break;
            case "subtract":
                type = OperationType.SUBTRACT;
                break;
            case "multiply":
                type = OperationType.MULTIPLY;
                break;
          /*case "divide":
            type = OperationType.DIVIDE;
            break;*/
            default:
                type = OperationType.UNDEFINED;
        }
        return type;
    }

    /**
     * Prints the traversal of the most recently performed Depth First Search algorithm.
     */
    public void printDFSTraversal() {
        System.out.print("[");
        for (RegisterNode node : depthFirstSearchResult) {
            System.out.print(" " + node.getId());
        }
        System.out.println(" ]");
    }

    /**
     * Prints the reverse traversal of the most recently performed Depth First Search algorithm.
     */
    public void printReverseDFSTraversal() {
        System.out.print("[");
        for (RegisterNode node : getDepthFirstSearchReverseResult()) {
            System.out.print(" " + node.getId());
            //System.out.print("[" + node.getEdges() + "]");
        }
        System.out.println(" ]");
    }
}
