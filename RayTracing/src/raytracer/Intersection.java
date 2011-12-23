package raytracer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GeometricPrimitives.GeometricPrimitive;

public class Intersection {

	private Vector origin;
	private Vector minIntPoint;
	private GeometricPrimitive minIntGeom;

	public Intersection(Vector rayOrigin) {
		origin = rayOrigin;
	}
	
	//private List<Vector> intPoints = new ArrayList<>();
	//private List<GeometricPrimitive> intGeom =new ArrayList<>();
	private Map<Vector, GeometricPrimitive> intData = new HashMap<>();
	
	public void addPoint(GeometricPrimitive g, Vector intPoint) {
		//intPoints.add(intPoint);
		//intGeom.add(g);
		intData.put(intPoint, g);
	}

	public void updateMin() {
		if ( noIntersection() )
			return;
		double minDist = origin.substract( intData.keySet().iterator().next() ).length();
		for ( Vector intPoint : intData.keySet() ) {
			double dist = origin.substract( intPoint ).length();
			if ( dist <= minDist){
				setMinIntPoint(intPoint);
				minDist = dist;
				setMinIntGeom(intData.get(intPoint));
			}
		}
		
	}

	public boolean noIntersection() {
		return (intData.size() == 0);
	}

	public Vector getMinIntPoint() {
		return minIntPoint;
	}

	private void setMinIntPoint(Vector minIntPoint) {
		this.minIntPoint = minIntPoint;
	}

	public GeometricPrimitive getMinIntGeom() {
		return minIntGeom;
	}

	private void setMinIntGeom(GeometricPrimitive minIntGeom) {
		this.minIntGeom = minIntGeom;
	}

}
