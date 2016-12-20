package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gravity.golf.Util.Constants;

import sun.misc.Launcher;

import static java.lang.Math.atan;

public class LauncherEntity extends Actor {
    private Texture launcher_base;
    public Vector2 pos;

    public LauncherEntity(Texture texture, Vector2 posicion){
        this.launcher_base = texture;
        pos = posicion;
        setSize(Constants.PIXEL_IN_METER, Constants.PIXEL_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((pos.x - 0.5f)* Constants.PIXEL_IN_METER, (pos.y - 0.5f) * Constants.PIXEL_IN_METER);
        batch.draw(launcher_base, getX(), getY(), getWidth() , getHeight() );
    }

    @Override
    public void act(float delta) {

    }


    public void detach(){

    }
}
