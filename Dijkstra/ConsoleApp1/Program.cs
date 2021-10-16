// C# program for Dijkstra's 
// single source shortest path 
// algorithm. The program is for 
// adjacency matrix representation 
// of the graph. 
using System;
using System.Collections.Generic;
using System.IO;

public class DijkstrasAlgorithm
{

    private static readonly int NO_PARENT = -1;

    // Function that implements Dijkstra's 
    // single source shortest path 
    // algorithm for a graph represented 
    // using adjacency matrix 
    // representation 
    private static void dijkstra(int[,] adjacencyMatrix,
                                        int startVertex)
    {
        int nVertices = adjacencyMatrix.GetLength(0);

        // shortestDistances[i] will hold the 
        // shortest distance from src to i 
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is 
        // included / in shortest path tree 
        // or shortest distance from src to 
        // i is finalized 
        bool[] added = new bool[nVertices];

        // Initialize all distances as 
        // INFINITE and added[] as false 
        for (int vertexIndex = 0; vertexIndex < nVertices;
                                            vertexIndex++)
        {
            shortestDistances[vertexIndex] = int.MaxValue;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from 
        // itself is always 0 
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest 
        // path tree 
        int[] parents = new int[nVertices];

        // The starting vertex does not 
        // have a parent 
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all 
        // vertices 
        for (int i = 1; i < nVertices; i++)
        {

            // Pick the minimum distance vertex 
            // from the set of vertices not yet 
            // processed. nearestVertex is 
            // always equal to startNode in 
            // first iteration. 
            int nearestVertex = -1;
            int shortestDistance = int.MaxValue;
            for (int vertexIndex = 0;
                    vertexIndex < nVertices;
                    vertexIndex++)
            {
                if (!added[vertexIndex] &&
                    shortestDistances[vertexIndex] <
                    shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as 
            // processed 
            added[nearestVertex] = true;

            // Update dist value of the 
            // adjacent vertices of the 
            // picked vertex. 
            for (int vertexIndex = 0;
                    vertexIndex < nVertices;
                    vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex, vertexIndex];

                if (edgeDistance > 0
                    && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                                                    edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents);
    }

    // A utility function to print 
    // the constructed distances 
    // array and shortest paths 
    private static void printSolution(int startVertex,
                                    int[] distances,
                                    int[] parents)
    {
        int nVertices = distances.Length;
        Console.Write("Vertex\t Distance\tPath");

        for (int vertexIndex = 0;
                vertexIndex < nVertices;
                vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                Console.Write("\n" + startVertex + " -> ");
                Console.Write(vertexIndex + " \t\t ");
                Console.Write(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
        }
    }

    // Function to print shortest path 
    // from source to currentVertex 
    // using parents array 
    private static void printPath(int currentVertex,
                                int[] parents)
    {

        // Base case : Source node has 
        // been processed 
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents);
        Console.Write(currentVertex + " ");
    }

 
    // Driver Code 
    public static void Main(String[] args)
    {

        string docPath = Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments);

        int nodes = 250;
        int edges = 2546;

        int temp = 0;
      
        Random rnd = new Random();


        int[,] matrix = new int[nodes,nodes];

        for(int i = 0; i < nodes; i++)
        {
            for (int j = 0; j < nodes; j++)
            {
                matrix[i, j] = 0;
            }
        }

        for(int k = 0; k < edges;)
        {
            int row = rnd.Next(0, nodes);
            int column = rnd.Next(0, nodes);
            

            if(row != column)
            {
                if(matrix[row, column] == 0)
                {
                    int weight = rnd.Next(1, 50);
                    matrix[row, column] = weight;
                    k++;
                }
                
            }

            temp = k;
                
        }


        if (temp == edges)
        {
            Console.WriteLine("Archi ok: " + temp);
        }
        else
        {
            Console.WriteLine("Archi non ok: " + temp);
        }



        // Write the string array to a new file named "WriteLines.txt".
        using (StreamWriter outputFile = new StreamWriter(Path.Combine(docPath, "Graph_" + nodes.ToString() + "_nodes.txt")))
        {
            outputFile.WriteLine(nodes);
            outputFile.WriteLine(edges);


            for (int i = 0; i < nodes; i++)
            {
                for (int j = 0; j < nodes; j++)
                {
                    outputFile.Write(matrix[i, j] + " ");
                }
                outputFile.WriteLine();
            }
        }

        string[] lines = System.IO.File.ReadAllLines(@"C:\Users\simoa\OneDrive\Documents\"+"Graph_" + nodes.ToString() + "_nodes.txt");
        int counter = 0;
        foreach (string line in lines)
        {
            string[] words = line.Split(' ');

            foreach(string word in words)
            {
                if(word != "")
                {
                    int w = int.Parse(word);
                    if (w != 0)
                    {
                        counter++;
                        //Console.WriteLine($"<{word}>");
                    }
                    
                }
                
            }
            // Use a tab to indent each line of the file.
            //Console.WriteLine("\t" + line);
        }

        Console.Write("Counter: " + (counter-2) + "\n");

        /*

       for (int i = 0; i < nodes; i++)
       {
           for (int j = 0; j < nodes; j++)
           {
               Console.Write(matrix[i, j] + " ");
           }

           Console.Write("\n");
       }

          */




      


        /*

        for (int i = 0; i < nodes; i++)
        {
            for (int j = 0; j < nodes; j++)
            {
                Console.Write(matrix[i,j]);
            }

            Console.Write("\n");
        }

        */



        int[,] adjacencyMatrix = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                                    { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                                    { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                                    { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                                    { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                                    { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
                                    { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
                                    { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                                    { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        dijkstra(matrix, 0);

        Console.ReadLine();
    }
}

// This code has been contributed by 29AjayKumar 
