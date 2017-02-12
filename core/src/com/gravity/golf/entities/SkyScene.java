package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class SkyScene extends Actor{
    private PlayerEntity player;
    private Texture texture;
    private float x, y, width, height, offx, offy;

    public SkyScene(PlayerEntity player, Texture texture, float x, float y, float width, float height){
        this.player = player;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.offx = 0;
        this.offy = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (player.isAlive()) {
            offx = player.getX() * 1024 / width;
            offy = player.getY() * 1024 / height;
        }
        batch.draw(texture, x + offx, y + offy, width, height);
    }
    @Override
    public void act(float delta) {

    }


    public void detach(){

    }
}
