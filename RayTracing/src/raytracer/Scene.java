package raytracer;

import java.util.LinkedList;
import java.util.List;

import GeometricPrimitives.GeometricPrimitive;
import GeometricPrimitives.Rectangle;
import Lights.Light;
import raytracer.Auxiliary;

public class Scene {

	private Vector backgroundColor = new Vector(0,0,0);
	private int height;
	private int width;
	List<GeometricPrimitive> geoList = new LinkedList<>();
	List<Light> lightList = new LinkedList<>();
	Vector vpTopLeft;
	Vector vpBottomLeft;
	Vector vpTopRight;
	Vector vpCenter;
	private Camera cam = new Camera(new Vector(0, 0, 0), new Vector(0,0,-1), new Vector(0,1,0));
	private double tanHTheta;
	private double tanWTheta;
	private double paneDist;
	private String backgroundTex;
	private Vector ambientLight;
	private int superSampling;
	private int useAcceleration;
	private double screenDistance = 1;
	
	public Scene(int sceneHeight, int sceneWidth) {
		setCanvasSize(sceneHeight, sceneWidth);
		calcPaneCoord();
	}

	public void setCanvasSize(int height, int width) {
		this.height = height;
		this.width = width;
		calcPaneCoord();
	}

	private void calcPaneCoord() {
		paneDist = getScreenDistance()*width;
		
		// vpCenter is the point straight ahead of the camera on the view plane
		Ray ray = new Ray(cam.getEye(), cam.getDirection());
		vpCenter = ray.retrievePoint(paneDist);
		
		// Vertical axis
		tanHTheta = height / (paneDist * 2.0);
		Vector top_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.add(cam.getUpDirection().scalarMult(paneDist*tanHTheta));
		Vector bottom_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.substract(cam.getUpDirection().scalarMult(paneDist*tanHTheta));
		
		// Horizontal axis
		tanWTheta = width / (paneDist * 2.0);
		Vector left_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.substract(cam.getRightDirection().scalarMult(paneDist*tanHTheta));
		Vector right_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.add(cam.getRightDirection().scalarMult(paneDist*tanHTheta));
		
		vpTopLeft = top_middle.add(left_middle.substract(vpCenter));
		vpBottomLeft = bottom_middle.add(left_middle.substract(vpCenter));
		vpTopRight = top_middle.add(right_middle.substract(vpCenter));
	}

	public Vector getColor(Scene scene, int x, int y) {
		
		Vector color = new Vector (0,0,0);
		
		Ray ray = constructRayThroughPixel(x, y);
		
		Intersection hit = findInteresction(scene, ray);
		if ( hit.noIntersection() )
			return getBackgroundColor();
		
		for(Light l: lightList){
			Vector impact = l.findLightImpact(scene, ray, hit);
			color = color.add(impact);
		}
		
		return color;
	}

	public static Intersection findInteresction(Scene scene, Ray ray) {
//		Intersection intersection = new Intersection(ray.getOrigin());
//		for ( GeometricPrimitive g : scene.geoList ) {
//			
//			IntersectioPoint intPoint = g.getIntersection(ray);
//			if ( intPoint != null )
//				intersection.addPoint(intPoint);
//		}
//		return intersection;
		return findInteresction(scene, ray, null);
	}

	public static Intersection findInteresction(Scene scene, Ray ray, GeometricPrimitive origin) {
		Intersection intersection = new Intersection(ray.getOrigin());
		for ( GeometricPrimitive g : scene.geoList ) {
			if ( (origin != null) && (g == origin) )
				continue;
			IntersectioPoint intPoint = g.getIntersection(ray);
			if ( intPoint != null )
				intersection.addPoint(intPoint);
		}
		return intersection;
	}

	private Ray constructRayThroughPixel(int x, int y) {
		Vector scenePaneSamplePoint = toSceneCoord(x,y);
		Ray ray = new Ray(cam.getEye(), scenePaneSamplePoint);
		return ray;
	}

	private Vector toSceneCoord(int x, int y) {
		x -= width / 2;
		y = height / 2 - y;
		Vector onLeftBorder = vpTopLeft
				.add(cam.getUpDirection()
						.scalarMult(2*paneDist*tanHTheta*((double)y / height - 0.5)));
		Vector onTopBorder = vpTopLeft
				.add(cam.getRightDirection()
						.scalarMult(2*paneDist*tanWTheta*((double)x / width + 0.5)));
		return onLeftBorder.add(onTopBorder.substract(vpTopLeft));
	}

	public void addGeomObject(GeometricPrimitive obj) {
		geoList.add(obj);
	}
	
	public void addLight(Light obj){
		lightList.add(obj);
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
	}

	public Vector getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Vector backgroundColor) {
		this.backgroundColor = backgroundColor.scalarMult(255.0);
	}

	public String getBackgroundTex() {
		return backgroundTex;
	}

	public void setBackgroundTex(String backgroundTex) {
		this.backgroundTex = backgroundTex;
	}

	public Vector getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Vector ambientLight) {
		this.ambientLight = ambientLight;
	}

	public int getSuperSampling() {
		return superSampling;
	}

	public void setSuperSampling(int superSampling) {
		this.superSampling = superSampling;
	}

	public int isUseAcceleration() {
		return useAcceleration;
	}

	public void setUseAcceleration(int val) {
		this.useAcceleration = val;
	}

	public double getScreenDistance() {
		
		return screenDistance;
	}

	public void setScreenDistance(double screenDistance) {
		this.screenDistance = screenDistance;
		calcPaneCoord();
	}
}
