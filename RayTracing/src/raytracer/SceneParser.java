package raytracer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import GeometricPrimitives.Box;
import GeometricPrimitives.Circle;
import GeometricPrimitives.Cylinder;
import GeometricPrimitives.GeometricPrimitive;
import GeometricPrimitives.Rectangle;
import GeometricPrimitives.Sphere;
import Lights.Light;
import Lights.ParallelDirectionalLight;
import Lights.PointLight;

import java.io.StreamTokenizer;
import java.util.LinkedList;
import java.util.List;

public class SceneParser {

	Scene scene;
	List<GeometricPrimitive> geoList = new LinkedList<>();
	List<Light> lightList = new LinkedList<>();
	int sceneHeight, sceneWidth;
	
	
	public SceneParser() {
		//this.scene = m_scene;
	}

	public Scene parse(BufferedReader br, int height, int width) throws IOException {

		String line, obj = null, param, valueStr;
		double value;
		
		sceneHeight = height;
		sceneWidth = width;
		
		// TODO: deal with cases of illegal input
		
		while((line = br.readLine()) != null) {
			if(line.length() == 0){
				continue;
			}
			else if (line.charAt(line.length() - 1) == ':') {
				obj = line.substring(0, line.length() - 1);
				//geoList.add((GeometricPrimitive) addObject(obj));
				addObject(obj);
				System.out.println("object: " + obj); //????
			}
			else {
				int equalsLoc = line.indexOf('=');
				int tmpEqualsLoc = equalsLoc; 
				int firstCharLocationBeforEq, firstCharLocationAfterEq;
				tmpEqualsLoc--;
				while (line.charAt(tmpEqualsLoc) == ' '){
					tmpEqualsLoc--;
				}
				firstCharLocationBeforEq = tmpEqualsLoc;
				tmpEqualsLoc = equalsLoc+1;
				while (line.charAt(tmpEqualsLoc) == ' '){
					tmpEqualsLoc++;
				}
				firstCharLocationAfterEq = tmpEqualsLoc;
				param = line.substring(0, firstCharLocationBeforEq+1);

				String[] args = line.substring(firstCharLocationAfterEq).trim().toLowerCase().split("\\s+");
				
				if(obj == null){
					// ????
				}
				
				addParams(param, args, obj);
					
				
				
				System.out.print("param : " + param + " value: "); //????
				for(int i=0; i<args.length; i++){
					System.out.print(args[i] + " ");
				}
				System.out.println();
			}
			
		}
		return scene;
	}

