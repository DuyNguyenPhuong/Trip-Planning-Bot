# README HW 8
## By Duy Nguyen and Kyra Helmbold

### Overview:
Our program takes in user input(file with article titles, file with links, starting page, and ending page), and uses this information to find the shortest path between the starting and ending pages. If there is no possible path, or the start/end pages don't exist, the program will tell the user there isn't a possible path. 

We also made it so you can test our program faster if you wish. To do so, simply uncomment lines 171-193 and comment out lines 195-208. This will make it so you don't have to keep running the program or retype articles.tsv and links.tsv.

### Usage:
To use our program, compile and run, and fill in the information when prompted. See example below:

```
$ javac *.java
$ java PathFinder
What file would you like to read articles from?
articles.tsv
What file would you like to read links from?
links.tsv
What would you like the starting page to be?
ASCII
What would you like the ending page to be?
Lettuce
Yay! There is a path! Lucky you! See below :D
[ASCII, AT&T, Dallas,_Texas, California, Vegetable, Lettuce]
```

## Rubric:

### Correct use of Queue:
Our program uses a queue in the breadthFirstSearch method (lines 78-101) in PathFinder.java. We enqueue the start item (line 84), then enqueue its neighbors(line 95). We continue dequeuing the first item and adding its neighbors to the queue until the queue is empty. 
Method is shown below:
```java
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
  ```

### Finds Canada_Goose to Duran_Duran shortest path correctly:
Our program successfully finds the shortest path. See our example below: 
```
What file would you like to read articles from?
articles.tsv
What file would you like to read links from?
links.tsv
What would you like the starting page to be?
Canada_Goose
What would you like the ending page to be?
Duran_Duran
Yay! There is a path! Lucky you! See below :D
[Canada_Goose, Animal, Latin, The_Adventures_of_Tintin, Duran_Duran]
```

###  Finds 5 other shortest paths:
Our program successfully finds many shortest paths! See 5 examples below using `articles.tsv` and `links.tsv`:

Example 1:
```
What would you like the starting page to be?
Thor
What would you like the ending page to be?
United_States
Yay! There is a path! Lucky you! See below :D
[Thor, English_language, United_States]
```
Example 2:
```
What would you like the starting page to be?
Vietnam
What would you like the ending page to be?
Caspian_Sea
Yay! There is a path! Lucky you! See below :D
[Vietnam, 10th_century, Black_Sea, Caspian_Sea]
```
Example 3:
```
What would you like the starting page to be?
Mars
What would you like the ending page to be?
Soviet_Union
Yay! There is a path! Lucky you! See below :D
[Mars, Soviet_Union]
```
Example 4:
```
What would you like the starting page to be?
Bird
What would you like the ending page to be?
Gmail
Yay! There is a path! Lucky you! See below :D
[Bird, 19th_century, 21st_century, Google, Gmail]
```
Example 5:
```
What would you like the starting page to be?
Ozone
What would you like the ending page to be?
Flood
Yay! There is a path! Lucky you! See below :D
[Ozone, Arsenic, Bangladesh, Flood]
```

### States if no path is possible:
Our program will send a friendly message letting the user know that there is no path if none exists. See below:
```
What would you like the starting page to be?
Red_Sea
What would you like the ending page to be?
Scott_Special
Sorry! Looks like there are no possible paths :((
```

### toString implemented as specified:
Our toString method can be found on lines 147-153 of PathFinder.java. As specified, it returns a string that represents the number of edges and verticies in the graph. 

To test this, uncomment line 169. This should print:
```
119882 4604
```
The first number represents the total number of edges, and the second number represents the total number of verticies. 

### Sufficient documentation:
We hope our javadocx is to your liking :))

### Otherwise good style:
We have followed the style guidelines as laid out in the Java Style Guide to the best of our ability. 

### README clear and complete:
We hope you have found our README informative and helpful!!
