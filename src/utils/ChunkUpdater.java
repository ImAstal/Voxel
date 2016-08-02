package utils;

import world.Chunk;

public class ChunkUpdater {

	private ChunkUpdateThread thread;
	
	public ChunkUpdater() {}
	
	public void AddChunk(Chunk chunk) {
		if(thread != null) {
			if(thread.isAlive()) {
				thread.AddChunk(chunk);
				return;
			}
		} 
		thread = new ChunkUpdateThread();
		thread.AddChunk(chunk);
	}
	
	public void FastUpdate(Chunk chunk) {
		chunk.GetMesh().GenerateMesh();
		chunk.GetMesh().UpdateData();
	}
	
	public void Stop() {
		if(thread != null)
			thread.Stop();
	}
}
