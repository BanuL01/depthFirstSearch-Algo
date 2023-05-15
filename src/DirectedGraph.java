//Banuji Thathsari Liyanwala
//w1867098 , 20210342


import java.io.FileNotFoundException;
import java.util.ArrayList;

        public class DirectedGraph {
            private final int verticeNo; //store the number of vertices in the graph
            private ArrayList<Integer>[] adjecList; //stores the adjacent vertices of the graph

            public DirectedGraph(int num) {
                this.verticeNo = num;
                adjecList = new ArrayList[num];
                for (int i = 0; i < num; i++) {
                    adjecList[i] = new ArrayList<Integer>();        //list length = number of vertices
                }
            }

            //add connection between tro vertices
            public void connectVertices(int from, int to) {
                adjecList[from].add(to);
            }

            public boolean isAcyclic() {
                boolean[] visitedNodes = new boolean[verticeNo];           // keeps track on the visited nodes.
                boolean[] recStack = new boolean[verticeNo];               // keeps track of the nodes in the current recursion stack
                ArrayList<Integer> sinks = new ArrayList<Integer>();       // keeps track of the nodes without outgoing edges.
                ArrayList<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();     // keeps track of the nodes with outgoing nodes
                for (int i = 0; i < verticeNo; i++) {
                    if (!visitedNodes[i]) {
                        dfs(i, visitedNodes, recStack, sinks, cycles);
                    }
                }
                return cycles.isEmpty();
            }

            //perform Depth First Search for the unvisited nodes.
            private void dfs(int v, boolean[] visitedNodes, boolean[] CurRecStack, ArrayList<Integer> sinks,
                             ArrayList<ArrayList<Integer>> cycles) {
                visitedNodes[v] = true;
                CurRecStack[v] = true;

                for (int nextVertex : adjecList[v]) { //the next vertex that is going to check
                    if (!visitedNodes[nextVertex]) {
                        dfs(nextVertex, visitedNodes, CurRecStack, sinks, cycles);
                    }
                    else if (CurRecStack[nextVertex]) {
                        // Found a cycle
                        ArrayList<Integer> cycleList = new ArrayList<Integer>(); //store the vertices of the formed cycles
                        cycleList.add(nextVertex);
                        int u = v;
                        while (u != nextVertex) {
                            cycleList.add(u);
                            if (sinks.contains(u)) {
                                // If u is a sink, keep u as it is
                            }
                            else {
                                // If u is not a sink, choose the first adjacent vertex as u
                                u = adjecList[u].get(0);
                            }
                        }
                        cycleList.add(nextVertex);
                        cycles.add(cycleList);
                    }
                }

                CurRecStack[v] = false;  //done process with the current vertex
                if (adjecList[v].isEmpty()) {  //adjacency list empty means it is a sink vertex
                    sinks.add(v);   // v added to the sinks list
                }
            }

            public static void main(String[] args) {
                try {
                    DirectedGraph graph = GraphParser.parse("input.txt");
                    if (graph.isAcyclic()) {
                        System.out.println("The graph is acyclic.");
                    }
                    else {
                        System.out.println("The graph is cyclic. \nThe cycles are:");
                        ArrayList<ArrayList<Integer>> cycles = graph.getAllCycles(); //get all the cycles in the graph and store in cycles
                        for (int i = 0; i < cycles.size(); i++) {
                            ArrayList<Integer> cycle = cycles.get(i);
                            System.out.println(cycle);
                        }
                    }
                }
                catch (FileNotFoundException e) {
                    System.out.println("Input file not found.");
                }
            }


            //finds all cycles in the directed graph using DFS.
            public ArrayList<ArrayList<Integer>> getAllCycles() {
                ArrayList<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();
                boolean[] visitedNodes = new boolean[verticeNo];        //boolean arrays with the length = number of vertices
                boolean[] CurRecStack = new boolean[verticeNo];
                ArrayList<Integer> sinks = new ArrayList<Integer>();   //keep track of the vertices with sinks

                //do the dfs for the unvisited nodes
                for (int i = 0; i < verticeNo; i++) {
                    if (!visitedNodes[i]) {
                        dfs(i, visitedNodes, CurRecStack, sinks, cycles);
                    }
                }
                return cycles;
            }
        }