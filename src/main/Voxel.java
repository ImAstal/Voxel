package main;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import rendering.MainRenderer;
import rendering.shaders.Shaders;
import scene.*;
import utils.Timer;

public class Voxel {
	
	private Scene scene;
	private MainRenderer renderer;
	private Timer timer;
	
	public Voxel() {
		this.Initialize();
		this.Run();
	}
	
	private void Initialize() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create(new PixelFormat(32, 0, 24, 0, 0));
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Shaders.Initialize();
		this.renderer = new MainRenderer();
		this.timer = new Timer();
		this.scene = new SceneGame(this.renderer);
	}
	
	private void Run() {
		while(!Display.isCloseRequested()) {
			this.scene.Update(this.timer.GetDeltaTime());
			this.renderer.Render(this.timer.GetDeltaTime());
			this.timer.Update();
			Display.sync(60);
			Display.update();
		}
		
		this.renderer.Cleanup();
		Shaders.Cleanup();
	}

	public static void main(String[] args) {
		new Voxel();
	}

}
