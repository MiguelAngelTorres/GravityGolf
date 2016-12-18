package com.gravity.golf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gravity.golf.Util.Constants;

import static java.lang.Math.atan;

public class PlayerEntity extends Actor {
    private Image texture;
    private World world;
    public Body body;
    private Fixture fixture;
    private boolean alive = false, explotando = false;
    private int contador_muerte;

    public void setAlive(boolean novo){
        alive = novo;
    }
    public boolean isAlive(){
        return alive;
    }
    public boolean isExplotando() { return explotando; }
    public void setExplosion(){ explotando = true; }

    public PlayerEntity(World world, Texture texture, Vector2 pos, Vector2 v_init){
        this.world = world;
        this.texture = new Image(texture);

        BodyDef def = new BodyDef();
        def.position.set(pos);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape jshape = new PolygonShape();
        jshape.setAsBox(0.25f, 0.5f);
        fixture = body.createFixture(jshape,1);
        fixture.setUserData("player");
        jshape.dispose();

        setSize(0.5f * Constants.PIXEL_IN_METER, Constants.PIXEL_IN_METER);

        body.setLinearVelocity(v_init);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.25f)* Constants.PIXEL_IN_METER, (body.getPosition().y - 0.5f) * Constants.PIXEL_IN_METER);

        if(alive == true) {
            ////////////////////////////////////////////   GIRANAVES EN FUNCION DE LA VELOCIDAD  ///////////////////////////////////
            texture.setColor(batch.getColor());
            texture.setSize(getWidth(), getHeight());
            texture.setPosition(getX(), getY());
            texture.setOriginX(getWidth() / 2);
            texture.setOriginY(getHeight() / 2);

            float vx = body.getLinearVelocity().x;
            float vy = body.getLinearVelocity().y;

            if (vx > 0) {
                texture.setRotation((float) (atan(vy / vx) * 180 / 3.141592f) + 145);
            } else if (vx < 0) {
                texture.setRotation((float) (atan(vy / vx) * 180 / 3.141592f) + 180 + 145);
            } else if (vy > 0) {
                texture.setRotation(90 + 145);
            } else {
                texture.setRotation(-90 + 145);
            }
            texture.draw(batch, parentAlpha);
        }else if ( explotando = false){

        }else{
            contador_muerte++;
        }
    }

    @Override
    public void act(float delta) {

    }


    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    //// ACIVA EL MOVIMIENTO CUANDO LA NAVE ES LANZADA  ////
    public void jump(float Vx, float Vy){
        alive = true;
        body.setLinearVelocity(Vx,Vy);
    }

    public  float getVelocityX(){
        return body.getLinearVelocity().x;
    }
    public  float getVelocityY() { return body.getLinearVelocity().y; }
    public void applyGravity(Vector2 g){
        body.applyForceToCenter(g,true);
    }
}