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

        // Render 3D world
        batch.begin();
        render3DWorld(batch);
        hud.render(batch);
        batch.end();

        // Render minimap
        renderMinimap();
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
        int minimapX = 10; // X position of the minimap (left corner)
        int tileSize = 10; // Size of each tile in the minimap
        int minimapHeight = cenario.getHeight() * tileSize; // Total height of the minimap
        int minimapY = Gdx.graphics.getHeight() - minimapHeight - 10; // Y position of the minimap (upper-left corner)
    
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
    }
}