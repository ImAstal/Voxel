package scene;

import rendering.MainRenderer;
import world.World;

public class SceneGame extends Scene {
	
	private World world;

	public SceneGame(MainRenderer renderer) {
		super(renderer);
		this.world = new World(renderer);
	}

	public void Update(float delta) {
		this.world.Update(delta);
	}

}
