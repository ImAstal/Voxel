package rendering;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import utils.Frustum;

public class MainRenderer {
	
	public static int CLIPPED = 0, MODEL_RENDERED = 0;
	private static int CLIP_CALCUL = 0, MODEL_CALCUL;
	
	private ArrayList<Mesh> meshList;
	private Camera camera;
	
	public MainRenderer() {
		this.meshList = new ArrayList<Mesh>();
		this.camera = new Camera();
	}
	
	public void AddMesh(Mesh mesh) {
		this.meshList.add(mesh);
	}
	
	public void RemoveMesh(Mesh mesh) {
		this.RemoveMesh(mesh, true);
	}
	
	public void RemoveMesh(Mesh mesh, boolean cleanup) {
		this.meshList.remove(mesh);
		if(cleanup)
			mesh.Cleanup();
	}
	
 	public void Render(float delta) { 	
		this.camera.Update(delta);
		CLIP_CALCUL = 0;
		MODEL_CALCUL = 0;
		Frustum frustum = Frustum.getFrustum(this.camera);
		
		glClearColor(0.1f, 0.2f, 0.8f, 1f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LESS);
		glCullFace(GL_BACK);
		
		for(Mesh mesh : this.meshList) {
			if(mesh.GetHitbox().IsInFrustum(frustum)) {
				if(mesh.DataUpdated() && mesh.IsReadyToRender()) {
					mesh.Render(this.camera);
					MODEL_CALCUL++;
				} else if(mesh.DataUpdated())
					mesh.UpdateData();
			}
			else
				CLIP_CALCUL++;
		}
		
		CLIPPED = CLIP_CALCUL;
		MODEL_RENDERED = MODEL_CALCUL;
		
		glDisable(GL_DEPTH_TEST);
	}
	
	public void Cleanup() {
		System.out.println("Cleaning " + this.meshList.size() + " meshes");
		for(Mesh mesh : this.meshList)
			mesh.Cleanup();
	}
	
	public Camera GetCamera() {
		return this.camera;
	}

}
