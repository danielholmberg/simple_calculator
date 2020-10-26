import java.util.ArrayDeque;

public interface IRegisterNode {
    void visit();

    void unVisit();

    boolean isVisited();

    String getId();

    void addEdge(OperationEdge operationEdge);

    ArrayDeque<OperationEdge> getEdges();

    double getLazyValue();

    void setLazyValue(double lazyValue);

    void resetLazyValue();
}
