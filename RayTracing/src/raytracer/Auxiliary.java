package raytracer;

public class Auxiliary {
	
	public static Vector findIntersectionOfVectorAndHalfPlane(Ray ray, Vector normal, Vector pointOnPlane){
		
		double denominator = ray.getDirection().dotProduct(normal);
		double numerator = (pointOnPlane.substract(ray.getOrigin())).dotProduct(normal);
		
		/*the line starts inside the plane and is parallel to the plane*/
		if(denominator == 0 && numerator == 0){
			return new Vector(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
		}
		
		/*the line starts outside the plane and is parallel to the plane, there is no intersection*/
		else if(denominator == 0){
			return new Vector(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
		}
		
		/* the line intersects the plane once and returned value represents the intersection as the distance along the line from ray origin */
		else { 
			return (ray.retrievePoint(numerator / denominator));
		}
	}
}
