package io.rba.raymob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; // For the minimap
    private Texture wallTexture;
    private Cenario cenario;
    private Player player;
    private Hud hud;
    private List<Ray> rays;
    private List<Characters> npcs; // List of NPCs
    private float[] zBuffer; // Depth buffer for occlusion

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer(); // Initialize ShapeRenderer
        wallTexture = new Texture("wallpaint.png");
        cenario = new Cenario();
        player = new Player(150, 150, cenario);
        hud = new Hud(player);

        // Initialize rays
        rays = new ArrayList<>();
        double playerAngle = Math.toRadians(player.getTurnAngle());
        double fov = Math.toRadians(60); // Field of view in radians
        int numColumns = 60; // Number of columns to render

        for (int i = 0; i < numColumns; i++) {
            double rayAngle = playerAngle - fov / 2 + (fov / numColumns) * i;
            rays.add(new Ray(cenario, player, (float) rayAngle, wallTexture));
        }

        // Initialize NPCs
        npcs = new ArrayList<>();
        zBuffer = new float[numColumns]; // Depth buffer for occlusion
        Texture npcTexture1 = new Texture("mechass-resised.png"); // Load NPC texture 1
        Texture npcTexture2 = new Texture("officer-resised.png"); // Load NPC texture 2
        Texture npcTexture3 = new Texture("mechass-resised.png"); // Load NPC texture 3

        // Add NPCs at different positions
        npcs.add(new Characters(200, 100, npcTexture1, player));
        npcs.add(new Characters(300, 30, npcTexture2, player));
        npcs.add(new Characters(400, 40, npcTexture3, player));
        npcs.add(new Characters(250, 350, npcTexture3, player));
        npcs.add(new Characters(100, 400, npcTexture3, player));

        // Configure touch events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                hud.handleInput(screenX, screenY, false);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                hud.handleInput(screenX, screenY, true);
                return true;
            }
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    
        // Update the depth buffer (zBuffer) with raycasting distances
        for (int i = 0; i < rays.size(); i++) {
            double[] wallHit = rays.get(i).cast();
            zBuffer[i] = (float) Math.hypot(wallHit[0] - player.getX(), wallHit[1] - player.getY());
        }
    
        // Render 3D world
        batch.begin();
        render3DWorld(batch);
        hud.render(batch);
    
        // Render NPCs
        for (Characters npc : npcs) {
            npc.drawSprite(batch);
        }
    
        batch.end();
    
        // Render minimap
        renderMinimap();
        player.updateMovement();
    }

    private void render3DWorld(SpriteBatch batch) {
        double playerAngle = player.getTurnAngle();
        double fov = Math.toRadians(60); // Field of view in radians
        int numColumns = 60; // Number of columns to render

        for (int i = 0; i < numColumns; i++) {
            double rayAngle = playerAngle - fov / 2 + (fov / numColumns) * i;
            rays.get(i).setRayAngle((float) rayAngle); // Update the ray angle
            rays.get(i).wallRender3d(batch, i); // Render the ray
        }
    }

    private void renderMinimap() {
        // Minimap settings
        int minimapX = 10; // X position of the minimap (bottom-left corner)
        int minimapY = 10; // Y position of the minimap (bottom-left corner)
        int tileSize = 10; // Size of each tile in the minimap

        // Set up ShapeRenderer for the minimap
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw the map
        for (int y = 0; y < cenario.getHeight(); y++) {
            for (int x = 0; x < cenario.getWidth(); x++) {
                if (cenario.getMatriz()[y][x] == 1) {
                    shapeRenderer.setColor(Color.GRAY);
                    shapeRenderer.rect(
                        minimapX + x * tileSize, // X position
                        minimapY + y * tileSize, // Y position
                        tileSize, // Width
                        tileSize // Height
                    );
                }
            }
        }

        // Draw the player
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(
            minimapX + (float) player.getX() / cenario.getTileSize() * tileSize, // Scaled X position
            minimapY + (float) player.getY() / cenario.getTileSize() * tileSize, // Scaled Y position
            5f // Player radius
        );

        // Draw the rays
        shapeRenderer.setColor(Color.RED);
        for (Ray ray : rays) {
            double[] endPoint = ray.cast(); // Get the intersection point
            shapeRenderer.line(
                minimapX + (float) player.getX() / cenario.getTileSize() * tileSize, // Start at player position
                minimapY + (float) player.getY() / cenario.getTileSize() * tileSize, // Start at player position
                minimapX + (float) endPoint[0] / cenario.getTileSize() * tileSize, // End at intersection point
                minimapY + (float) endPoint[1] / cenario.getTileSize() * tileSize // End at intersection point
            );
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        wallTexture.dispose();
        for (Characters npc : npcs) {
            npc.getTexture().dispose(); // Dispose NPC textures
        }
    }
}