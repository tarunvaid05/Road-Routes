/**
 @author Tarun Vaidhyanathan
 115510562
 R02
 */
import java.util.*;
/**
 * Represents an edge connecting two nodes in a graph.
 */
public class Edge implements Comparable<Object>{
    /** First node connected by the edge */
    private Node A;
    /** second node connected by the edge */
    private Node B;
    /** cost of the edge */
    private int cost;
    /**
     * Constructs a new edge with the specified nodes and cost.
     * @param A The first node connected by the edge.
     * @param B The second node connected by the edge.
     * @param cost The cost or weight associated with traversing the edge.
     */
    public Edge(Node A, Node B, int cost){
        this.A = A;
        this.B = B;
        this.cost = cost;
    }
    /**
     * Retrieves the first node connected by the edge.
     * @return The first node connected by the edge.
     */
    public Node getA(){
        return A;
    }
    /**
     * Retrieves the second node connected by the edge.
     * @return The second node connected by the edge.
     */
    public Node getB(){
        return B;
    }
    /**
     * Retrieves the cost or weight associated with traversing the edge.
     * @return The cost or weight associated with traversing the edge.
     */
    public int getCost(){
        return cost;
    }
    /**
     * Sets the first node connected by the edge.
     * @param A The first node connected by the edge.
     */
    public void setA(Node A){
        this.A = A;
    }
    /**
     * Sets the second node connected by the edge.
     * @param B The second node connected by the edge.
     */
    public void setB(Node B){
        this.B = B;
    }
    /**
     * Sets the cost or weight associated with traversing the edge.
     * @param cost The cost or weight associated with traversing the edge.
     */
    public void setCost(int cost){
        this.cost = cost;
    }
    /**
     * Compares this edge with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as this edge is less than, equal to, or greater than the specified object.
     * @param otherEdge The object to be compared.
     * @return A negative integer, zero, or a positive integer as this edge is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Object otherEdge){
        if(otherEdge instanceof Edge){
            if(this.cost < ((Edge) otherEdge).cost){
                return -1;
            }
            else if(this.cost > ((Edge)otherEdge).cost){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            return -2;
        }
    }
    /**
     * Returns a string representation of the edge.
     * @return A string representation of the edge in the format "Node A to Node B cost".
     */
    @Override
    public String toString(){
        return A.getName() + " to " + B.getName() + " " + cost;
    }
}
