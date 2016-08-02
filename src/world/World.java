package world;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import rendering.Light;
import rendering.MainRenderer;
import rendering.shaders.Shaders;
import utils.ChunkUpdater;
import utils.HitResult;
import utils.Raycast;

import static game.Constants.*;

import java.util.ArrayList;

public class World {
	
	private MainRenderer renderer;
	private ChunkUpdater chunkUpdater;
	private ArrayList<Chunk> chunks;
	private Light sun;
	
	//private int oldPx = 0, oldPz = 0; 
	
	public World(MainRenderer renderer) {
		this.renderer = renderer;
		
		this.chunks = new ArrayList<Chunk>();
		
		
		this.sun = new Light(new Vector3f(0.1f, 0.9f, -0.5f), new Vector3f(1f, 1f, 1f));
		Shaders.STANDARD.LoadLight(sun);

		this.chunkUpdater = new ChunkUpdater();
		this.CreateChunkList();
	}
	
	
	private boolean leftClick = false, rightClick = false;
	public void Update(float delta) {

		for(Chunk chunk : this.chunks)
			chunk.Update(delta);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed())
			Mouse.setGrabbed(false);
		
		while (Mouse.next()){
			if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0 && !leftClick) {
		        	if(!Mouse.isGrabbed())
		        		Mouse.setGrabbed(true);
		        	// TODO NE FONCTIONNE PAS AVEC LES CHUNKS AUX COORDONNEES NEGATIVES
		        	HitResult hit = Raycast.RaycastWorldBlock(this, Raycast.GetRay(this.renderer.GetCamera()), 0.2f);
		        	if(hit != null) {
		        		hit.GetChunk().SetBlockId((byte)0, (int)hit.ToChunkCoords().x, (int)hit.ToChunkCoords().y, (int)hit.ToChunkCoords().z);
		        		hit.GetChunk().SetDirty();
		        		this.chunkUpdater.FastUpdate(hit.GetChunk());
		        	}
		            leftClick = true;
		        }
		        if (Mouse.getEventButton() == 1 && !rightClick) {
		        	HitResult hit = Raycast.RaycastWorldNextBlock(this, Raycast.GetRay(this.renderer.GetCamera()), 0.2f);
		        	if(hit != null) {
		        		hit.GetChunk().SetBlockId((byte)1, (int)(hit.ToChunkCoords().x), (int)(hit.ToChunkCoords().y), (int)(hit.ToChunkCoords().z));
		        		hit.GetChunk().SetDirty();
		        		this.chunkUpdater.FastUpdate(hit.GetChunk());
		        	}
		        }
		    } else {
		        if (Mouse.getEventButton() == 0 && leftClick)
		            leftClick = false;
		        if (Mouse.getEventButton() == 1 && rightClick)
		            rightClick = false;
			}
		}
		
		/*Shaders.STANDARD.LoadLight(sun);
		this.sun.GetDirection().x += 0.05f * delta;*/
	}
	
	public byte GetBlock(int x, int y, int z) {
		int cx = (x / CHUNK_SIZE) * CHUNK_SIZE;
		int cy = 0;
		int cz= (z / CHUNK_SIZE) * CHUNK_SIZE;
		
		Chunk chunk = GetChunk(cx, cy, cz);
		if(chunk == null)
			return 0;
		return chunk.GetBlockId(x + cx * CHUNK_SIZE, y + cy * CHUNK_HEIGHT, z+ cz * CHUNK_SIZE);
	}
	
	public Chunk GetChunk(int x, int y, int z) {
		Vector3f location = new Vector3f(x, y, z);
		for(Chunk chunk : this.chunks) {
			if(chunk.GetLocation().equals(location))
				return chunk;
		}
		return null;
	}
	
	public ChunkUpdater GetChunkUpdater() {
		return this.chunkUpdater;
	}
	
	private void CreateChunkList() {
		Vector3f playerPos = this.renderer.GetCamera().GetPosition();
		int px = (int) ((playerPos.x/CHUNK_SIZE));
		int pz = (int) ((playerPos.z/CHUNK_SIZE));
		
		for(int x = -VIEW_DISTANCE; x < VIEW_DISTANCE; x++) {
			for(int z = -VIEW_DISTANCE; z < VIEW_DISTANCE; z++) {
				int cx = px + x;
				int cz = pz + z;
				if(cx < 0 || cz < 0)
					continue;
				
				Chunk chunk = new Chunk(new Vector3f(cx, 0, cz));
				this.chunks.add(chunk);
				this.chunkUpdater.AddChunk(chunk);
				this.renderer.AddMesh(chunk.GetMesh());
			}
		}
		//this.oldPx = px;
		//this.oldPz = pz;
	}
	
	public void Cleanup() {
		this.chunkUpdater.Stop();
	}
} 
