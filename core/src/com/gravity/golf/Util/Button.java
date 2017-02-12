package com.gravity.golf.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gravity.golf.Util.Letters;

import static com.gravity.golf.Util.Constants.*;


public abstract class Button extends Actor{
    protected Texture boton_sin_pulsar;
    protected Texture boton_pulsado;
    protected Rectangle bordes; // El rectangulo que establece la posición, altura y anchura del botón
    protected String cadena;
    protected float xMinima; // Estos atributos sirven para poner las coordenadas para pulsar el botón.
    protected float yMinima;
    protected float xMaxima;
    protected float yMaxima;
    protected Letters frase = new Letters();
    protected float xLast, yLast;

    protected boolean pulsado = false;

    public Button(int x, int y, int width, int height) {
        boton_sin_pulsar = new Texture("img/plantilla_sin_pulsar.png");
        boton_pulsado = new Texture("img/plantilla_pulsar.png");
        cadena = "";
        bordes = new Rectangle(x, y, width, height);

        xMinima = bordes.x*Gdx.graphics.getWidth()/WIDTH;
        yMaxima = (HEIGHT - bordes.y)*Gdx.graphics.getHeight()/HEIGHT;
        xMaxima = (bordes.x + bordes.width)*Gdx.graphics.getWidth()/WIDTH;
        yMinima = (HEIGHT - (bordes.y + bordes.height))*Gdx.graphics.getHeight()/HEIGHT;
    }

    public void draw(Batch batch) {
        if(!pulsado) {
            float alto = bordes.height/DIV_SIN_PULSAR;
            float ancho = frase.anchoCadena(cadena, alto);
            float pos_x = bordes.x+bordes.width/2f-(ancho)/2f;
            float pos_y = bordes.y+bordes.height/2f-alto/2f;
            batch.draw(boton_sin_pulsar, bordes.x, bordes.y, bordes.width, bordes.height);
            frase.writeCadena(batch, cadena, pos_x, pos_y, alto);
        }
        else {
            float alto = bordes.height/DIV_PULSADO;
            float ancho = frase.anchoCadena(cadena, alto);
            float pos_x = bordes.x+bordes.width/2f-(ancho)/2f;
            float pos_y = bordes.y+bordes.height/2f-alto/2f;
            batch.draw(boton_pulsado, bordes.x, bordes.y, bordes.width, bordes.height);
            frase.writeCadena(batch, cadena, pos_x, pos_y, alto);
        }
    }

    public void update() {
        if(Gdx.input.isTouched()){
            xLast = Gdx.input.getX();
            yLast = Gdx.input.getY();
        }

        if(Gdx.input.justTouched() && sePulsaElBoton())
            pulsado = true;
        else if(pulsado && !sePulsaElBoton()) {
            pulsado = false;
            if(inside(xLast,yLast))
                funcionamiento();
        }
    }

    protected boolean sePulsaElBoton() { // Esta función privada sirve para comprobar si se pulsa el botón.
        return Gdx.input.isTouched() && Gdx.input.getX() >= xMinima && Gdx.input.getX() <= xMaxima && // Devuelve true si se pulsa dentro de los límites
                Gdx.input.getY() >= yMinima && Gdx.input.getY() <= yMaxima;
    }

    private boolean inside(float x, float y) { // Esta función privada sirve para comprobar si se pulsa el botón.
        return x >= xMinima && x <= xMaxima && // Devuelve true si se pulsa dentro de los límites
                y >= yMinima && y <= yMaxima;
    }

    protected abstract void funcionamiento(); // Método que implementarán las clases hijas y contendrá el comportamiento deseado

    // Getters and Setters ------------------------------------------------------------------------

    public Rectangle getBordes() {
        return bordes;
    }

    public void setBordes(Rectangle bordes) {
        this.bordes = bordes;
    }
}
