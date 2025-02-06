package io.rba.raymob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Characters {
    private float x, y; // NPC position
    private Texture texture; // NPC texture
    private Player player; // Reference to the player
    private float distance; // Distance to the player
    private boolean visible; // Whether the NPC is visible
    private float[] zBuffer; // Depth buffer for occlusion

    // Constants
    private static final float FOV = MathUtils.degreesToRadians * 60; // Field of view in radians
    private static final float HALF_FOV = FOV / 2; // Half of the field of view
    private static final float PROJECTION_PLANE_DISTANCE = 300 / (float) Math.tan(HALF_FOV); // Distance to the projection plane

    public Characters(float x, float y, Texture texture, Player player, float[] zBuffer) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.player = player;
        this.zBuffer = zBuffer;
        this.visible = false;
    }

    /**
     * Calculate the angle between the NPC and the player.
     */
    private void calcAngle() {
        float vectX = (float) (this.x - player.getX());
        float vectY = (float) (this.y - player.getY());
        float anglePlayerObject = (float) Math.atan2(vectY, vectX);
        float diffAngle = (float) (player.getTurnAngle() - anglePlayerObject);

        // Normalize diffAngle to be within -PI and PI
        if (diffAngle < -MathUtils.PI) {
            diffAngle += 2 * MathUtils.PI;
        }
        if (diffAngle > MathUtils.PI) {
            diffAngle -= 2 * MathUtils.PI;
        }

        // Check if the NPC is within the player's field of view
        this.visible = Math.abs(diffAngle) < HALF_FOV;

        // Debug log
        Gdx.app.log("NPC Visibility", "NPC at (" + x + ", " + y + "): " + (visible ? "Visible" : "Not Visible"));
    }

    /**
     * Calculate the distance between the NPC and the player.
     */
    private void calcDistance() {
        float dx = (float) (this.x - player.getX());
        float dy = (float) (this.y - player.getY());
        this.distance = (float) Math.hypot(dx, dy);
    }

    /**
     * Update the NPC's angle and distance.
     */
    public void updateData() {
        calcAngle();
        calcDistance();
    }

    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Draw the NPC sprite on the screen.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void drawSprite(SpriteBatch batch) {
        updateData();

        if (this.visible) {
            float tileHeight = 500; // Base height of the sprite
            float spriteHeight = tileHeight / this.distance * PROJECTION_PLANE_DISTANCE;

            // Calculate where to draw the sprite
            float y0 = (Gdx.graphics.getHeight() / 2) - (spriteHeight / 10);
            float y1 = y0 + (spriteHeight / 10);

            // Calculate the sprite's horizontal position
            float dx = (float) (this.x - player.getX());
            float dy = (float) (this.y - player.getY());
            float spriteAngle = (float) ((float) Math.atan2(dy, dx) - player.getTurnAngle());

            // Map the sprite angle to the screen columns
            float screenX = (float) (Math.tan(spriteAngle) * PROJECTION_PLANE_DISTANCE);
            float x1 = (Gdx.graphics.getWidth() / 2) + screenX - (texture.getWidth() / 2);

            // Draw the sprite
            batch.draw(
                texture,
                x1, // X position
                y1, // Y position
                texture.getWidth(), // Width
                texture.getHeight() // Height
            );
        }
    }

    // Getters and setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }
}