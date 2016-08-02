package utils;

import org.lwjgl.util.vector.Vector3f;

import world.Chunk;

public class HitResult {

	private Vector3f worldCoords, chunkCoords;
	private Chunk chunk;
	private Ray ray;
	
	public HitResult(Ray ray, Chunk chunk, Vector3f worldCoords, Vector3f chunkCoords) {
		this.ray = ray;
		this.chunk = chunk;
		this.worldCoords = worldCoords;
		this.chunkCoords = chunkCoords;
	}
	
	public Ray GetRay() {
		return this.ray;
	}
	
	public Chunk GetChunk() {
		return this.chunk;
	}
	
	public Vector3f ToWorldCoords() {
		return this.worldCoords;
	}
	
	public Vector3f ToChunkCoords() {
		return this.chunkCoords;
	}
}
