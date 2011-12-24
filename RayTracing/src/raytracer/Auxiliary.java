package raytracer;

public class Auxiliary {
	
	public static Vector findIntersectionOfVectorAndHalfPlane(Ray ray, Vector normal, Vector pointOnPlane){
		
		double denominator = ray.getDirection().dotProduct(normal);
		double numerator = (pointOnPlane.substract(ray.getOrigin())).dotProduct(normal);
		
		/*the line starts inside the plane and is parallel to the plane*/
		if(denominator == 0 && numerator == 0){
			return null;
		}
		
		/*the line starts outside the plane and is parallel to the plane, there is no intersection*/
		else if(denominator == 0){
			return null;
		}
		
		/* intersection point is in the opposite direction of the ray */
		else if ( numerator / denominator < 0 ) {
			return null;
		}
		
		/* the line intersects the plane once and returned value represents the intersection as the distance along the line from ray origin */
		else { 
			return (ray.retrievePoint(numerator / denominator));
		}
	}

	public static Vector findNearestPointOnLine(Vector point, Vector origin, Vector direction) {
		Vector PO = point.substract(origin);
		double size = PO.dotProduct(direction);
		return origin.add(direction.scalarMult(size));
	}

	public static double[] quadricRoots(double a, double b, double c) {
		// a = 0
		if ( a == 0.0 ) return new double[] {(- c / b )};
		
		// discremenant
		double det = b*b-4.0*a*c;
		if ( det < 0 )
			return null;
		
		if ( det == 0 )
			return new double[]{ (-b / (2.0 * a) ) };
		
		return new double[]{
				(-b + Math.sqrt(det)) / (2.0 * a),
				(-b - Math.sqrt(det)) / (2.0 * a)
		};
	}
}
