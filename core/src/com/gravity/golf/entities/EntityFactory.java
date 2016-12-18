package com.gravity.golf.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class EntityFactory extends Actor {
    private int level;
    private AssetManager manager;
    public PlayerEntity player;
    private LauncherEntity launcher;
    private List<PlanetaEntity> planetaList = new ArrayList<PlanetaEntity>();
    private List<SkyScene> skyList = new ArrayList<SkyScene>();

    public EntityFactory(AssetManager manager, int level) {
        this.manager = manager;
        this.level = level;
    }



    public PlayerEntity createPlayer(World world) {
        //////////////////////////////////////////    LOAD   IMAGES    ////////////////////////////////////////////
        Texture playerTexture = manager.get("img/nave.png");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////    LOAD    PLAYER    ///////////////////////////////////////////
        if(level == 1) {
            player = new PlayerEntity(world, playerTexture, new Vector2(6,0), new Vector2(0,5));
        }else if(level == 2){
            player = new PlayerEntity(world, playerTexture, new Vector2(0,-6), new Vector2(-6,4));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        return player;
    }

    public LauncherEntity createLauncher(){
        //////////////////////////////////////////    LOAD   IMAGES    ////////////////////////////////////////////
        Texture launcherbaseTexture = manager.get("img/plataforma.png");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(level == 1) {
            launcher = new LauncherEntity(launcherbaseTexture, new Vector2(6,0));
        }else if(level == 2){
            launcher = new LauncherEntity(launcherbaseTexture, new Vector2(0,-6));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        return launcher;
    }


    public List<PlanetaEntity> createPlanets(World world) {
        //////////////////////////////////////////    LOAD   IMAGES    ////////////////////////////////////////////
        Texture planetaTexture = manager.get("img/tierra.png");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////    LOAD    PLANETS    ///////////////////////////////////////////
        if(level == 1) {
            planetaList.add(new PlanetaEntity(world, planetaTexture, new Vector2(0,0), 2));
        }else if(level == 2){
            planetaList.add(new PlanetaEntity(world, planetaTexture, new Vector2(0,0), 2));
            planetaList.add(new PlanetaEntity(world, planetaTexture, new Vector2(0,15), 2));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        return planetaList;
    }



    public List<SkyScene> createSky() {
        //////////////////////////////////////////    LOAD   IMAGES    ////////////////////////////////////////////
        Texture texture1 = manager.get("img/Estrella_1.png");
        Texture texture2 = manager.get("img/Estrella_2.png");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////     LOAD    SKY     ///////////////////////////////////////////
        skyList.add(new SkyScene(texture1,-2000,-2000,4000,4000));
        skyList.add(new SkyScene(texture2,-1600,-1600,3200,3200));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        return skyList;
    }


}



