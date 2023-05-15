//Banuji Thathsari Liyanwala
//w1867098 , 20210342

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphParser {

    //reads the text file and creates the DirectedGraph object
    public static DirectedGraph parse(String input) throws FileNotFoundException {
        File inputFile = new File(input);
        Scanner scanner = new Scanner(inputFile);
        int verticeNo = 0;
        //get the count of the number of vertices
        while (scanner.hasNext()) {
            int inputData = scanner.nextInt();
            if (inputData > verticeNo ){
                verticeNo = inputData;
            }
        }
        verticeNo++;

        System.out.println("Number of vertices : " + verticeNo);
        scanner.close();
        DirectedGraph graph = new DirectedGraph(verticeNo);
        scanner = new Scanner(inputFile);
        //assigning the first vertex read by the scanner to "from" and the second one to "to"
        while (scanner.hasNext()) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.connectVertices(from, to);
        }
        scanner.close();
        return graph;
    }
}
