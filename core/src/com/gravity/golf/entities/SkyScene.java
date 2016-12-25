package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class SkyScene extends Actor{
    private PlayerEntity player;
    private Texture texture;
    private float x, y, width, height;

    public SkyScene(PlayerEntity player, Texture texture, float x, float y, float width, float height){
        this.player = player;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,x + player.getX()*256/width ,y + player.getY()*256/height,width,height);
    }

    @Override
    public void act(float delta) {

    }


    public void detach(){

    }
}
