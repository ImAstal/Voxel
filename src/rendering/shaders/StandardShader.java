package rendering.shaders;

import org.lwjgl.util.vector.Matrix4f;

import rendering.Light;

public class StandardShader extends ShaderProgram {
	
	private int location_ProjectionMatrix;
	private int location_ViewMatrix;
	private int location_TransformationMatrix;
	private int location_LightDirection;
	private int location_LightColor;

	public StandardShader() {
		super("standardVertex.txt", "standardFragment.txt");
	}

	protected void BindAllAttributes() {
		super.BindAttribute(0, "position");
		super.BindAttribute(1, "normal");
		super.BindAttribute(2, "texture");
	}

	protected void GetAllUniformLocations() {
		this.location_ProjectionMatrix = super.GetUniformLocation("projectionMatrix");
		this.location_ViewMatrix = super.GetUniformLocation("viewMatrix");
		this.location_TransformationMatrix = super.GetUniformLocation("transformationMatrix");
		this.location_LightDirection = super.GetUniformLocation("lightDirection");
		this.location_LightColor = super.GetUniformLocation("lightColor");
	}
	
	public void LoadProjectionMatrix(Matrix4f matrix) {
		this.Bind();
		super.LoadMatrix(matrix, location_ProjectionMatrix);
		this.Unbind();
	}
	
	public void LoadViewMatrix(Matrix4f matrix) {
		super.LoadMatrix(matrix, location_ViewMatrix);
	}
	
	public void LoadTransformationMatrix(Matrix4f matrix) {
		super.LoadMatrix(matrix, location_TransformationMatrix);
	}
	
	public void LoadLight(Light light) {
		this.Bind();
		super.LoadVector(light.GetDirection(), this.location_LightDirection);
		super.LoadVector(light.GetColor(), this.location_LightColor);
		this.Unbind();
	}

}
