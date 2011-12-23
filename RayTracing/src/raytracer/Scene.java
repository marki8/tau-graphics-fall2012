package raytracer;

import java.util.LinkedList;
import java.util.List;

import GeometricPrimitives.GeometricPrimitive;
import GeometricPrimitives.Rectangle;
import Lights.Light;
import raytracer.Auxiliary;

public class Scene {

	private static final Vector BackgroundColor = new Vector(200,2000,200);
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
	
	public void setCanvasSize(int height, int width) {
		this.height = height;
		this.width = width;
		calcPaneCoord();
	}

	private void calcPaneCoord() {
		paneDist = cam.getScreenDist()*width;
		
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
		hit.updateMin();
		if ( hit.noIntersection() )
			return BackgroundColor; // TODO: return background image pixel.
		
		for(Light l: lightList){
			Vector impact = l.findLightImpact(scene, ray, hit);
			color = color.add(impact);
		}
		
		
		
		// TODO: find the color at the minimal intersection point.
		return color;
	}

	public static Intersection findInteresction(Scene scene, Ray ray) {
		Intersection intersection = new Intersection(scene.cam.getEye());
		for ( GeometricPrimitive g : scene.geoList ) {
			
			Vector intPoint = g.getIntersection(ray);
			if ( intPoint != null )
				intersection.addPoint(g, intPoint);
			
			
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
		calcPaneCoord();
	}
}
