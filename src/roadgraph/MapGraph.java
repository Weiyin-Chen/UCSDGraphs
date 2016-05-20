/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;


import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	private HashMap<GeographicPoint, MapNode> vertice; //the variable for this class
	
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		this.vertice = new HashMap<GeographicPoint, MapNode>(); //create an empty vertice
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		int number = vertice.size();
		return number;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		Set<GeographicPoint> cwy = vertice.keySet();//get all of the geographicpoint data
		return cwy;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		Set<GeographicPoint> cwy = vertice.keySet();
		List<MapNode> bb = new ArrayList<MapNode>(); //bb is used to store MapNode
		int haha = 0;// haha is used to count the number of edges
		for(GeographicPoint aa:cwy){
			bb.add(vertice.get(aa));			
		}
		for(MapNode cc: bb){
			haha += cc.numedges();//get the number of egdes of each MapNode
		}
		
		return haha;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if (vertice.containsKey(location) || location ==null){
			return false;
		}else{
			MapNode jj = new MapNode(location);
			vertice.put(location, jj);
			return true;
		}	
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		if (!vertice.containsKey(from)||!vertice.containsKey(to)||roadName == null||roadType == null||length<0)
		{
			throw new IllegalArgumentException();
		}
		MapNode kk = vertice.get(from);
		kk.addedge(to, roadName, roadType, length);
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		
		if (!vertice.containsKey(start)||!vertice.containsKey(goal)) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Queue<GeographicPoint> toExplore = new LinkedList<GeographicPoint>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		toExplore.add(start);
		visited.add(start);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			GeographicPoint curr = toExplore.remove();
			if (curr.equals(goal)) {
				found = true;
				break;
			}
			List<GeographicPoint> neighbors = vertice.get(curr).getneighbors();
			ListIterator<GeographicPoint> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				GeographicPoint next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					nodeSearched.accept(next);
					parentMap.put(next, curr);
					toExplore.add(next);
				}
			}
		}
		
		
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<GeographicPoint>();
		}
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		if (!vertice.containsKey(start)||!vertice.containsKey(goal)) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		double inf = Double.POSITIVE_INFINITY;
		int jaa = 0;
		for(GeographicPoint qq : vertice.keySet()){
			vertice.get(qq).SetDistance(inf);
		}
		toExplore.add(vertice.get(start));
		vertice.get(start).SetDistance(0);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			GeographicPoint curr = toExplore.poll().GetPoint();
			jaa++;
			nodeSearched.accept(curr);
			if (!visited.contains(curr)){
				visited.add(curr);
				if (curr.equals(goal)) {
					found = true;
					break;
				}
				List<MapEdges> edges = vertice.get(curr).GetEdges();
				for(MapEdges zz : edges){
					double fuck = vertice.get(curr).distance()+zz.distance();
					if(!visited.contains(zz.GetEnd())&&fuck<vertice.get(zz.GetEnd()).distance()){
						vertice.get(zz.GetEnd()).SetDistance(vertice.get(curr).distance()+zz.distance());
						toExplore.add(vertice.get(zz.GetEnd()));
						parentMap.put(vertice.get(zz.GetEnd()).GetPoint(), curr);	
					}
					
				}
				
			}	
		}
		
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<GeographicPoint>();
		}
		
		System.out.println(jaa);
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (!curr.equals(start)) {
			
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		if (!vertice.containsKey(start)||!vertice.containsKey(goal)) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		double inf = Double.POSITIVE_INFINITY;
		int kll=0;
		for(GeographicPoint qq : vertice.keySet()){
			vertice.get(qq).SetDistance(inf);
			vertice.get(qq).SetDistance2(inf);
		}
		toExplore.add(vertice.get(start));
		vertice.get(start).SetDistance(0);
		vertice.get(start).SetDistance2(start.distance(goal));
		boolean found = false;
		while (!toExplore.isEmpty()) {
			GeographicPoint curr = toExplore.poll().GetPoint();
			kll++;
			nodeSearched.accept(curr);
			if (!visited.contains(curr)){
				visited.add(curr);
				if (curr.equals(goal)) {
					found = true;
					break;
				}
				List<MapEdges> edges = vertice.get(curr).GetEdges();
				for(MapEdges zz : edges){
					double fuck = vertice.get(curr).distance()+zz.distance();
					if(!visited.contains(zz.GetEnd())&&fuck<vertice.get(zz.GetEnd()).distance()){
						vertice.get(zz.GetEnd()).SetDistance(vertice.get(curr).distance()+zz.distance());
						vertice.get(zz.GetEnd()).SetDistance2(zz.GetEnd().distance(goal));
						toExplore.add(vertice.get(zz.GetEnd()));
						parentMap.put(vertice.get(zz.GetEnd()).GetPoint(), curr);	
					}
					
				}
				
			}	
		}
		
		System.out.println(kll);
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<GeographicPoint>();
		}
		
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (!curr.equals(start)) {
			
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

	
	public static void main(String[] args)
	{
		
		
		// You can use this method for testing.  
		
		
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
		

		
		
	}
	
}
