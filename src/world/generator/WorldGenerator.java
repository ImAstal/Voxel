package world.generator;

import world.Chunk;

import static game.Constants.*;

public class WorldGenerator {
	
	private static final int SEED = 1234585;
	private static SimplexNoise noise = new SimplexNoise(SEED);
	private static SimplexNoise noise2 = new SimplexNoise(SEED + 1);
	private static SimplexNoise noise3 = new SimplexNoise(SEED + 2);
	private static float SCALE = 128f;

	public static Chunk GenerateChunk(Chunk chunk) {
		int cx = (int) chunk.GetLocation().x;
		int cz = (int) chunk.GetLocation().z;
		for(int x = 0; x < CHUNK_SIZE; x++) {
			for(int z = 0; z < CHUNK_SIZE; z++) {
				float rx = x + cx * CHUNK_SIZE;
				float rz = z + cz * CHUNK_SIZE;
				for(int y = 0; y < WORLD_HEIGHT; y++) {
					chunk.SetBlockId(GetBlock(rx, y, rz), x, y, z);
				}
			}
		}
		
		for(int x = 0; x < CHUNK_SIZE; x++) {
			float rx = x + cx * CHUNK_SIZE;
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				chunk.backBorder[x][y] = GetBlock(rx, y, cz * CHUNK_SIZE - 1);
			}
		}
		
		for(int x = 0; x < CHUNK_SIZE; x++) {
			float rx = x + cx * CHUNK_SIZE;
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				chunk.frontBorder[x][y] = GetBlock(rx, y, cz * CHUNK_SIZE + CHUNK_SIZE);
			}
		}
		
		for(int z = 0; z < CHUNK_SIZE; z++) {
			float rz = z + cz * CHUNK_SIZE;
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				chunk.leftBorder[z][y] = GetBlock(cx * CHUNK_SIZE - 1, y, rz);
			}
		}
		
		for(int z = 0; z < CHUNK_SIZE; z++) {
			float rz = z + cz * CHUNK_SIZE;
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				chunk.rightBorder[z][y] = GetBlock(cx * CHUNK_SIZE + CHUNK_SIZE, y, rz);
			}
		}
		
		chunk.SetGenerated(true);
		return chunk;
	}
	
	private static int GetHeight(float rx, float rz) {
		return (int)(60 + noise.noise(rx / SCALE, rz / SCALE) * 5f + noise2.noise(rx / SCALE, rz / SCALE) * 7f + noise3.noise(rx / SCALE / 9f, rz / SCALE /9f) * 50f);
	}
	
	private static byte GetBlock(float rx, float ry, float rz) {
		if(ry < GetHeight(rx, rz))
			return 1;
		else
			return 0;
	}
}
