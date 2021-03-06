package utils;

import java.util.ArrayList;

import world.Chunk;
import world.generator.WorldGenerator;

public class ChunkUpdateThread extends Thread {
	
	private ArrayList<Chunk> chunks;
	private volatile boolean running = false;
	
	public ChunkUpdateThread() {
		this.setName("Chunk update thread");
		this.chunks = new ArrayList<Chunk>();
		this.start();
	}

	public void run() {
		this.running = true;
		while(this.running) {
			try {
				if(this.chunks.size() == 0) {
					this.running = false;
					continue;
				}
				Chunk chunk = this.chunks.get(0);
				if(chunk == null) {
					System.err.println("dqzd");
					continue;
				}
				if(!chunk.IsGenerated())
					WorldGenerator.GenerateChunk(chunk);
				chunk.GetMesh().GenerateMesh();
				this.chunks.remove(0);
				Thread.sleep((long)(1f/60f));
			} catch(Exception e) {
				e.printStackTrace();
				this.running = false;
			}
		}
	}
	
	public void Stop() {
		this.running = false;
	}
	
	public void AddChunk(Chunk chunk) {
		this.chunks.add(chunk);
	}
	
	

}
