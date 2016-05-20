package roadgraph;

import geography.GeographicPoint;
import java.util.List;

/**
* a class which represents the edge of a vertex
*
*
*
*/


public class MapEdges {
	private GeographicPoint start;
	private GeographicPoint end;
	private String streetname;
	private String roadtype;
	private double length;
	
	//create a new edge
	public MapEdges (GeographicPoint first, GeographicPoint second, String name, 
			String type, double length){
		this.start = first;
		this.end = second;
		this.streetname = name;
		this.roadtype = type;
		this.length = length;
	}
	
	//get a end point of the MapEdge, return a GeographicPoint
	public GeographicPoint get(){
		return this.end;
	}
	
	public GeographicPoint GetEnd(){
		return this.end;
	}
	
	public double distance(){
		return this.length;
	}

	
}
