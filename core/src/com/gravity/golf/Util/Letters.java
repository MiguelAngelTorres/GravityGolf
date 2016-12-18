package com.gravity.golf.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Letters {
    private Texture letras;
    private TextureRegion letra[];
    private Pair<Integer, Integer> pos_letras[];

    public Letters(){
        letras = new Texture("img/letras.png");
        letra = new TextureRegion[27];
        pos_letras = new Pair[27];
        int alto = letras.getHeight();
        pos_letras[0] = new Pair<Integer, Integer>(0,43);
        pos_letras[1] = new Pair<Integer, Integer>(43,85);
        pos_letras[2] = new Pair<Integer, Integer>(85,123);
        pos_letras[3] = new Pair<Integer, Integer>(123,164);
        pos_letras[4] = new Pair<Integer, Integer>(164,202);
        pos_letras[5] = new Pair<Integer, Integer>(202,238);
        pos_letras[6] = new Pair<Integer, Integer>(238,276);
        pos_letras[7] = new Pair<Integer, Integer>(276,325);
        pos_letras[8] = new Pair<Integer, Integer>(325,350);
        pos_letras[9] = new Pair<Integer, Integer>(350,385);
        pos_letras[10] = new Pair<Integer, Integer>(385,432);
        pos_letras[11] = new Pair<Integer, Integer>(432,467);
        pos_letras[12] = new Pair<Integer, Integer>(467,516);
        pos_letras[13] = new Pair<Integer, Integer>(516,559);
        pos_letras[14] = new Pair<Integer, Integer>(559,602);
        pos_letras[15] = new Pair<Integer, Integer>(602,644);
        pos_letras[16] = new Pair<Integer, Integer>(644,683);
        pos_letras[17] = new Pair<Integer, Integer>(683,725);
        pos_letras[18] = new Pair<Integer, Integer>(725,770);
        pos_letras[19] = new Pair<Integer, Integer>(770,805);
        pos_letras[20] = new Pair<Integer, Integer>(805,844);
        pos_letras[21] = new Pair<Integer, Integer>(844,886);
        pos_letras[22] = new Pair<Integer, Integer>(886,926);
        pos_letras[23] = new Pair<Integer, Integer>(926,978);
        pos_letras[24] = new Pair<Integer, Integer>(978,1017);
        pos_letras[25] = new Pair<Integer, Integer>(1017,1057);
        pos_letras[26] = new Pair<Integer, Integer>(1057,1094);

        for(int i = 0; i < 27; i++){
            int ancho = pos_letras[i].getSecond()-pos_letras[i].getFirst();
            letra[i] = new TextureRegion(letras, pos_letras[i].getFirst(), 0, ancho, alto);
        }
    }

    public float anchoCadena(String cadena, float sizeY){
        float ancho = 0;
        for(int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) != ' ') {
                int pos = cadena.charAt(i) - 'a';
                float ancho_letra = 1.5f*(pos_letras[pos].getSecond()-pos_letras[pos].getFirst())*1f*sizeY/letras.getHeight();

                if (cadena.charAt(i) > 'n' && cadena.charAt(i) != 'ñ')
                    pos = pos + 1;
                else if (cadena.charAt(i) == 'ñ')
                    pos = 14;
                ancho+= ancho_letra*1f;
            }
            else
                ancho+=sizeY/2f;
        }
        return ancho;
    }

    public void writeCadena(Batch batch, String cadena, float x, float y, float sizeY){
        float pos_escritura = x;
        for(int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) != ' ') {
                int pos = cadena.charAt(i) - 'a';
                float ancho_letra = 1.5f*(pos_letras[pos].getSecond()-pos_letras[pos].getFirst())*1f*sizeY/letras.getHeight();

                if (cadena.charAt(i) > 'n' && cadena.charAt(i) != 'ñ')
                    pos = pos + 1;
                else if (cadena.charAt(i) == 'ñ')
                    pos = 14;
                batch.draw(letra[pos], pos_escritura, y, ancho_letra, sizeY);
                pos_escritura+= ancho_letra;
            }
            else
                pos_escritura+=sizeY/2f;
        }
    }
}
