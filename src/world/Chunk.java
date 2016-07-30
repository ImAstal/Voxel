package world;

import org.lwjgl.util.vector.Vector3f;

import rendering.ChunkMesh;

import static game.Constants.*;

public class Chunk {

	private ChunkMesh mesh;
	private Vector3f location;
	
	private byte[][][] blocks;
	public byte[][] leftBorder, rightBorder, topBorder, bottomBorder, backBorder, frontBorder;
	

	public Chunk(Vector3f location) {
		this.location = location;
		this.mesh = new ChunkMesh(this);
		this.blocks = new byte[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		this.leftBorder = new byte[CHUNK_SIZE][CHUNK_HEIGHT];
		this.rightBorder = new byte[CHUNK_SIZE][CHUNK_HEIGHT];
		this.frontBorder = new byte[CHUNK_SIZE][CHUNK_HEIGHT];
		this.backBorder = new byte[CHUNK_SIZE][CHUNK_HEIGHT];
		this.topBorder = new byte[CHUNK_SIZE][CHUNK_SIZE];
		this.bottomBorder = new byte[CHUNK_SIZE][CHUNK_SIZE];
	}
	
	public void Update(float delta) {
		
	}	
	
	public byte GetBlockId(int x, int y, int z) {
		if(!this.IsInsideChunk(x, y, z))
			return -1;
		return this.blocks[x][y][z];
	}
	
	private int TestWidth(int xz) {
		return (xz >= 0 && xz < CHUNK_SIZE) ? 1 : 0;
	}
	
	private int TestHeight(int y) {
		return (y >= 0 && y < CHUNK_HEIGHT) ? 1 : 0;
	}
	
	public boolean IsOpaque(int x, int y, int z) {
		if(!this.IsInsideChunk(x, y, z)) {
			int tx = TestWidth(x);
			int tz = TestWidth(z);
			int ty = TestHeight(y);
			if((tx + ty + tz) == 2) {
				if((tx + ty) == 2) {
					if(z == -1)
						return this.backBorder[x][y] != 0;
					else
						return this.frontBorder[x][y] != 0;
				} else if((ty + tz) == 2) {
					if(x == -1)
						return this.leftBorder[z][y] != 0;
					else
						return this.rightBorder[z][y] != 0;
				}
			}
			return true;
		}
		else
			return this.blocks[x][y][z] != 0;
	}
	
	public ChunkMesh GetMesh() {
		return this.mesh;
	}
	
	public Vector3f GetLocation() {
		return this.location;
	}
	
	public Vector3f GetWorldLocation() {
		return new Vector3f(this.location.x * CHUNK_SIZE, this.location.y * CHUNK_HEIGHT, this.location.z * CHUNK_SIZE);
	}
	
	public String GetKey() {
		int x = (int)this.location.x;
		int y = (int)this.location.y;
		int z = (int)this.location.z;
		return x + "-" + y + "-" + z;
	}
	
	public void SetBlockId(byte id, int x, int y, int z) {
		if(!this.IsInsideChunk(x, y, z))
			return;
		this.blocks[x][y][z] = id;
	}
	
	private boolean IsInsideChunk(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < CHUNK_SIZE && y < CHUNK_HEIGHT && z < CHUNK_SIZE;
	}
}