	private void addParams(String param, String[] args, String obj) {
		
		double val=0;
		Vector valVec = new Vector(0,0,0);
		if(args.length == 1) {
			val = Double.parseDouble(args[0]);
		}
		else if(args.length == 3){
			valVec = new Vector(Double.parseDouble(args[0]), Double.parseDouble(args[1]),Double.parseDouble(args[2]));
		}
		
		if(obj.equals("scene")){
			if(param.equals("background-col")) scene.setBackgroundColor(valVec);
			else if(param.equals("background-tex")) scene.setBackgroundTex(args[0]);
			else if(param.equals("ambient-light")) scene.setAmbientLight(valVec);
			else if(param.equals("super-samp-width")) scene.setSuperSampling((int) val);
			else if(param.equals("use-acceleration")) scene.setUseAcceleration((int) val);
		}
		else if(obj.equals("camera")){
			if(param.equals("eye")) scene.getCam().setEye(valVec);
			else if(param.equals("direction")) scene.getCam().setDirection(valVec);
			else if(param.equals("look-at")) scene.getCam().setLookAt(valVec);
			else if(param.equals("up-direction")) scene.getCam().setUpDirection(valVec);
			else if(param.equals("screen-dist")) scene.getCam().setScreenDist(val);
			else if(param.equals("screen-width")) scene.getCam().setScreenWidth(val);
			
		}
		else if(obj.equals("light-directed")){
			if(param.equals("color")) scene.lightList.get(scene.lightList.size()-1).setIntensity(valVec);
			if(param.equals("direction")) ((ParallelDirectionalLight)scene.lightList.get(scene.lightList.size()-1)).setDirection(valVec);
			
		}
		else if(obj.equals("light-point")){
			if(param.equals("color")) scene.lightList.get(scene.lightList.size()-1).setIntensity(valVec);
			if(param.equals("pos")) ((PointLight)scene.lightList.get(scene.lightList.size()-1)).setPosition(valVec);
			if(param.equals("attenuation")) ((PointLight)scene.lightList.get(scene.lightList.size()-1)).setAttenuation(valVec);
		}
		if(obj.equals("disc") || obj.equals ("sphere") || obj.equals ("box") || obj.equals ("cylinder") || obj.equals ("rectangle")){
			if(param.equals("mtl-type")) scene.geoList.get(scene.geoList.size()-1).getSurface().setSurfaceType(args[0]);
			if(param.equals("mtl-diffuse")){
				GeometricPrimitive g = scene.geoList.get(scene.geoList.size()-1);
				Surface s = g.getSurface();
				s.setMtlDiffuse(valVec);
			}
			if(param.equals("mtl-specular")) scene.geoList.get(scene.geoList.size()-1).getSurface().setMtlSpecular(valVec);
			if(param.equals("mtl-ambient")) scene.geoList.get(scene.geoList.size()-1).getSurface().setMtlAmbient(valVec);
			if(param.equals("mtl-emission")) scene.geoList.get(scene.geoList.size()-1).getSurface().setMtlEmission(valVec);
			if(param.equals("mtl-shininess")) scene.geoList.get(scene.geoList.size()-1).getSurface().setMtlShininess(val);
			if(param.equals("checkers-size")) scene.geoList.get(scene.geoList.size()-1).getSurface().setCheckersSize((int)val);
			if(param.equals("checkers-diffuse1")) scene.geoList.get(scene.geoList.size()-1).getSurface().setCheckersDiffuse1(valVec);
			if(param.equals("checkers-diffuse2")) scene.geoList.get(scene.geoList.size()-1).getSurface().setCheckersDiffuse2(valVec);
			if(param.equals("texture")) scene.geoList.get(scene.geoList.size()-1).getSurface().setTexture(args[0]);
			if(param.equals("reflectance")) scene.geoList.get(scene.geoList.size()-1).getSurface().setReflectance(val);
		}
		if(obj.equals("rectangle")){
			if(param.equals("p0")) ((Rectangle)scene.geoList.get(scene.geoList.size()-1)).setP0(valVec);
			if(param.equals("p1")) ((Rectangle)scene.geoList.get(scene.geoList.size()-1)).setP1(valVec);
			if(param.equals("p2")) ((Rectangle)scene.geoList.get(scene.geoList.size()-1)).setP2(valVec);
		}
		if(obj.equals("disc")){
			if(param.equals("center")) ((Circle)scene.geoList.get(scene.geoList.size()-1)).setCenter(valVec);
			if(param.equals("normal")) ((Circle)scene.geoList.get(scene.geoList.size()-1)).setNormal(valVec);
			if(param.equals("radius")) ((Circle)scene.geoList.get(scene.geoList.size()-1)).setRadius(val);
		}
		if(obj.equals("sphere")){
			if(param.equals("center")) ((Sphere)scene.geoList.get(scene.geoList.size()-1)).setCenter(valVec);
			if(param.equals("radius")) ((Sphere)scene.geoList.get(scene.geoList.size()-1)).setRadius(val);
		}
		if(obj.equals("box")){
			if(param.equals("p0")) ((Box)scene.geoList.get(scene.geoList.size()-1)).setP0(valVec);
			if(param.equals("p1")) ((Box)scene.geoList.get(scene.geoList.size()-1)).setP1(valVec);
			if(param.equals("p2")) ((Box)scene.geoList.get(scene.geoList.size()-1)).setP2(valVec);
			if(param.equals("p3")) ((Box)scene.geoList.get(scene.geoList.size()-1)).setP3(valVec);
		}
		else if(obj.equals("cylinder")){
			if(param.equals("start")) ((Cylinder)scene.geoList.get(scene.geoList.size()-1)).setStart(valVec);
			if(param.equals("direction")) ((Cylinder)scene.geoList.get(scene.geoList.size()-1)).setDirection(valVec);
			if(param.equals("length")) ((Cylinder)scene.geoList.get(scene.geoList.size()-1)).setLength(val);
			if(param.equals("radius")) ((Cylinder)scene.geoList.get(scene.geoList.size()-1)).setRadius(val);
		}
		
	}

	private void addObject(String obj) {
		if(obj.equals("scene")) scene = new Scene(sceneHeight, sceneWidth);
		else if(obj.equals("camera")) scene.setCam(new Camera());
		else if(obj.equals("light-directed")) scene.addLight(new ParallelDirectionalLight());
		else if(obj.equals("light-point")) scene.addLight(new PointLight());
//		//if(obj.equals("light-area")) return new ;
		else if(obj.equals("rectangle")) scene.addGeomObject(new Rectangle());
//		//if(obj.equals("disc")) return new ;
		else if(obj.equals("sphere")) scene.addGeomObject(new Sphere());
		else if(obj.equals("box")) scene.addGeomObject(new Box());
		else if(obj.equals("cylinder")) scene.addGeomObject(new Cylinder());
		
		
		// ?????? IF NOT RECOGNIZED THROW ERROR?
	}


	

}
