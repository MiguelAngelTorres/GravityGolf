package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gravity.golf.Util.Constants;

public class Explosion extends Actor {
    private Texture boom;
    public Vector2 pos;
    private float lambda_convergence = 0.5f;

    public Explosion(Texture texture, Vector2 posicion){
        this.boom = texture;
        pos = posicion;
        setSize(2*Constants.PIXEL_IN_METER, 2*Constants.PIXEL_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(pos.x, pos.y - 0.5f*Constants.PIXEL_IN_METER*lambda_convergence);
        batch.draw(boom, getX() - Constants.PIXEL_IN_METER*lambda_convergence, getY(), getWidth()*lambda_convergence,getHeight()*lambda_convergence);
    }

    @Override
    public void act(float delta) {
        lambda_convergence += (1.5f - lambda_convergence)*0.05f; /// MAKE THE EXPLOSION SOFT-INCREASING
    }

    public void detach(){

    }
}
