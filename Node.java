/**
 @author Tarun Vaidhyanathan
 115510562
 R02
 */
import java.util.*;
/**
 * Represents a node in a graph.
 */
public class Node {
    /** name of the node */
    private String name;
    /** Edges of the node */
    private HashSet<Edge> edges = new HashSet<>();
    /** Flags if the node is visited */
    private boolean visited;
    /** Path from this node to another*/
    private LinkedList<String> path = new LinkedList<>();
    /** Distance from one node to another */
    private int distance;
    /**
     * Constructs a new Node with the given name.
     * @param name The name of the node.
     */
    public Node(String name){
        this.name = name;
        this.distance = Integer.MAX_VALUE;
        this.visited = false;
    }
    /**
     * Retrieves the name of the node.
     * @return The name of the node.
     */
    public String getName(){
        return name;
    }
    /**
     * Retrieves the set of edges connected to this node.
     * @return The set of edges connected to this node.
     */
    public HashSet<Edge> getEdges(){
        return edges;
    }
    /**
     * Retrieves the visited status of the node.
     * @return True if the node has been visited, otherwise false.
     */
    public boolean getVisited(){
        return visited;
    }
    /**
     * Retrieves the path to this node in a traversal.
     * @return The path to this node in a traversal.
     */
    public LinkedList<String> getPath(){
        return path;
    }
    /**
     * Retrieves the distance of this node from the source node in a traversal.
     * @return The distance of this node from the source node.
     */
    public int getDistance(){
        return distance;
    }
    /**
     * Sets the name of the node.
     * @param name The name of the node.
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Sets the set of edges connected to this node.
     * @param edges The set of edges connected to this node.
     */
    public void setEdges(HashSet<Edge> edges){
        this.edges = edges;
    }
    /**
     * Sets the visited status of the node.
     * @param visited True if the node has been visited, otherwise false.
     */
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    /**
     * Sets the path to this node in a traversal.
     * @param path The path to this node in a traversal.
     */
    public void setPath(LinkedList<String> path){
        this.path = path;
    }
    /**
     * Sets the distance of this node from the source node in a traversal.
     * @param distance The distance of this node from the source node.
     */
    public void setDistance(int distance){
        this.distance = distance;
    }
    /**
     * Adds an edge connected to this node.
     * @param edge The edge to add.
     */
    public void addEdge(Edge edge){
        edges.add(edge);
    }
    /**
     * Adds a node to the path of this node in a traversal.
     * @param nodeName The name of the node to add to the path.
     */
    public void addToPath(String nodeName) {
        path.add(nodeName);
    }
    /**
     * Returns a string representation of the path to this node in a traversal.
     * @return A string representation of the path to this node.
     */
    public String printPath(){
        String s = "";
        for(String str: this.path){
            s += str + ",";
        }
        if (!this.path.isEmpty()) {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }
}