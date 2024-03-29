package Lights;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.Vector;

public class ParallelDirectionalLight extends Light {
	
	private Vector direction;

	
	
	public ParallelDirectionalLight(Vector direction){
		super();
		this.direction = direction;
	}
	
	public ParallelDirectionalLight() {
		// TODO Auto-generated constructor stub
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction.normalize();
	}


	@Override
	public Vector findLightImpact(Scene scene, Ray ray, Intersection hit) {

		Vector towardsLight = direction.scalarMult(-1);
		Ray rayFromObjToLight = new Ray(hit.getMinIntPoint().getLocation(), towardsLight);
		Intersection objToLightInt = Scene.findInteresction(scene, rayFromObjToLight, hit.getMinIntPoint().getGeom());
		
		if(objToLightInt.getMinIntPoint() == null) { 
			// nothing intersects with the ray from the object to the light
			return calcColor(scene, ray, hit, towardsLight);
			
		}
		
		else{
			return new Vector (0,0,0);
		}
	}
	
	
}
