package world;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import rendering.Light;
import rendering.MainRenderer;
import rendering.shaders.Shaders;
import world.generator.WorldGenerator;

import static game.Constants.*;

public class World {
	
	private MainRenderer renderer;
	private ArrayList<Chunk> chunks, renderingChunks, toMove, toRemove;
	
	private Light sun;
	
	private boolean processing = false;
	
	public World(MainRenderer renderer) {
		this.renderer = renderer;
		
		this.chunks = new ArrayList<Chunk>();
		this.renderingChunks = new ArrayList<Chunk>();
		this.toMove = new ArrayList<Chunk>();
		this.toRemove = new ArrayList<Chunk>();
		
		
		this.sun = new Light(new Vector3f(0.1f, 0.9f, -0.5f), new Vector3f(1f, 1f, 1f));
		Shaders.STANDARD.LoadLight(sun);
	}
	
	
	public void Update(float delta) {
		// TODO Changer les listes en tableaux
		
		Vector3f playerPos = this.renderer.GetCamera().GetPosition();
		int px = (int) ((playerPos.x/CHUNK_SIZE));
		int pz = (int) ((playerPos.z/CHUNK_SIZE));
		
		if(!this.processing) {
			for(int x = -VIEW_DISTANCE; x < VIEW_DISTANCE; x++) {
				for(int z = -VIEW_DISTANCE; z < VIEW_DISTANCE; z++) {
					if(this.processing)
						continue;
					int cx = px + x;
					int cz = pz + z;
					
					String key = cx + "-" + 0 + "-" + cz;
					boolean found = false;
					for(Chunk chunk : this.renderingChunks) {
						if(chunk.GetKey().equals(key))
							found = true;
					}
					if(!found) {
						this.chunks.add(new Chunk(new Vector3f(cx, 0, cz)));
						this.processing = true;
					}
				}
			}
		}
		
		if(this.chunks.size() > 0) {
		
			for(Chunk chunk : this.chunks) {
				WorldGenerator.GenerateChunk(chunk);
				chunk.GetMesh().UpdateData();
				this.toMove.add(chunk);
			}
			
			for(Chunk chunk : this.toMove) {
				this.chunks.remove(chunk);
				this.renderingChunks.add(chunk);
				this.renderer.AddMesh(chunk.GetMesh());
			}
			
			this.toMove.clear();
		}
		

		for(Chunk chunk : this.renderingChunks) {
			if(Math.abs(chunk.GetLocation().x - px) > VIEW_DISTANCE || Math.abs(chunk.GetLocation().z - pz) > VIEW_DISTANCE)
				this.toRemove.add(chunk);
			else
				chunk.Update(delta);
		}
		
		if(this.toRemove.size() > 0) {
			for(Chunk chunk : this.toRemove) {
				this.renderingChunks.remove(chunk);
				this.renderer.RemoveMesh(chunk.GetMesh());
			}
			this.toRemove.clear();
		}
		this.processing = false;
		
		/*Shaders.STANDARD.LoadLight(sun);
		this.sun.GetDirection().x += 0.05f * delta;*/
	}
}
