import java.util.*;

public class RegisterNode implements IRegisterNode {

    private String id;
    private ArrayDeque<OperationEdge> edges;
    private double lazyValue;
    private boolean visited;

    RegisterNode(String id) {
        this.id = id.toLowerCase();
        this.edges = new ArrayDeque<>();
        lazyValue = 0.0;
        visited = false;
    }

    @Override
    public void visit() {
        visited = true;
    }

    @Override
    public void unVisit() {
        visited = false;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addEdge(OperationEdge operationEdge) {
        edges.add(operationEdge);
    }

    @Override
    public ArrayDeque<OperationEdge> getEdges() {
        return edges;
    }

    @Override
    public double getLazyValue() {
        return lazyValue;
    }

    @Override
    public void setLazyValue(double lazyValue) {
        this.lazyValue = lazyValue;
    }

    @Override
    public void resetLazyValue() {
        lazyValue = 0.0;
    }

    @Override
    public String toString() {
        return "RegisterNode(" + id + ")";
    }
}