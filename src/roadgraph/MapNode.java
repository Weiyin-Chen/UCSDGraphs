package roadgraph;

import geography.GeographicPoint;
import java.util.List;
import roadgraph.MapEdges;
import java.util.LinkedList;

/**
* 
* A class which represents the vertex of graph
*
*/


public class MapNode implements Comparable<MapNode>{
	private GeographicPoint location;
	private List<MapEdges> edges;
	private double totaldistance;
	private double predict;
	
	//set up am empty MapNode
	public MapNode(GeographicPoint location){
		this.location = location;
		edges = new LinkedList<MapEdges>();	
	}
	
	//add a new edge to a exiting MapNode
	public void addedge(GeographicPoint to,
			String name, String type, double length){
		MapEdges cwy = new MapEdges(location, to, name, type, length);
		edges.add(cwy);
	}
	
	//get the number of edges of a MapNode
	public int numedges(){
		return edges.size();
	}
	
	//get the neighbors of a MapNode, return a list of GeographicPoint
	public List<GeographicPoint> getneighbors() {
		List<GeographicPoint> yy = new LinkedList<GeographicPoint>();
		for(MapEdges i : edges){
			yy.add(i.get());
		}
		return yy;
	}
	
	public int compareTo(MapNode o) {
		return ((Double)(this.totaldistance+this.predict)).compareTo(o.totaldistance+o.predict);
	}
	
	public void SetDistance (double kk){
		this.totaldistance = kk;
	}
	
	public void SetDistance2 (double ll){
		this.predict = ll;
	}
	
	public GeographicPoint GetPoint(){
		return this.location;
	}
	
	public List<MapEdges> GetEdges(){
		return this.edges;
	}
	
	public double distance(){
		return this.totaldistance;
	}
	
	public double distance2(){
		return this.totaldistance+this.predict;
	}
	
	public String kkkk(){
		return this.totaldistance+"ddd";
	}

}
