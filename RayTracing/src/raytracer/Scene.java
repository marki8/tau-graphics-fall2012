package raytracer;

import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import GeometricPrimitives.GeometricPrimitive;
import Lights.Light;

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
	private Vector ambientLight = new Vector(0,0,0);
	private int superSampling = 1;
	private int useAcceleration;
	private double screenDistance = 1;
	private double screenTosceneFactor = 1;
	private double paneWidth;
	private double paneHeight;
	
	private ImageData textureImg = null;
	
	public Scene(int sceneHeight, int sceneWidth) {
		setCanvasSize(sceneHeight, sceneWidth);
		//calcPaneCoord();
	}

	public void setCanvasSize(int height, int width) {
		this.height = height;
		this.width = width;
		calcPaneCoord();
	}

	public void calcPaneCoord() {
		paneDist = getScreenDistance();
		paneWidth = cam.getScreenWidth();
		paneHeight = cam.getScreenWidth()*((double)height/width);
		screenTosceneFactor = paneWidth / (double)width;
		
		// vpCenter is the point straight ahead of the camera on the view plane
		Ray ray = new Ray(cam.getEye(), cam.getDirection());
		vpCenter = ray.retrievePoint(paneDist);
		
		// Vertical axis
		tanHTheta = paneHeight / (paneDist * 2.0);
		Vector top_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.add(cam.getUpDirection().scalarMult(paneDist*tanHTheta));
		Vector bottom_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.substract(cam.getUpDirection().scalarMult(paneDist*tanHTheta));
		
		// Horizontal axis
		tanWTheta = paneWidth / (paneDist * 2.0);
		Vector left_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.substract(cam.getRightDirection().scalarMult(paneDist*tanWTheta));
		Vector right_middle = cam.getEye()
				.add(cam.getDirection().scalarMult(paneDist))
				.add(cam.getRightDirection().scalarMult(paneDist*tanWTheta));
		
		vpTopLeft = top_middle.add(left_middle.substract(vpCenter));
		vpBottomLeft = bottom_middle.add(left_middle.substract(vpCenter));
		vpTopRight = top_middle.add(right_middle.substract(vpCenter));
	}

	public Vector getColor(int x, int y) {
		
		Vector pixelColor = new Vector(0,0,0);
		int sups = getSuperSampling();
		
		for ( int i = 0 ; i < sups ; i++ ){
			for ( int j = 0 ; j < sups ; j++ ) {
				Ray ray = constructRayThroughPixel((double)x+((double)i/sups), (double)y+((double)j/sups));
				Vector color = rayColor(ray, null, x, y);

				
				/*Intersection hit = findInteresction(this, ray);
				if ( hit.noIntersection() )
				{
					pixelColor = pixelColor.add(getBackgroundColor());
					continue;
				}
				
				for(Light l: lightList){
					Vector impact = l.findLightImpact(this, ray, hit);
					color = color.add(impact);
				}
				
				if ( hit.getMinIntPoint().getGeom().getSurface().getReflectance() > 0 ){
					Vector refDir = 
							Vector.vectorReflection(ray.getDirection().scalarMult(-1), 
									hit.getMinIntPoint().getNormal() );
					Ray refl = new Ray(hit.getMinIntPoint().getLocation(), refDir);
				}
				
				Vector Ka = hit.getMinIntPoint().getGeom().getSurface().getMtlAmbient();
				double ambientX = (Ka.getDoubleX() * getAmbientLight().getDoubleX());		
				double ambientY = (Ka.getDoubleY() * getAmbientLight().getDoubleY());		
				double ambientZ = (Ka.getDoubleZ() * getAmbientLight().getDoubleZ());		
				color = color.add(new Vector(ambientX, ambientY, ambientZ));
				if ( color.getDoubleX() > 255.0 ) color.setX(255);
				if ( color.getDoubleY() > 255.0 ) color.setY(255);
				if ( color.getDoubleZ() > 255.0 ) color.setZ(255);
				// TODO: add emition component 
				*/
				pixelColor = pixelColor.add(color);
			}
		}
		
		return pixelColor.scalarMult(1.0 / (sups*sups));
		
	}

	private Vector rayColor(Ray ray, GeometricPrimitive originGeom, int x, int y) {
		Intersection hit = findInteresction(this, ray, originGeom);
		Vector color = new Vector(0,0,0);
		if ( hit.noIntersection() )
		{
			if(textureImg != null){
				int rgb = textureImg.getPixel(x, y);
//				double r = (rgb>>16)&0xFF;
//				double g = (rgb>>8)&0xFF;
//				double b = (rgb&0xFF);
				double r = ((rgb & 0xFF0000)>>16);
				double g = ((rgb & 0x00FF00)>>8 );
				double b = (rgb & 0x0000FF);

				return(new Vector(r,g,b));
			}
			return getBackgroundColor();
		}
		
		for(Light l: lightList){
			Vector impact = l.findLightImpact(this, ray, hit);
			color = color.add(impact);
		}
		
		double Ks = hit.getMinIntPoint().getGeom().getSurface().getReflectance(); 
		if ( Ks > 0 ){
			Vector refDir = 
					Vector.vectorReflection(ray.getDirection().scalarMult(-1), 
							hit.getMinIntPoint().getNormal() );
			Ray refl = new Ray(hit.getMinIntPoint().getLocation(), refDir);
			color = color.add(
					rayColor(refl, hit.getMinIntPoint().getGeom(), x, y)
					.scalarMult(Ks));
		}
		
		Vector Ka = hit.getMinIntPoint().getGeom().getSurface().getMtlAmbient();
		double ambientX = (Ka.getDoubleX() * getAmbientLight().getDoubleX());		
		double ambientY = (Ka.getDoubleY() * getAmbientLight().getDoubleY());		
		double ambientZ = (Ka.getDoubleZ() * getAmbientLight().getDoubleZ());		
		color = color.add(new Vector(ambientX, ambientY, ambientZ));
		if ( color.getDoubleX() > 255.0 ) color.setX(255);
		if ( color.getDoubleY() > 255.0 ) color.setY(255);
		if ( color.getDoubleZ() > 255.0 ) color.setZ(255);
		// TODO: add emition component 
		
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

	public static Intersection findInteresction(Scene scene, Ray r, GeometricPrimitive origin) {
		Ray ray;
		if ( origin != null )
			ray = new Ray(r.retrievePoint(0.001), r.getDirection());
		else
			ray = r;
		Intersection intersection = new Intersection(ray.getOrigin());
		for ( GeometricPrimitive g : scene.geoList ) {
			IntersectioPoint intPoint = g.getIntersection(ray);
			if ( intPoint != null )
				intersection.addPoint(intPoint);
		}
		return intersection;
	}

	private Ray constructRayThroughPixel(double d, double e) {
		Vector scenePaneSamplePoint = toSceneCoord(d,e);
		Ray ray = new Ray(cam.getEye(), scenePaneSamplePoint.substract(cam.getEye()));
		return ray;
	}

	private Vector toSceneCoord(double d, double e) {
		double scenex = (d - width / 2) * screenTosceneFactor ;
		double sceney = (height / 2 - e) * screenTosceneFactor;
		Vector onLeftBorder = vpTopLeft
				.add(cam.getUpDirection()
						.scalarMult(2*paneDist*tanHTheta*(sceney / paneHeight - 0.5)));
		Vector onTopBorder = vpTopLeft
				.add(cam.getRightDirection()
						.scalarMult(2*paneDist*tanWTheta*(scenex / paneWidth + 0.5)));
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
		textureImg = new ImageData(backgroundTex);
		textureImg = textureImg.scaledTo(width, height);


	}

	public Vector getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Vector ambientLight) {
		this.ambientLight = ambientLight.scalarMult(255.0);
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
