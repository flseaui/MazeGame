package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import entity.Player;
import io.Window;
import render.Camera;
import render.Shader;
import render.ui.Canvas;
import render.ui.Container;
import render.ui.element.UIButton;
import world.Tile;
import world.TileRenderer;
import world.World;

public class Manager {

	ArrayList<Canvas> world_of_wow_swag_$$ = new ArrayList<>();
	
	World world = new World();
	
	Canvas gameCanvas = new Canvas(0);
	
	Camera camera;
	
	TileRenderer tiles = new TileRenderer();
	
	Player player = new Player();
	
	Shader shader = new Shader();
	
	public Manager(Window window) {
		//Add canvi to arraylist
		world_of_wow_swag_$$.add(gameCanvas);
		
		worldInit();
		elementInit();
		
		shader.createFragmentShader("fragment");
		shader.createVertexShader("vertex");
		shader.attach();
		
		camera = new Camera(window.getWidth(), window.getHeight());
	}
	
	public void update(Window window) {
		player.update(window, camera, world);
		
		world.correctCamera(camera, window);
	}
	
	public void input(Window window, double frame_cap) {
		if(window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)){
			//glfwSetWindowShouldClose(win.getWindow(), true);
			System.out.println("TRUE");
		}
		if(window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_1)){ //2 should be changed to the right number
			//glfwSetWindowShouldClose(win.getWindow(), true);
			//double x;
			//double y;
			DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
			DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(window.getWindow(), b1, b2);
			//Tile thisTile = world.getTile((int)b1.get(0), (int)b2.get(0));
			
			world.setGlobalTile(Tile.test2, (int)b1.get(0), (int)b2.get(0), window, camera);
			
			System.out.println("x : " + b1.get(0) + ", y = " + b2.get(0));
		}
		
		player.input(window, world, (float)frame_cap);
	}

	public void render(Window window) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		//System.out.println("BUG: game crashes when many tiles are drawn off screen!");
		
		world.render(tiles, shader, camera, window);
		
		player.render(shader, camera);

		int scale = 32;
		
		Matrix4f world;
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale); //Makes the tiles 32 x 32
		
		int posX = ((int)camera.getPosition().x+(window.getWidth()/2)) / (scale*2);
		int posY = ((int)camera.getPosition().y-(window.getHeight()/2)) / (scale*2);
		
		for(Canvas c : world_of_wow_swag_$$) {
			c.getContainer(0).render(6, -6, shader, world, camera);
		}
		
		window.swapBuffers();
	}
	
	public void elementInit() {
		gameCanvas.addContainer(new Container(0, 0));
		gameCanvas.getContainer(0).addElement(new UIButton("player"));
	}
	
	public void worldInit() {
		for(int a = 0; a < 30;a++){
			world.setTile(Tile.test2, a, 0);
		}
		
		for(int a = 0; a < 30;a++){
			world.setTile(Tile.test2, 0, a);
		}
		
		for(int a = 0; a < 30;a++){
			world.setTile(Tile.test2, 30, a);
		}
		
		for(int a = 0; a < 30;a++){
			world.setTile(Tile.test2, a, 30);
		}
		
		for(int i=6; i < 12; i++){
			for(int j=6; j < 12; j++){
				//ds
				world.setTile(Tile.test2, i, j);
			}
		}
	}
	
}
