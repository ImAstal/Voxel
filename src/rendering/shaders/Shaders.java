package rendering.shaders;

import utils.Maths;

public class Shaders {
	
	public static StandardShader STANDARD;
	
	public static void Initialize() {
		STANDARD = new StandardShader();
		STANDARD.LoadProjectionMatrix(Maths.CreateProjectionMatrix());
	}
	
	public static void Cleanup() {
		STANDARD.Cleanup();
	}
}
