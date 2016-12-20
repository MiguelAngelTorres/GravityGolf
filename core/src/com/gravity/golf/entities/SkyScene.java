package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;


public class SkyScene extends Actor{
    private Texture texture;
    private float x, y, width, height, interval;

    public SkyScene(Texture texture, float x, float y, float width, float height){
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.interval = 0;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,x + (width/10)*sin(interval),y + (height/10)*cos(interval),width,height);
    }

    @Override
    public void act(float delta) {
        interval = (interval + delta/10) % 360;
    }


    public void detach(){

    }
}
