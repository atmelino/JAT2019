package chapter06p2.game;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import chapter06p2.engine.GameItem;
import chapter06p2.engine.Utils;
import chapter06p2.engine.Window;
import chapter06p2.engine.graph.ShaderProgram;
import chapter06p2.engine.graph.Transformation;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;

    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("../resources/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("../resources/fragment.fs"));
        shaderProgram.link();
        
        // Create uniforms for world and projection matrices
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        
        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, GameItem[] gameItems) {
        clear();

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        
        // Render each gameItem
        for(GameItem gameItem : gameItems) {
            // Set world matrix for this item
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                    gameItem.getPosition(),
                    gameItem.getRotation(),
                    gameItem.getScale());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            // Render the mes for this game item
            gameItem.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
