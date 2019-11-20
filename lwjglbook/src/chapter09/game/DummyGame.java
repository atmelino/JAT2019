package chapter09.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import chapter09.engine.GameItem;
import chapter09.engine.IGameLogic;
import chapter09.engine.MouseInput;
import chapter09.engine.Window;
import chapter09.engine.graph.Camera;
import chapter09.engine.graph.Mesh;
import chapter09.engine.graph.OBJLoader;
import chapter09.engine.graph.Texture;

public class DummyGame implements IGameLogic {

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private final Vector3f cameraInc;

	private final Renderer renderer;

	private final Camera camera;

	private GameItem[] gameItems;

	private static final float CAMERA_POS_STEP = 0.05f;

	public boolean bunny;

	public DummyGame(boolean bunny) {
		this.bunny = bunny;
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0, 0, 0);
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		if (bunny) {

			Mesh mesh = OBJLoader.loadMesh("../resources/models/bunny.obj");
			GameItem gameItem = new GameItem(mesh);
			gameItem.setScale(0.5f);
			gameItem.setPosition(0, 0, -2);
			gameItems = new GameItem[] { gameItem };

		} else {
			Mesh mesh = OBJLoader.loadMesh("../resources/models/cube.obj");
			Texture texture = new Texture("bin/chapter09/textures/grassblock.png");
			mesh.setTexture(texture);
			GameItem gameItem = new GameItem(mesh);
			gameItem.setScale(0.5f);
			gameItem.setPosition(0, 0, -2);
			gameItems = new GameItem[] { gameItem };

		}
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.z = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_Z)) {
			cameraInc.y = -1;
		} else if (window.isKeyPressed(GLFW_KEY_X)) {
			cameraInc.y = 1;
		}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);

		// Update camera based on mouse
		if (mouseInput.isRightButtonPressed()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		}
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItems);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (GameItem gameItem : gameItems) {
			gameItem.getMesh().cleanUp();
		}
	}

}
