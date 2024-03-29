package com.gravity.golf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gravity.golf.Util.Constants;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

public class PlayerEntity extends Actor {
    private Image texture;
    private World world;
    public Body body;
    private Fixture fixture;
    private LauncherEntity launcher;
    private Stage stage;

    private boolean alive = false, explotando = false, agarrado = false;

    float touchX, touchY;

    public void setAlive(boolean novo){
        alive = novo;
    }
    public boolean isAlive(){
        return alive;
    }
    public boolean isExplotando() { return explotando; }
    public void setExplosion(){ explotando = true;}

    public PlayerEntity(Stage s, World world, Texture texture, LauncherEntity launcher){
        this.stage = s;
        this.world = world;
        this.texture = new Image(texture);
        this.launcher = launcher;

        BodyDef def = new BodyDef();
        def.position.set(launcher.pos.x,launcher.pos.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        CircleShape jshape = new CircleShape();
        jshape.setRadius(0.25f);
        fixture = body.createFixture(jshape,1);
        fixture.setUserData("player");
        jshape.dispose();

        setSize(Constants.PIXEL_IN_METER, Constants.PIXEL_IN_METER);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.25f)* Constants.PIXEL_IN_METER, (body.getPosition().y - 0.25f) * Constants.PIXEL_IN_METER);

        if(alive == true) {
            ////////////////////////////////////////////   GIRANAVES EN FUNCION DE LA VELOCIDAD  ///////////////////////////////////
            texture.setColor(batch.getColor());
            texture.setSize(getWidth(), getHeight());
            texture.setPosition(getX()-0.25f, getY()-0.25f);
            texture.setOriginX(getWidth()/2);
            texture.setOriginY(getHeight()/2);

            float vx = body.getLinearVelocity().x;
            float vy = body.getLinearVelocity().y;

            if (vx > 0) {
                texture.setRotation((float) (atan(vy / vx) * 180 / 3.141592f) + 150);
            } else if (vx < 0) {
                texture.setRotation((float) (atan(vy / vx) * 180 / 3.141592f) + 180 + 150);
            } else if (vy > 0) {
                texture.setRotation(90 + 150);
            } else {
                texture.setRotation(-90 + 150);
            }
            texture.draw(batch, parentAlpha);
        }else if ( explotando == false){
            texture.setColor(batch.getColor());
            texture.setSize(getWidth(), getHeight());
            texture.setPosition(getX(), getY());
            texture.setOriginX(getWidth() / 2);
            texture.setOriginY(getHeight() / 2);
            texture.setRotation((float) atan( (launcher.pos.x-getX()) / (launcher.pos.y-getY()) ));
            texture.draw(batch, parentAlpha);
        }
    }


    @Override
    public void act(float delta) {
        if(!alive){
            cogido();
            if(agarrado){
                float sq = abs(Vector2.dst(Gdx.input.getX() - touchX,touchY - Gdx.input.getY(),launcher.posX(),launcher.posY()));
                if(sq > 128){                                                              /// LIMITACION DEL TIRACHINAS
                    body.setTransform(launcher.posX() + (Gdx.input.getX() - touchX)*128 / (sq*Constants.PIXEL_IN_METER), launcher.posY() + (touchY - Gdx.input.getY())*128 / (sq*Constants.PIXEL_IN_METER), 0);
                }else {
                    body.setTransform(launcher.posX() + (Gdx.input.getX() - touchX) / Constants.PIXEL_IN_METER, launcher.posY() + (touchY - Gdx.input.getY()) / Constants.PIXEL_IN_METER, 0);
                }
            }else{

            }
        }else{
            /////////    ACTIVACION DE COMPONENTES   O    PAUSE     /////////////
        }
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

    private void cogido(){    ////////////////////////////////////////////////   ALGO RARO PASA SI CAMBIAS DE RESOLUCION
        if(Gdx.input.isTouched()){
            if(Gdx.input.getX()*1.0/Gdx.graphics.getWidth()*1.0<0.52f  &&  Gdx.input.getX()*1.0/Gdx.graphics.getWidth()*1.0>0.48f &&
               Gdx.input.getY()*1.0/Gdx.graphics.getHeight()*1.0<0.51f  &&  Gdx.input.getY()*1.0/Gdx.graphics.getHeight()*1.0 >0.47f){  // DESFASE POR EL OUTPUT, EL 50% ESTA EN LA BASE DE LA NAVE
                agarrado = true;
                touchX = Gdx.input.getX();
                touchY = Gdx.input.getY();
            }else if (!agarrado){
                if(Gdx.input.justTouched()){    ///  SE CALCULA EL OFFSET CUANDO PINCHAS FUERA DE LA NAVE
                    touchX = Gdx.input.getX();
                    touchY = Gdx.input.getY();
                }                               ///  SE MUEVE LA CAMARA A LA POSICION TOCADA MENOS EL OFFSET
                stage.getCamera().translate((touchX - Gdx.input.getX())*0.2f, (Gdx.input.getY() - touchY)*0.2f,0);
            }
        }else{
            if(agarrado){
                alive = true;
                jump((launcher.posX() - body.getPosition().x)*5, (launcher.posY() - body.getPosition().y)*5);
                agarrado = false;
            }else{

            }

        }
    }
    public  float getVelocityX(){
        return body.getLinearVelocity().x;
    }
    public  float getVelocityY() { return body.getLinearVelocity().y; }
    public void applyGravity(Vector2 g){
        body.applyForceToCenter(g,true);
    }
}