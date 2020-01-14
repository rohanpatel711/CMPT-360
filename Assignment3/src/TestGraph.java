import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
//****************
//START: READ ONLY
//****************
public class TestGraph {
//****************
//END: READ ONLY
//****************
// YOU CAN DEFINE YOUR OWN FUNCTIONS HERE IF YOU REALLY NEED ONE

    public static String DFS(Graph g,Node q1, Stack stack){
		
        for (Enumeration j = q1.outId.keys(); j.hasMoreElements(); ) {
            Integer x = (Integer)j.nextElement();
            if (stack.contains(x)){
                return "False";
            }
            else{
                stack.push(x);
                if (g.nodeIDs.get(x).outdegree>0){
                    if(DFS(g,g.nodeIDs.get(x),stack)=="False"){
                        return "False";
                    }
                }
            }
        }
        return "True";
    }

//****************
//START: READ ONLY
//****************

	public static String DAGTest(Graph G) {
//****************
//END: READ ONLY
//****************
		//WRITE YOUR NSID: rsp502
		//start: edit and write your code here
        Node q;
        for (Enumeration k = G.nodes.keys(); k.hasMoreElements();) {
            q = (Node) k.nextElement();
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(q.id);
            if (q.outdegree>0) {
                if(DFS(G, q, stack)=="False"){
                    return "False";
                }
            }
        }
        return "True";
            //end: write your code here

	}
//****************
//START: READ ONLY
//****************
	/**
	 * Main Function.
	 */
	public static void main(String[] args) {

		BufferedReader reader;
		File file = new File("output.txt");
		int a=0,b = 0;
		Graph G;
		String line;
		try {
			reader = new BufferedReader(new FileReader("TestGraph.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			G = new Graph();
			while(true){
				line = reader.readLine();
				if(line == null) break;
				if(line.equals("END")) {
					//G.print();		
					writer.write(DAGTest(G) + "\n");
					G = new Graph();
					continue;
				}

				StringTokenizer st = new StringTokenizer(line,",");
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				G.insertEdge(new Node(a), new Node(b));
				//G.print();
			}
			writer.flush();
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not locate input file.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}



class Node{
	public int id;
	public int weight;
	public int outdegree;
	public int indegree;
	public Hashtable<Integer, Integer> outId = new Hashtable<Integer, Integer>();
	public Hashtable<Integer, Integer> inId = new Hashtable<Integer, Integer>();
	Node(int x){id = x;  indegree = 0; outdegree = 0; weight = 0;}
}

class Edge{
	public String id;
	public Node source;
	public Node target;
	Edge(Node x, Node y){source = x; target = y;   id = x.id+""+y.id;}
}
class Graph{
	public Hashtable<Node, Integer> nodes = new Hashtable<Node, Integer>();
	public Hashtable<Integer, Node> nodeIDs = new Hashtable<Integer, Node>();
	public Hashtable<String, Integer> edges = new Hashtable<String, Integer>();
	public boolean insertNode(Node x){
		if(nodeIDs.containsKey(x.id)) return false;
		else {
			nodes.put(x, x.id);
			nodeIDs.put(x.id,x);
		}
		return true;
	}
	public boolean insertEdge(Node x, Node y){
		//first add the nodes if it is not already there
		boolean status;
		status = insertNode(x);
		if(status == false) x = nodeIDs.get(x.id);
		status = insertNode(y);
		if(status == false) y = nodeIDs.get(y.id);
		//add the edge
		Edge e = new Edge(x,y);
		if(edges.containsKey(e.id)) return false;
		else {
			edges.put(e.id, 0);
			//updare degrees
			x.outdegree++;
			y.indegree++;
			//update lists
			x.outId.put(y.id,0);
			y.inId.put(x.id,0);

		}
		return true;
	}
	public void print(){
		System.out.println("Number of Nodes = " + nodes.size());
		System.out.println("Number of Edges = " + edges.size());
		System.out.println();
		Node q;
		for (Enumeration k = nodes.keys(); k.hasMoreElements();)
		{
			q = (Node)k.nextElement();
			System.out.println("Node ID : " + q.id);
			System.out.println("indegree = " + q.indegree);
			System.out.println("outdegree = " + q.outdegree);

			for (Enumeration j = q.inId.keys(); j.hasMoreElements();) {
				System.out.println("In List: " + j.nextElement());
			}
			for (Enumeration j = q.outId.keys(); j.hasMoreElements();) {
				System.out.println("Out List: " + j.nextElement());
			}
			System.out.println();
		}

	}
}