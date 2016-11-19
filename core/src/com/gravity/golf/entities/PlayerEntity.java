package com.gravity.golf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gravity.golf.Constants;

public class PlayerEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private Sound dieSound, jumpSound;
    private boolean alive = true;

    public void setAlive(boolean novo){
        if(novo == false){
            dieSound.play();
        }
        alive = novo;
    }
    public boolean isAlive(){
        return alive;
    }

    public PlayerEntity(World world, Texture texture, Sound jumpSound, Sound dieSound,float width, float height, Vector2 pos){
        this.world = world;
        this.texture = texture;
        this.jumpSound = jumpSound;
        this.dieSound = dieSound;

        BodyDef def = new BodyDef();
        def.position.set(pos);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape jshape = new PolygonShape();
        jshape.setAsBox(width/2,height/2);
        fixture = body.createFixture(jshape,1);
        fixture.setUserData("player");
        jshape.dispose();

        setSize(width * Constants.PIXEL_IN_METER, height * Constants.PIXEL_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f)* Constants.PIXEL_IN_METER, (body.getPosition().y -0.5f) * Constants.PIXEL_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        if(alive) {
            body.setLinearVelocity(Constants.PLAYER_SPEED,body.getLinearVelocity().y);
        }
    }


    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public  float getVelocityX(){
        return body.getLinearVelocity().x;
    }
    public  float getVelocityY() { return body.getLinearVelocity().y; }
}