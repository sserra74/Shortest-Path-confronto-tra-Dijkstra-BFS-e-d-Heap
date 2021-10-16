import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Data structure to store graph edges
class Edge
{
    int source, dest, weight;

    public Edge(int source, int dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

// Data structure to store heap nodes
class Node {
    int vertex, weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}

// class to represent a graph object
class Graph
{
    // A List of Lists to represent an adjacency list
    List<List<Edge>> adjList = null;

    // Constructor
    Graph(List<Edge> edges, int N)
    {
        adjList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            adjList.add(new ArrayList<>());
        }

        //System.out.println("Adj list size: " + adjList.size());
        //System.out.println("Edges list size: " + edges.size());

        // add edges to the undirected graph
        for (Edge edge: edges) {
            //System.out.println("Edge: ");
            System.out.println("<"  + edge.source + "," +  edge.dest + "," + edge.weight+">,");
            adjList.get(edge.source).add(edge);
        }
    }
}

class Main
{
    private static void getRoute(int[] prev, int i, List<Integer> route)
    {
        if (i >= 0) {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    // Run Dijkstra's algorithm on given graph
    public static void shortestPath(Graph graph, int source, int N, List<Edge> edges,long start)
    {
        // create min heap and push source node having distance 0
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        minHeap.add(new Node(source, 0));

        // set infinite distance from source to v initially
        List<Integer> dist = new ArrayList<>(Collections.nCopies(N, Integer.MAX_VALUE));

        // distance from source to itself is zero
        dist.set(source, 0);

        // boolean array to track vertices for which minimum
        // cost is already found
        boolean[] done = new boolean[N];
        done[source] = true;

        // stores predecessor of a vertex (to print path)
        int[] prev = new int[N];
        prev[source] = -1;

        List<Integer> route = new ArrayList<>();

        List<List<Integer>> list_routes = new ArrayList<>();

        // run till minHeap is empty
        while (!minHeap.isEmpty())
        {
            // Remove and return best vertex
            Node node = minHeap.poll();

            // get vertex number
            int u = node.vertex;

            // do for each neighbor v of u
            for (Edge edge: graph.adjList.get(u))
            {
                int v = edge.dest;
                int weight = edge.weight;

                // Relaxation step
                if (!done[v] && (dist.get(u) + weight) < dist.get(v))
                {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            // marked vertex u as done so it will not get picked up again
            done[u] = true;
        }

        for (int i = 1; i < N; ++i)
        {
            if (i != source && dist.get(i) != Integer.MAX_VALUE) {
                getRoute(prev, i, route);
                System.out.printf("Path (%d -> %d): Minimum Cost = %d and Route is %s\n", source, i, dist.get(i), route);
                //System.out.println("Route: " + route);
                list_routes.add(new ArrayList<>(route));
                //System.out.println("List route: " + list_routes);
                route.clear();
            }
        }

        long endTime   = System.nanoTime();
        long totalTime = endTime - start;
        System.out.println(totalTime);

        //System.exit(0);



        //System.out.println("Size list: " + list_routes.size());


        int s = 0;
        int d = 0;
        int cont = 1;
        int somma = 0;
        int temp_s = 0;
        int temp_d = 0;
        int index = 0;
        boolean check = false;
        boolean visitedN=false;
        List<Integer> visited = new ArrayList<>();


        for (int i = 0; i < list_routes.size();)
        {
            s = list_routes.get(i).get(index);
            d = list_routes.get(i).get(index + 1);

            //System.out.printf("Sorgente: %d - Dest: %d\n",s,d);
            //System.out.println("Visited: " + visited);

            if((index+1) < list_routes.get(i).size())
            {
                if(!visited.isEmpty())
                {
                    for (int p = 0; p < visited.size(); p=p+2)
                        if((p+1)<visited.size())
                        {
                            if(s == visited.get(p) && d == (visited.get(p + 1))) {
                                visitedN = true;
                                //System.out.printf("Arco %d - %d visitato in precedenza\n", list_routes.get(i).get(index), list_routes.get(i).get(index + 1));
                            }

                        }

                }
            }

            if(!visitedN)
            {
                visited.add(s);
                visited.add(d);

                //System.out.println("Source: " + s + " Dest: " + d);

                for (int j = i + 1; j < list_routes.size();j++)
                {
                        for (int k = 0; k < list_routes.get(j).size(); k++) {
                            if (k + 1 < list_routes.get(j).size()) {
                                temp_s = list_routes.get(j).get(k);
                                temp_d = list_routes.get(j).get(k + 1);

                                if (s == temp_s && d == temp_d)
                                    cont++;
                            }
                        }
                }

                for (Edge e: edges)
                {
                    if(e.source == s && e.dest == d)
                    {
                        //System.out.println("Cont: " + cont);
                        //System.out.println("Peso arco: " + e.weight);
                        cont = cont * e.weight;
                        check = true;

                    }

                    if(check)
                        break;

                }

                check = false;

                //System.out.println("Peso tot: " + cont);
                somma = somma + cont;
                cont = 1;
            }

            index = index + 1;
            visitedN = false;

            if((index+1) >= list_routes.get(i).size())
            {
                i++;
                index = 0;

            }
        }

        System.out.println("F.O. " + somma);

    }

    public static void main(String[] args) throws IOException {
        // initialize edges as per above diagram
        // (u, v, w) triplet represent undirected edge from
        // vertex u to vertex v having weight w

        FileInputStream fis=new FileInputStream("10V30A.txt");
        Scanner scanner = new Scanner(fis);
        int cont=0;
        int a = 0;
        int b = 0;
        int c = 0;

        List<Edge> edges = new ArrayList<Edge>();

        while(scanner.hasNextLine()){
            String[] row = scanner.nextLine().split(" ");


            for (String el : row )
            {
                if(!el.equals("")) {

                    switch (cont)
                    {
                        case 0:
                            a = Integer.parseInt(el)-1;
                            break;
                        case 1:
                            b = Integer.parseInt(el)-1;
                            break;
                        case 2:
                            c = Integer.parseInt(el);
                            break;


                    }

                    cont = cont + 1;
                }
            }

            edges.add(new Edge(a,b,c));

            cont = 0;



        }

        scanner.close();



        List<Edge> list_index = new ArrayList<Edge>();

        int count = 0;

        for (int j = 0; j <edges.size(); j++)
        {
            Edge e = edges.get(j);
            //System.out.println("-----");
            //System.out.println(e.source + "-" + e.dest+"-"+e.weight);
            for (int i=0;i<edges.size();i++)
            {
                Edge temp = edges.get(i);
                if(!e.equals(temp))
                {
                    if((e.source == temp.source)&&(e.dest == temp.dest))
                    {
                        if(e.weight<temp.weight)
                        {
                            //System.out.println("Rimuovi: " + temp.source + "-" + temp.dest+"-"+temp.weight);

                            //edges.remove(e2);

                            if(!list_index.contains(temp))
                            {
                                list_index.add(temp);
                                count=count+1;
                            }

                        }

                        else {
                            //System.out.println("Rimuovi: " + e.source + "-" + e.dest + "-" + e.weight);

                            if(!list_index.contains(e))
                            {
                                list_index.add(e);
                                count=count+1;
                            }
                            //edges.remove(e);
                        }


                    }
                }
            }

        }

        for (Edge e: list_index )
        {
            //System.out.println("Arco: " + e.source + "-" + e.dest+"-"+e.weight);
            edges.remove(e);
        }

        System.out.println("PostProcessing");



        // Set number of vertices in the graph
        final int N = 10;

        /*

        FileWriter myWriter = new FileWriter("new20000V400000A.txt");
        int archi = 400000;
        myWriter.write(N + " " + (archi-count) + System.lineSeparator());

        for (Edge e:edges) {
            //System.out.println("-----");
            //System.out.println(e.source + "-" + e.dest + "-" + e.weight);
            myWriter.write( e.source + " " + e.dest + " " + e.weight + System.lineSeparator());
        }

        myWriter.close();

        */


        long startTime = System.nanoTime();

        // construct graph
        Graph graph = new Graph(edges, N);

        int source = 0;
        shortestPath(graph, source, N, edges,startTime);
    }
}