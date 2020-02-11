/*
 * Cycle.java
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Cycle {
	/**
	 * @author Nikhil Kaushik (nk2214@rit.edu)
	 */
	
	public static void bfs(Map<Integer, Node> mp, int start){
		boolean bipartiteFlag = false;
		boolean cycleFlag = false;
		Node startNode = mp.get(start);
		int level = 0;
		
		startNode.layer = level;
		
		Set<Integer> visited = new HashSet<>();
		List<Node> q = new ArrayList<>();
		
		q.add(startNode);
		visited.add(start);
		
		
		System.out.print(startNode.id + "(" + startNode.layer + ")" + " ");
		
		
		while(!q.isEmpty()){
			int cycleCount = 0;
			Node currNode = q.remove(0);
			level = currNode.layer + 1;
			for(Node n : currNode.adj){
				if(visited.add(n.id)){
					n.layer = level;
					q.add(n);
					System.out.print(n.id + "(" + level + ")" + " ");
				}
				if(!bipartiteFlag){
					if(level-1 == n.layer)
						bipartiteFlag = true;
				}
				if(!cycleFlag){
					if(level-1 == n.layer || level-1 == n.layer-1 || level-1 == n.layer+1)
						cycleCount++;
				}
			}
			if(cycleCount > 1)
				cycleFlag = true;
			
		}
		if(!bipartiteFlag)
			System.out.println("\nbipartite");
		else
			System.out.println("\nnot bipartite");
		
		if(cycleFlag)
			System.out.println("cycle exists");
		else
			System.out.println("acyclic");
	}
	
	public static int notVisited(Map<Integer, Node> mp){
		for(Map.Entry<Integer, Node> es : mp.entrySet()){
			if(es.getValue().layer == -1){
				return es.getKey();
			}
		}
		
		return -1;
	}

	
	
	
	
	
	public static void main(String[] args) {
		int numberOfNodes;
		Map<Integer, Node> mp = new HashMap<Integer, Node>();
		
		//File file = new File("C:\\Users\\hp\\Desktop\\665\\p2\\test5.txt");
		try (Scanner sc = new Scanner(System.in);) {
			
			while (sc.hasNext()) {
				String line = sc.nextLine().trim();
				int index = line.indexOf("#");
				if (index > -1)
					line = line.substring(0, index).trim();
				if (!line.isEmpty()) {
					System.out.println(line);
					if (!line.startsWith("N")) {
						numberOfNodes = Integer.parseInt(line);
					} else {
						String[] tempArr = line.split("\\s+");
						int st = tempArr[0].indexOf('[');
						int end = tempArr[0].indexOf(']');
						int src = Integer.parseInt(tempArr[0].substring(st+1, end));
						if(!mp.containsKey(src)){
							mp.put(src, new Node(src));
						}
						Node srcNode = mp.get(src);
						for(int i = 1; i<tempArr.length; i++){
							int target = Integer.parseInt(tempArr[i]);
							if(!mp.containsKey(target)){
								mp.put(target, new Node(target));
							}
							Node targetNode = mp.get(target);
							srcNode.addEdge(targetNode);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured.");
			e.printStackTrace();
		}
		
		int counter = 1;
		System.out.println("connected component " + counter + ":");
		Cycle.bfs(mp, 1);
		
		while(true){
			int start = Cycle.notVisited(mp);
			if(start > 0){
				System.out.println("\nconnected component " + (++counter) + ":");
				Cycle.bfs(mp, start);
			}
			else
				break;
		}
	}
}//Cycle
