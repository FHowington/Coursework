import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static java.lang.Double.MAX_VALUE;
import static java.lang.System.exit;

/**
 * Created by Forbes Turley on 7/19/17.
 * No code was duplicated from Sedgewick, all code below is original
 */
public class Graph {
    private static HashMap<Vertex, ArrayList<Edge>> graph;
    private static int vertices;
    private static int activeVertices;
    private static int Edges;
    private static ArrayList<Vertex> vertexList;
    private static Scanner sc;

    public static void main(String[] args) {
        System.out.println("Possible commands:");
        System.out.println("Q to quit");
        System.out.println("M: Minimum spanning tree");
        System.out.println("S i j: Shortest path by latency between vertices i and j");
        System.out.println("P i j x: All paths between i and j of latency less than x");
        System.out.println("D i: Disconnects vertex i");
        System.out.println("U i: Connectes vertex i");
        System.out.println("C i j x: Changes latenxy of edge between i and j or creates edge if none exists");
        System.out.println("E i j x: Generates all paths between i and j of value less than x with no common edges");
        System.out.println("N: Creates a new vertex and prompts user for edges to this vertex");
        // Main loops repeatedly requesting used input and calling the desired functions'
        try {
            sc = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            exit(0);
        }
        vertices = sc.nextInt();
        activeVertices = vertices;
        vertexList = new ArrayList<>();
        graph = new HashMap<>(vertices);
        Edges = sc.nextInt();

        // Initializes the graph linked representation of each of the vertices
        for (int i = 0; i < vertices; i++) {
            Vertex tempVertex = new Vertex(i);
            vertexList.add(tempVertex);
            graph.put(tempVertex, new ArrayList<>());
        }
        for (int i = 0; i < Edges; i++) {
            Vertex from = vertexList.get(sc.nextInt());
            Vertex to = vertexList.get(sc.nextInt());
            int latency = sc.nextInt();
            graph.get(from).add(new Edge(from, to, latency));
            graph.get(to).add(new Edge(to, from, latency));
        }
        sc = new Scanner(System.in);
        // Calls the functions for each valid used input
            while (true) {
                try {
                    System.out.println("\nEnter your next instruction\n");

                    char input = sc.next(".").charAt(0);
                    input = Character.toUpperCase(input);
                    switch (input) {
                        case 'R':
                            report();
                            break;

                        case 'D':
                            deactivate(sc.nextInt());
                            break;

                        case 'M':
                            StringBuilder s = new StringBuilder();
                            boolean result = mst(1, s);
                            if (result)
                                System.out.println(s);
                            else
                                System.out.println("No path is possible");
                            break;

                        case 'U':
                            activate(sc.nextInt());
                            break;

                        case 'C':
                            change(sc.nextInt(), sc.nextInt(), sc.nextInt());
                            break;

                        case 'Q':
                            System.exit(0);
                            break;

                        case 'S':
                            shortest(sc.nextInt(), sc.nextInt());
                            break;

                        case 'P':
                            permute(vertexList.get(sc.nextInt()), vertexList.get(sc.nextInt()), sc.nextDouble());
                            break;

                        case 'N':
                            newVertex();
                            break;
                        case 'E':
                            noCommon(vertexList.get(sc.nextInt()), vertexList.get(sc.nextInt()), sc.nextDouble());
                            break;
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid selection");
                    sc = new Scanner(System.in);
            }
        }
    }


    // Outputs the status of the network. Connectivity is based on whether a valid MST exists
    // Components are connected by iteratively finding all possible connections from a starting node until all
    // active nodes exists within some component
    private static void report() {
        int connected = 0;

        if(mst(0,new StringBuilder()))
            System.out.println("The network is currently connected\n");
        else
            System.out.println("The network is disconnected\n");

        System.out.println("The following nodes are currently down:");
        if(activeVertices==vertices)
            System.out.println("None");
        else
            for(Vertex v:vertexList)
                if(!v.active)
                    System.out.print(v.identity + " ");

        System.out.println("\nThe following nodes are currently up:");
        if(activeVertices==0)
            System.out.println("None");
        else
            for(Vertex v:vertexList)
                if(v.active)
                    System.out.print(v.identity + " ");
        System.out.println();

        int i = 0;
        int componentnum = 0;
        ArrayList<Vertex> connectedVertices = new ArrayList<>();

        while (connected < activeVertices) {
            if (vertexList.get(i).active) {
                ArrayList<Vertex> net = new ArrayList<>();
                buildGraph(net, vertexList.get(i), connectedVertices);
                if (!net.isEmpty()) {
                    System.out.println("\nConnected component " + componentnum++ + ":");
                    for(Vertex netVertex: net) {
                        connected++;
                        ArrayList<Edge> Edges = graph.get(netVertex);

                        System.out.print(netVertex.identity + ": ");
                        for (int k = 0; k < Edges.size(); k++)
                            if (Edges.get(k).to.active)
                                System.out.print(Edges.get(k).from.identity + "->" +
                                        Edges.get(k).to.identity + "  " + Edges.get(k).latency + "   ");
                        System.out.println();
                    }
                }
            }
            i++;
        }
    }

    // Sets the status of a node to inactive
    private static void deactivate(int vecnum) {
        vertexList.get(vecnum).active = false;
        System.out.println("Vertex " + vecnum + " is now offline");
        activeVertices--;
    }

    // Sets the status of a node to active
    private static void activate(int vecnum) {
        vertexList.get(vecnum).active = true;
        System.out.println("Vertex " + vecnum + " is now online");
        activeVertices++;
    }

    // Changes the latency of the edge between the supplies vertices. If no such edge exists, the edge is deactivated
    // If the new latency is less than zero, the edge is deactivated
    private static void change(int from, int to, int lat) {
        boolean edgeFound = false;
        ArrayList<Edge> edges = graph.get(vertexList.get(from));
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to.identity == to) {
                if (lat < 0)
                    edges.remove(edges.get(i));
                else
                    edges.get(i).latency = lat;
                edgeFound = true;
            }
        }
        edges = graph.get(vertexList.get(to));
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to.identity == from) {
                if (lat < 0)
                    edges.remove(edges.get(i));
                else
                    edges.get(i).latency = lat;
            }
        }
        if (!edgeFound) {
            graph.get(vertexList.get(from)).add(new Edge(vertexList.get(from), vertexList.get(to), lat));
            graph.get(vertexList.get(to)).add(new Edge(vertexList.get(to), vertexList.get(from), lat));
        }
    }

    // Used by the report method to find all connected vertices
    private static void buildGraph(ArrayList<Vertex> connected, Vertex v, ArrayList<Vertex> alreadyConnected) {
        ArrayList<Edge> connectedVertices = graph.get(v);

        for (int i = 0; i < connectedVertices.size(); i++) {
            Edge nextEdge = connectedVertices.get(i);
            if (nextEdge.to.active) {
                if (!alreadyConnected.contains(nextEdge.to)) {
                    connected.add(nextEdge.to);
                    alreadyConnected.add(nextEdge.to);
                    buildGraph(connected, nextEdge.to, alreadyConnected);
                }
            }
        }
    }

    // Finds the minimum spanning tree. Because this can also be used to determine if the network is up
    // it also takes int use to determine how output should be formatted
    private static boolean mst(int use, StringBuilder s) {

        ArrayList<Vertex> mstVertices = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ArrayList<Edge> edges;
        int i=0;
        while(true) {
            if (vertexList.get(i).active) {
                edges = graph.get(vertexList.get(0));
                break;
            }
            i++;
        }

        mstVertices.add(vertexList.get(0));
        for (Edge edge : edges)
            pq.add(edge);

        while (mstVertices.size() < activeVertices) {
            if(pq.isEmpty())
                return false;
            Edge e = pq.remove();
            if (!mstVertices.contains(e.to) && e.to.active){
                edges = graph.get(e.to);
                for (Edge edge : edges)
                    pq.add(edge);
                mstVertices.add(e.to);
                if(use==1)
                    s = s.append(e.from.identity + "->" + e.to.identity + "  " + e.latency + "\n");
            }
        }
        return true;
    }

    // Finds the shortest route between two nodes used Djiksstra's method. The implementation of the
    // indexable PQ used by this exists within the djikstrasQueue class
    private static void shortest(int from, int to) {
        ArrayList<Vertex> visited = new ArrayList<>();
        visited.add(vertexList.get(from));
        DjikstraQueue weights = new DjikstraQueue(vertices);
        HashMap<Vertex, DjikstraQueue.Node> nodes = new HashMap<>();

        for (Vertex v : vertexList) {
            if (v.identity != from) {
                DjikstraQueue.Node temp = new DjikstraQueue.Node(MAX_VALUE, v);
                weights.add(temp);
                nodes.put(v, temp);
            } else {
                nodes.put(v, new DjikstraQueue.Node(0, v));
            }
        }

        while (true) {
            if (from == to) {
                System.out.println("Shortest total path length is " + nodes.get(vertexList.get(from)).value +
                        ". Path is:");
                System.out.println(nodes.get(vertexList.get(from)).path);
                break;
            }

            ArrayList<Edge> edges = graph.get(vertexList.get(from));

            for (Edge edge : edges) {
                if (!visited.contains(edge.to))
                    if (edge.to.active)
                        if (nodes.get(edge.to).value > nodes.get(edge.from).value + edge.latency) {
                            weights.update(nodes.get(edge.to), nodes.get(edge.from).value + edge.latency);
                            nodes.get(edge.to).path = nodes.get(edge.from).path + edge.from.identity + "->" +
                                    edge.to.identity + "  " + edge.latency + "  ";
                        }
            }

            Vertex v;
            while (true) {
                if (!weights.isEmpty()) {
                    v = weights.remove();
                    if (v.active && nodes.get(v).value != MAX_VALUE)
                        break;
                } else {
                    System.out.println("No path possible.");
                    return;
                }
            }
            if (v.active && nodes.get(v).value != MAX_VALUE) {
                visited.add(v);
                from = v.identity;
            }
        }
    }

    // Used to generate all routes between two points less than a maximum value
    // Does so by the recursive helper method
    private static void permute(Vertex from, Vertex to, double max) {
        ArrayList<ArrayList<Edge>> results = new ArrayList<>();
        ArrayList<Vertex> visited = new ArrayList<>();
        visited.add(from);
        permuteHelper(from, to, new ArrayList<>(), 0, max, results, visited);
        if(!results.isEmpty()) {
            System.out.println("Distinct paths from " + from.identity + " to " + to.identity +
                    " (differing by at least one edge)");
            for (int i=0;i<results.size();i++) {
                double total=0;
                for(Edge e: results.get(i))
                    total+=e.latency;
                System.out.print("Path " + (i+1) + ": Total weight (" + total + ") ");
                for (Edge e: results.get(i))
                    System.out.print(e.from.identity + "->" + e.to.identity + "  " + e.latency + "  ");
                System.out.println();
            }
            System.out.println("Total paths: " + results.size());
        }
        else
            System.out.println("No paths possible");
    }

    // Recursive helper method for finding all permutations of a path between two points
    private static void permuteHelper(Vertex v, Vertex target, ArrayList<Edge> edgeList, double total, double max,
                                      ArrayList<ArrayList<Edge>> results, ArrayList<Vertex> vertexVisited) {
        if (v == target) {
            ArrayList<Edge> temp = new ArrayList<>();
            for (Edge e : edgeList)
                temp.add(e);
            results.add(temp);
            return;
        }

        ArrayList<Edge> edges = graph.get(v);
        for (Edge e : edges)
            if (e.to.active && e.latency + total < max && !vertexVisited.contains(e.to)){
                edgeList.add(e);
                vertexVisited.add(e.to);
                permuteHelper(e.to, target, edgeList, total + e.latency, max, results,vertexVisited);
                vertexVisited.remove(e.to);
                edgeList.remove(e);
            }
    }

    // Creates a new vertex and allows the used to supply all edges leading to/from this node
    private static void newVertex(){
        System.out.println("The new vertex is vertex number " + vertices);
        System.out.println("Enter the edges connected to this new vertex one by one");
        System.out.println("In the format FROM TO LATENCY");
        System.out.println("Enter only a single direction for each edge to add. Enter D to finish");
        Vertex v = new Vertex(vertices);
        vertexList.add(v);
        graph.put(v,new ArrayList<>());
        activeVertices++;
        vertices++;

        Scanner sc = new Scanner(System.in);
        while(true){
            String s = sc.next();
            if(s.equals("D"))
                return;
            int to = sc.nextInt();
            double lat = sc.nextDouble();
            graph.get(vertexList.get(Integer.parseInt(s))).add(new Edge(vertexList.get(Integer.parseInt(s)),vertexList.get(to),lat));
            graph.get(vertexList.get(to)).add(new Edge(vertexList.get(to),vertexList.get(Integer.parseInt(s)),lat));
        }
    }


    // Generates a permutation of all possible routes with no common edges between two points
    private static void noCommon(Vertex from, Vertex to, double max){
        ArrayList<Edge> used = new ArrayList<>();
        int i = 1;
        while(true) {
            double total = 0;
            ArrayList<Edge> result = noCommonHelper(from, to, used, max);
            if (result.isEmpty())
                break;
            for(Edge e: result)
                total+=e.latency;
            System.out.print("Path " + (i) + ": Total weight (" + total + ") ");
            for (Edge e : result)
                System.out.print(e.from.identity + "->" + e.to.identity + "  " + e.latency + "   ");
            System.out.println();
            i++;
        }
    }

    // Recursive helper method for noCommon
    private static ArrayList<Edge> noCommonHelper(Vertex from, Vertex to, ArrayList<Edge> used, double max) {
        ArrayList<Vertex> visited = new ArrayList<>();
        visited.add(from);
        DjikstraQueue weights = new DjikstraQueue(vertices);
        HashMap<Vertex, DjikstraQueue.Node> nodes = new HashMap<>();
        ArrayList<Edge> result = new ArrayList<>();

        for (Vertex v : vertexList) {
            if (v.identity != from.identity) {
                DjikstraQueue.Node temp = new DjikstraQueue.Node(MAX_VALUE, v);
                weights.add(temp);
                nodes.put(v, temp);
            } else {
                nodes.put(v, new DjikstraQueue.Node(0, v));
            }
        }

        boolean complete = false;
        while (!complete) {
            if (from == to) {
                for(Edge e: nodes.get(from).pathList) {
                    result.add(e);
                    used.add(e);
                    used.add(reverse(e));
                }
                break;
            }

            ArrayList<Edge> edges = graph.get(from);

            for (Edge edge : edges) {
                if (!visited.contains(edge.to) && !used.contains(edge))
                    if (edge.to.active)
                        if (nodes.get(edge.to).value > nodes.get(edge.from).value + edge.latency &&
                                nodes.get(edge.from).value + edge.latency < max) {
                            weights.update(nodes.get(edge.to), nodes.get(edge.from).value + edge.latency);
                            nodes.get(edge.to).pathList = new ArrayList<>();
                            for(Edge e: nodes.get(from).pathList)
                                nodes.get(edge.to).pathList.add(e);
                            nodes.get(edge.to).pathList.add(edge);
                        }
            }

            Vertex v = null;
            while (true) {
                if (!weights.isEmpty()) {
                    v = weights.remove();
                    if (v.active && nodes.get(v).value != MAX_VALUE)
                        break;
                } else {
                    return result;
                }
            }

            if (v != null && v.active && nodes.get(v).value != MAX_VALUE) {
                visited.add(v);
                from = v;
            }
        }
        return result;
    }

    // Simply returns the twin edge for a given input edge
    private static Edge reverse(Edge e){
        ArrayList<Edge> edges = graph.get(e.to);
        for(Edge edge: edges)
            if(edge.to == e.from)
                return edge;
        return null;
    }

    private static class Edge implements Comparable<Edge> {
        Vertex from;
        Vertex to;
        double latency;

        private Edge(Vertex from, Vertex to, double latency) {
            this.from = from;
            this.to = to;
            this.latency = latency;
        }

        public int compareTo(Edge e) {
            return (int) (this.latency - e.latency);
        }
    }


    // Class representing an indexable priority queue for use in finding the shortest route between two points
    private static class DjikstraQueue {
        int size;
        Node[] djarray;
        int position = 0;

        // Constructor for the queue. Number of elements in queue must be known ahead of time
        private DjikstraQueue(int s) {
            this.size = s;
            djarray = new Node[size];
        }

        // Adds a new node to the indexable queue
        private void add(Node n) {
            djarray[++position] = n;
            n.pos = position;
        }

        // Removes an element from the queue. Sinks node to the correct position in the tree
        private Vertex remove() {
            Vertex x = djarray[1].ver;
            djarray[1] = djarray[size - 1];
            djarray[1].pos = 1;
            djarray[size - 1] = null;
            size--;

            int current = 1;
            while (true) {
                if (current * 2 < size && djarray[current * 2] != null && djarray[current * 2].value < djarray[current].value) {
                    if (current * 2 < size && djarray[current * 2 + 1] != null && djarray[current * 2 + 1].value < djarray[current * 2].value) {
                        Node temp = djarray[current];
                        djarray[current] = djarray[current * 2 + 1];
                        djarray[current].pos = current;
                        djarray[current * 2 + 1] = temp;
                        temp.pos = current * 2 + 1;
                        current = current * 2 + 1;
                    } else {
                        Node temp = djarray[current];
                        djarray[current] = djarray[current * 2];
                        djarray[current].pos = current;
                        djarray[current * 2] = temp;
                        temp.pos = current * 2;
                        current = current * 2;
                    }
                } else if (current * 2 < size && djarray[current * 2 + 1] != null && djarray[current * 2 + 1].value < djarray[current].value) {
                    Node temp = djarray[current];
                    djarray[current] = djarray[current * 2 + 1];
                    djarray[current].pos = current;
                    djarray[current * 2 + 1] = temp;
                    temp.pos = current * 2 + 1;
                    current = current * 2 + 1;
                } else
                    break;
            }
            return x;
        }

        // Updates the weight associates with a vertex
        private void update(Node n, double d) {
            n.value = d;
            int current = n.pos;
            while (true) {
                if (current > 1) {
                    if (djarray[current / 2].value > djarray[current].value) {
                        Node temp = djarray[current];
                        djarray[current] = djarray[current / 2];
                        djarray[current].pos = current;
                        djarray[current / 2] = temp;
                        temp.pos = current / 2;
                        current = current / 2;
                    } else
                        break;
                } else
                    break;
            }
        }

        private boolean isEmpty() {
            return size == 1;
        }

        // Inner class representing a node in the queue
        private static class Node {
            int pos;
            double value;
            Vertex ver;
            String path = "";
            ArrayList<Edge> pathList;

            private Node(double y, Vertex v) {
                this.value = y;
                this.ver = v;
                pathList = new ArrayList<>();
            }
        }
    }

    // Class representing a vertex and it's state
    private static class Vertex {
        boolean active;
        int identity;

        private Vertex(int identity) {
            this.active = true;
            this.identity = identity;
        }
    }

}

