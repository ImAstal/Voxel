package resources;

public class Texture {
	
	private int id, width, height, subWidth, subHeight;

	public Texture(int id, int width, int height) {
		this(id, width, height, 0, 0);
	}
	
	public Texture(int id, int width, int height, int subWidth, int subHeight) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.subWidth = subWidth;
		this.subHeight = subHeight;
	}
	
	public int GetId() {
		return this.id;
	}
	
	public int GetWidth() {
		return this.width;
	}
	
	public int GetHeight() {
		return this.height;
	}
	
	public int GetSubWidth() {
		return this.subWidth;
	}
	
	public int GetSubHeight() {
		return this.subHeight;
	}
}
