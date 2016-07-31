package world;

import org.lwjgl.util.vector.Vector3f;

import rendering.Light;
import rendering.MainRenderer;
import rendering.shaders.Shaders;
import utils.ChunkUpdateThread;

import static game.Constants.*;

import java.util.ArrayList;

public class World {
	
	private MainRenderer renderer;
	private ChunkUpdateThread chunkUpdateThread;
	private ArrayList<Chunk> chunks;
	
	private Light sun;
	
	private int oldPx = 0, oldPz = 0; 
	
	public World(MainRenderer renderer) {
		this.renderer = renderer;
		
		this.chunks = new ArrayList<Chunk>();
		
		
		this.sun = new Light(new Vector3f(0.1f, 0.9f, -0.5f), new Vector3f(1f, 1f, 1f));
		Shaders.STANDARD.LoadLight(sun);

		this.chunkUpdateThread = new ChunkUpdateThread();
		this.CreateChunkList();
	}
	
	
	public void Update(float delta) {

		for(Chunk chunk : this.chunks)
			chunk.Update(delta);
		
		/*Shaders.STANDARD.LoadLight(sun);
		this.sun.GetDirection().x += 0.05f * delta;*/
	}
	
	private void CreateChunkList() {
		Vector3f playerPos = this.renderer.GetCamera().GetPosition();
		int px = (int) ((playerPos.x/CHUNK_SIZE));
		int pz = (int) ((playerPos.z/CHUNK_SIZE));
		
		for(int x = -VIEW_DISTANCE; x < VIEW_DISTANCE; x++) {
			for(int z = -VIEW_DISTANCE; z < VIEW_DISTANCE; z++) {
				int cx = px + x;
				int cz = pz + z;
				
				Chunk chunk = new Chunk(new Vector3f(cx, 0, cz));
				this.chunks.add(chunk);
				this.chunkUpdateThread.AddChunk(chunk);
				this.renderer.AddMesh(chunk.GetMesh());
			}
		}
		this.oldPx = px;
		this.oldPz = pz;
	}
	
	public void Cleanup() {
		this.chunkUpdateThread.Stop();
	}
} 
