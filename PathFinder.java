import java.util.*;

import java.io.*;

public class PathFinder {
  UnweightedGraph wikiGraph = new MysteryUnweightedGraphImplementation();
  Map<String, Integer> articleVertex = new HashMap<String, Integer>();
  Map<Integer, String> vertexArticle = new HashMap<Integer, String>();
  /**
  * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
  * nodeFile and edges specified as in edgeFile.
  * @param nodeFile name of the file with the node names
  * @param edgeFile name of the file with the edge names
  */
  public PathFinder(String nodeFile, String edgeFile){
    readNodes(nodeFile);
    readEdges(edgeFile);

  }

  /**
   * reads nodes from a file and adds them to the graph
   * @param nodeFile file to read nodes from
   */
  private void readNodes(String nodeFile) {
    File inputFile = new File(nodeFile);
    Scanner scanner = null;
    try {
        scanner = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
        System.err.println(e);
        System.exit(1);
    }

    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if(line.length() > 0 && !line.substring(0,1).equals("#")) {
        Integer nodeNum = wikiGraph.addVertex();
        articleVertex.put(line, nodeNum);
        vertexArticle.put(nodeNum, line);
      }
    }
  }

  /**
   * Reads edges from a file and adds them to the graph
   * @param edgeFile file to read edges from
   */
  private void readEdges(String edgeFile) {
    File inputFile = new File(edgeFile);
    Scanner scanner = null;
    try {
        scanner = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
        System.err.println(e);
        System.exit(1);
    }

    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if(line.length() > 0 && !line.substring(0,1).equals("#")) {
        String[] splitline = line.split("\\s+");
        String begin = splitline[0];
        String end = splitline[1];

        int beginNum = articleVertex.get(begin);
        int endNum = articleVertex.get(end);
        wikiGraph.addEdge(beginNum, endNum);
      }
    }
  }

  /**
   * Return a map of the predecessor of each node on the shortest path from the start node.
   * @param start (String) String of the start node
   * @return (Map) Map of the predecessor of each node on the shortest path from the start node.
   */
  public Map<Integer, Integer> breadthFirstSearch(String start) {
    boolean visited[] = new boolean[wikiGraph.numVerts()];
    LinkedList<Integer> queuee = new LinkedList<Integer>();
    Map<Integer, Integer> predeMap = new HashMap<Integer, Integer>();
    int startNum = articleVertex.get(start);
    visited[startNum] = true;
    queuee.add(startNum);
    predeMap.put(startNum, null);
    while(queuee.size() !=0){
      int curNum;
      curNum = queuee.poll();
      Iterable<Integer> testi = wikiGraph.getNeighbors(curNum);
      Iterator<Integer> yo = testi.iterator();
      while (yo.hasNext() ){
        int n = yo.next();
        if (visited[n] == false){
          visited[n] = true;
          queuee.add(n);
          predeMap.put(n, curNum);
        }
      }
    }
    return predeMap;
  }
  
  /**
  * Returns a shortest path from node1 to node2, represented as list that has node1 at
  * position 0, node2 in the final position, and the names of each node on the path
  * (in order) in between. If the two nodes are the same, then the "path" is just a
  * single node. If no path exists, returns an empty list.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return list of the names of nodes on the shortest path
  */
  public List<String> getShortestPath(String node1, String node2){
    ArrayList<String> listtt = new ArrayList<String>();
    if (articleVertex.containsKey(node1) == false || articleVertex.containsKey(node2) == false) {
      return listtt;
    }
    Map<Integer, Integer> map = breadthFirstSearch(node1);
    int current = 0;
    int numNode1 = articleVertex.get(node1);
    int numNode2 = articleVertex.get(node2);

    if (map.containsKey(numNode2) == false || map.containsKey(numNode1) == false  ){
      listtt.clear();
      return listtt;
    }
    current = map.get(numNode2);
    listtt.add(node2);
    while (current != numNode1){
      listtt.add(vertexArticle.get(current));
      if (map.containsKey(current) == false){
        listtt.clear();
        return listtt;
      }
      else{
        current = map.get(current);
      }
    }
    listtt.add(node1);
    Collections.reverse(listtt);
    return listtt;
  }

  /**
  * Return number of verticies and edges
  * @return String that represents the total number of verticies and edges
  */
  public String toString() {
    String total = "";
    String numEdges = Integer.toString(wikiGraph.numEdges());
    String numVerts = Integer.toString(wikiGraph.numVerts());
    total = numEdges + " " + numVerts;
    return total;
  }

  /**
   * Main method. Takes input on article and link files, and start and end pages, and finds the shortest
   * path between them. 
   * @param args
   */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    System.out.println("What file would you like to read articles from?");
    String articles = scanner.nextLine();
    System.out.println("What file would you like to read links from?");
    String links = scanner.nextLine();
    PathFinder test = new PathFinder(articles, links);

    //System.out.println(test.toString());

    // while(true){
    //   System.out.println("What would you like the starting page to be?");
    //   System.out.println("Type \"stopp\" to exit");

    //   String startingPage = scanner.nextLine();
    //   if (startingPage.equals("stopp")){
    //     break;
    //   }
    //   System.out.println("What would you like the ending page to be?");
    //   System.out.println("Type \"stopp\" to exit");
    //   String endingPage = scanner.nextLine();
    //   if (endingPage.equals("stopp")){
    //     break;
    //   }
    //   List<String> tempList = test.getShortestPath(startingPage, endingPage);
    //   if(tempList.size() == 0) {
    //     System.out.println("Sorry! Looks like there are no possible paths :((");
    //   }
    //   else {
    //     System.out.println("Yay! There is a path! Lucky you! See below :D");
    //     System.out.println(test.getShortestPath(startingPage, endingPage));
    //   }
    // }

    System.out.println("What would you like the starting page to be?");
    String startingPage = scanner.nextLine();
    System.out.println("What would you like the ending page to be?");
    String endingPage = scanner.nextLine();
    
    List<String> tempList = test.getShortestPath(startingPage, endingPage);
    if(tempList.size() == 0) {
      System.out.println("Sorry! Looks like there are no possible paths :((");
    }
    else {
      System.out.println("Yay! There is a path! Lucky you! See below :D");
      System.out.println(test.getShortestPath(startingPage, endingPage));
    }
    scanner.close();

  }
  
}
                  