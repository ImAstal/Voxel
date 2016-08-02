package rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import utils.Hitbox;

public abstract class Mesh {
	
	private Hitbox hitbox;
	private int vao, verticesVbo, normalsVbo, texturesVbo, colorsVbo, indicesVbo, indicesCount;
	private boolean readyToRender = false, dataUpdated = false;
	private float[] vertices, normals, colors, textures;
	private int[] indices;
	
	private static final int GL_TYPE = GL15.GL_DYNAMIC_DRAW;
	
	public Mesh(Hitbox hitbox) {
		this.hitbox = hitbox;
		this.Initialize();
		this.indicesCount = 0;
	}
	
	private void Initialize() {
		this.vao = GL30.glGenVertexArrays();
		this.verticesVbo = GL15.glGenBuffers();
		this.normalsVbo = GL15.glGenBuffers();
		this.texturesVbo = GL15.glGenBuffers();
		this.colorsVbo = GL15.glGenBuffers();
		this.indicesVbo = GL15.glGenBuffers();
	}
	
	private void Bind() {
		GL30.glBindVertexArray(this.vao);
	}
	
	private void Unbind() {
		GL30.glBindVertexArray(0);
	}
	
	public void SetData(float[] vertices, float[] normals, float[] textures, float[] colors, int[] indices) {
		this.vertices = vertices;
		this.normals = normals;
		this.textures = textures;
		this.colors = colors;
		this.indices = indices;
		this.dataUpdated = true;
	}
	
	public void UpdateData() {
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		verticesBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		verticesBuffer.put(this.vertices);
		verticesBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.verticesVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL_TYPE);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        verticesBuffer.clear();
        this.vertices = null;
		
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(this.normals.length);
		normalsBuffer = BufferUtils.createFloatBuffer(this.normals.length);
		normalsBuffer.put(this.normals);
		normalsBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.normalsVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL_TYPE);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        normalsBuffer.clear();
        this.normals = null;
        
        FloatBuffer texturesBuffer = BufferUtils.createFloatBuffer(this.textures.length);
        texturesBuffer.put(this.textures);
        texturesBuffer.flip();
        this.Bind();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.texturesVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texturesBuffer, GL_TYPE);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        this.textures = null;
        
        FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(this.colors.length);
        colorsBuffer.put(this.colors);
        colorsBuffer.flip();
        this.Bind();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.colorsVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL_TYPE);
        GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        this.colors = null;
        
		
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(this.indices.length);
		indicesBuffer = BufferUtils.createIntBuffer(this.indices.length);
		indicesBuffer.put(this.indices);
		indicesBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_TYPE);		
		this.Unbind();
		this.indicesCount = this.indices.length;
		this.indices = null;
		this.Unbind();
		this.readyToRender = true;
	}
	
	public void Render(Camera camera) {
		this.Bind();
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.indicesCount, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		this.Unbind();
	}
		
	
	public void Cleanup() {
		GL15.glDeleteBuffers(this.verticesVbo);
		GL15.glDeleteBuffers(this.normalsVbo);
		GL15.glDeleteBuffers(this.texturesVbo);
		GL15.glDeleteBuffers(this.colorsVbo);
		GL15.glDeleteBuffers(this.indicesVbo);
		GL30.glDeleteVertexArrays(this.vao);
	}
	
	public int GetVaoId() {
		return this.vao;
	}
	
	public int GetVertexCount() {
		return this.indicesCount;
	}
	
	public Hitbox GetHitbox() {
		return this.hitbox;
	}
	
	public boolean IsReadyToRender() {
		return this.readyToRender;
	}
	
	public boolean DataUpdated() {
		return this.dataUpdated;
	}
	
	public void SetDataUpdated(boolean value) {
		this.dataUpdated = value;
	}
	
	public void SetReadyToRender(boolean value) {
		this.readyToRender = value;
	}

}
