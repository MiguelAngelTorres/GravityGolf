package com.gravity.golf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gravity.golf.entities.EntityFactory;
import com.gravity.golf.entities.LauncherEntity;
import com.gravity.golf.entities.PlanetaEntity;
import com.gravity.golf.entities.PlayerEntity;
import com.gravity.golf.entities.SkyScene;

import java.util.ArrayList;
import java.util.List;

import static com.gravity.golf.Util.Constants.GAME_HEIGHT;
import static com.gravity.golf.Util.Constants.GAME_WIDTH;
import static java.lang.Math.pow;


public class GameScreen extends PantallaBase {

    private Stage stage;
    private World world;
    private ArrayList<Texture> fondos = new ArrayList<Texture>();

    private Sound dieSound;
    private Music gameMusic;
    private Vector3 position;
    public PlayerEntity player;
    private List<PlanetaEntity> planetaList;
    private List<SkyScene> skyList;
    private LauncherEntity launcher;


    public GameScreen(final MainGame game) {
        super(game);
        dieSound = game.getManager().get("sound/die.ogg");

        stage = new Stage(new FitViewport(GAME_WIDTH, GAME_HEIGHT));
        world = new World(new Vector2(0, 0), true);

        position = new Vector3(stage.getCamera().position);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB) {
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) || (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact, "player", "planet")) {
                    if (player.isAlive()) {
                        dieSound.play();
                        player.setAlive(false);
                        player.setExplosion();
                        stage.addAction(Actions.sequence(
                                Actions.delay(1f),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        game.setScreen(game.gameoverscreen);
                                        dispose();
                                    }
                                })
                        ));
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        EntityFactory factor = new EntityFactory(game.getManager(),2);
        skyList = factor.createSky();
        for (SkyScene s : skyList){
            stage.addActor(s);
        }
        player = factor.createPlayer(world);
        launcher = factor.createLauncher();
        stage.addActor(launcher);
        stage.addActor(player);  /// LE CAMBIO EL ORDEN PARA QUE SE PINTEN BIEN

        planetaList = factor.createPlanets(world);
        for (PlanetaEntity p : planetaList){
            stage.addActor(p);
        }

        ((OrthographicCamera)stage.getCamera()).zoom = 2.5f;

//        gameMusic.setVolume(0.60f);
//        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        //cuando se cambie de tamaño se redimensionara el juego
        Gdx.gl.glViewport( 0, 0, width, height);
    }

    @Override
    public void hide() {
//        gameMusic.stop();
        player.detach();
        player.remove();
        for (PlanetaEntity p : planetaList){
            p.detach();
        }
        planetaList.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta,6,2);

        /////////////////////////////    CÁMARA SIGUE AL JUGADOR Y LE HACE ZOOM SI HA SIDO LANZADO      ///////////////////////
        if(player.isAlive()) {
            ((OrthographicCamera)stage.getCamera()).position.x = player.getX();
            ((OrthographicCamera)stage.getCamera()).position.y = player.getY();
            ((OrthographicCamera)stage.getCamera()).zoom -= (float) ((((OrthographicCamera)stage.getCamera()).zoom - 2) * 0.01f);


            //////////////////////////////////////////////    MOTOR     DE     GRAVEDAD     //////////////////////////////////////
            Vector2 aux = new Vector2(0,0);
            for(PlanetaEntity planet : planetaList){
                float dx = planet.body.getPosition().x - player.body.getPosition().x;
                float dy = planet.body.getPosition().y - player.body.getPosition().y;
                float dcubo = (float) (1/pow(dx*dx + dy*dy,1.5f));
                aux.x += planet.getMass()*(dx) * dcubo;
                aux.y += planet.getMass()*(dy) * dcubo;
                System.out.println(aux.toString());
            }
            player.applyGravity(aux);
        }else if (!player.isExplotando()){   /////  EL JUGADOR ESTA EN BASE  /////
            ((OrthographicCamera)stage.getCamera()).position.x = player.getX();
            ((OrthographicCamera)stage.getCamera()).position.y = player.getY();
            ((OrthographicCamera)stage.getCamera()).zoom = 2.5f;

        }else{
            /////////////  LA CAMARA SE MUEVE HACIA EL LAUNCHER   ////////////////
            ((OrthographicCamera)stage.getCamera()).position.x += (float) ((launcher.pos.x - ((OrthographicCamera)stage.getCamera()).position.x) * 0.01f);
            ((OrthographicCamera)stage.getCamera()).position.y += (float) ((launcher.pos.y - ((OrthographicCamera)stage.getCamera()).position.y) * 0.01f);
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();

    }
}
