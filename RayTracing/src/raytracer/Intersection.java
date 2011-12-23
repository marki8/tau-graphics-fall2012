package raytracer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import GeometricPrimitives.GeometricPrimitive;

public class Intersection {

	private Vector origin;
	private IntersectioPoint minIntPoint = null;

	public Intersection(Vector rayOrigin) {
		origin = rayOrigin;
	}
	
	//private List<Vector> intPoints = new ArrayList<>();
	//private List<GeometricPrimitive> intGeom =new ArrayList<>();
	private List<IntersectioPoint> intData = new LinkedList<>();
	private double minDist;
	
	public boolean noIntersection() {
		return (intData.size() == 0);
	}

	public IntersectioPoint getMinIntPoint() {
		return minIntPoint;
	}

	public void addPoint(IntersectioPoint intPoint) {
		intData.add(intPoint);
		if ( minIntPoint == null )
		{
			minIntPoint = intPoint;
			minDist = intPoint.getLocation().distance(origin);
		}
		else if ( minDist > intPoint.getLocation().distance(origin) ) {
			minDist = intPoint.getLocation().distance(origin);
			minIntPoint = intPoint;
		}
	}

}
