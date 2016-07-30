package scene;

import rendering.MainRenderer;

public abstract class Scene {
	
	protected MainRenderer renderer;
	
	public Scene(MainRenderer renderer) {
		this.renderer = renderer;
	}
	
	public abstract void Update(float delta);

}
