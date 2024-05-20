/**
 @author Tarun Vaidhyanathan
 115510562
 R02
 */
import java.util.*;
import big.data.DataSource;

/** Calculator of how to get from one city to another */
public class RoadCalculator {
    /**
     * Represents the graph of cities
     */
    private static HashMap<String, Node> graph;
    /**
     * Represents the minimum spanning tree of the graph.
     */
    private static LinkedList<Edge> mst;
    /**
     * Represents the current edges in the graph.
     */
    private static ArrayList<Edge> currentEdges = new ArrayList<>(0);

    /**
     * Main method for executing the road calculator program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter graph URL: ");
        String location = input.nextLine().trim();
        graph = buildGraph(location);

        System.out.println("Loading Map...");
        System.out.println();
        System.out.println("Cities:");
        ArrayList<String> cities = new ArrayList<>(graph.keySet());
        Collections.sort(cities);
        for (String city : cities) {
            System.out.println(city);
        }
        Comparator<Edge> alphabetic = new Comparator<Edge>() {
            @Override
            public int compare(Edge a, Edge b) {
                if (a.getA().getName().compareTo(b.getA().getName()) == 0) {
                    return a.getB().getName().compareTo(b.getB().getName());
                } else {
                    return a.getA().getName().compareTo(b.getA().getName());
                }
            }
        };
        Collections.sort(currentEdges, alphabetic);
        System.out.println();
        System.out.println("Roads:");
        for (Edge edge : currentEdges) {
            System.out.println(edge.toString());
        }
        System.out.println();
        System.out.println("Minimum Spanning Tree:");
        mst = buildMST(graph);
        Collections.sort(mst, alphabetic);
        for (Edge e : mst) {
            System.out.println(e.toString());
        }
        System.out.println();

        while (true) {
            System.out.println("Enter a starting point for shortest path or Q to quit:");
            String choice = input.nextLine().trim();
            if (choice.equals("Q") || choice.equals("q")) {
                System.out.println("Goodbye; PSA, there's a cop on the right in 3 miles!");
                System.exit(0);
            } else {
                System.out.println("Enter a destination:");
                String destination = input.nextLine();
                if (!graph.containsKey(destination) || !graph.containsKey(choice)) {
                    System.out.println("Cannot find the given city.");
                } else {
                    int distance = Djikstra(graph, choice, destination);
                    System.out.println("Distance: " + distance);
                    System.out.println("Path:");
                    System.out.println(graph.get(choice).printPath());
                }
            }
        }
    }

    /**
     * Builds the graph of cities and roads from the provided data source.
     *
     * @param location The URL of the data source.
     * @return The graph of cities and roads.
     */
    public static HashMap<String, Node> buildGraph(String location) {
        HashMap<String, Node> cities = new HashMap<String, Node>();
        DataSource ds = DataSource.connectXML(location);
        ds.load();
        String cityNamesStr = ds.fetchString("cities");
        String[] cityNames = cityNamesStr.substring(1, cityNamesStr.length() - 1).replace("\"", "").split(",");
        String roadNamesStr = ds.fetchString("roads");
        String[] roadNames = roadNamesStr.substring(1, roadNamesStr.length() - 1).split("\",\"");
        roadNames[0] = roadNames[0].substring(1);
        roadNames[roadNames.length - 1] = roadNames[roadNames.length - 1].substring(0, roadNames[roadNames.length - 1].length() - 1);
        for (String s : cityNames) {
            cities.put(s, new Node(s));
        }
        for (int i = 0; i < roadNames.length; i++) {
            String[] roadElements = roadNames[i].split(",");
            String nodeA = roadElements[0];
            String nodeB = roadElements[1];
            int cost = Integer.parseInt(roadElements[2]);

            Edge temp = new Edge(cities.get(nodeA), cities.get(nodeB), cost);
            cities.get(nodeA).addEdge(temp);
            cities.get(nodeB).addEdge(temp);
            currentEdges.add(temp);
        }
        return cities;
    }

    /**
     * Builds the minimum spanning tree of the graph using Prim's algorithm.
     *
     * @param graph The graph of cities and roads.
     * @return The minimum spanning tree of the graph.
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph) {
        LinkedList<Edge> mst = new LinkedList<>();
        HashSet<String> visitedNodes = new HashSet<>();
        PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingInt(Edge::getCost));

        String startNodeName = graph.keySet().iterator().next();
        visitedNodes.add(startNodeName);
        for (Edge edge : graph.get(startNodeName).getEdges()) {
            edges.add(edge);
        }

        while (!edges.isEmpty()) {
            Edge minEdge = edges.poll();
            String nodeA = minEdge.getA().getName();
            String nodeB = minEdge.getB().getName();

            if (visitedNodes.contains(nodeA) && visitedNodes.contains(nodeB)) {
                continue;
            }
            mst.add(minEdge);

            String nextNode;
            if (visitedNodes.contains(nodeA)) {
                nextNode = nodeB;
            } else {
                nextNode = nodeA;
            }
            visitedNodes.add(nextNode);
            for (Edge e : graph.get(nextNode).getEdges()) {
                String nextName;
                if (e.getA().getName().equals(nextNode)) {
                    nextName = e.getB().getName();
                } else {
                    nextName = e.getA().getName();
                }
                if (!visitedNodes.contains(nextName)) {
                    edges.add(e);
                }
            }
        }
        return mst;
    }

    /**
     * Calculates the shortest path between two cities using Dijkstra's algorithm.
     *
     * @param graph  The graph of cities and roads.
     * @param source The starting city.
     * @param dest   The destination city.
     * @return The distance of the shortest path between the source and destination cities.
     */
    public static int Djikstra(HashMap<String, Node> graph, String source, String dest) {
        Node currentNode = graph.get(source);
        currentNode.setDistance(0);
        ArrayList<String> unvisitedNodes = new ArrayList<>(graph.keySet());
        graph.get(source).addToPath(source);
        while (!unvisitedNodes.isEmpty()) {
            Node minDistanceNode = null;
            int minDistance = Integer.MAX_VALUE;
            for (String nodeName : unvisitedNodes) {
                Node node = graph.get(nodeName);
                if (node.getDistance() < minDistance) {
                    minDistance = node.getDistance();
                    minDistanceNode = node;
                }
            }

            currentNode = minDistanceNode;
            currentNode.setVisited(true);
            unvisitedNodes.remove(currentNode.getName());
            if (currentNode.getName().equals(dest)) {
                graph.get(source).addToPath(dest);
                break;
            }

            for (Edge e : currentNode.getEdges()) {
                Node next = null;
                if (e.getA() == currentNode) {
                    next = e.getB();
                } else if (e.getB() == currentNode) {
                    next = e.getA();
                }
                if (next != null) {
                    int newDistance = currentNode.getDistance() + e.getCost();
                    if (newDistance < next.getDistance()) {
                        next.setDistance(newDistance);
                        if (currentNode.getVisited()) {
                            graph.get(source).addToPath(next.getName());
                        }
                    }
                }
            }
        }
        return graph.get(dest).getDistance();
    }
}



