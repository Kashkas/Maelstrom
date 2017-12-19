/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import actors.Asteroide;
import actors.Bala;
import actors.Nave;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import utils.FileProperties;

/**
 *
 * @author Pablo
 */
public 
class Animacion extends Thread {
    
    int sizeX, sizeY;
    Canvas c;
    Graphics w1, w2;
    Image imagen, img;
    boolean Boo = true;
    static final List<Asteroide> a = new ArrayList<>();
    Nave n;
    static final List<Bala> b = new ArrayList<>();
    int maxBalas;
    int maxAsteroides;
    int sleep;
    int score;
    List<Integer> keys;

    public Animacion(Canvas c, int sizeX, int sizeY, List<Integer> keys) throws Exception{
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.c = c;
        imagen = c.createImage(this.sizeX, this.sizeY);
        score=0;
        w2 = imagen.getGraphics();
        w1 = c.getGraphics();
        n = new Nave(w2, this.sizeX, this.sizeY);
        while (a.size()<5) {
            a.add(new Asteroide((Math.random() * this.sizeX/2), (Math.random() * this.sizeY/2), (Math.random() * 2 - 1), (Math.random() * 2 - 1), w2));
        }
        this.maxAsteroides = Integer.parseInt(FileProperties.getParamProperties("maxAsteroides", "animacion"));
        this.maxBalas = Integer.parseInt(FileProperties.getParamProperties("maxBalas", "animacion"));
        this.sleep = Integer.parseInt(FileProperties.getParamProperties("sleep", "animacion"));
        this.keys = keys;
    }

    @Override
    public void run(){

        while (Boo) {
            
            synchronized (keys){
                ListIterator<Integer> keyIterator = keys.listIterator();
                while(keyIterator.hasNext()){
                    int key = keyIterator.next();
                    if (key == 39) {
                        girarDerecha();
                    }
                    if (key == 37) {
                        girarIzquierda();
                    }
                    if (key == 38) {
                        acelerar();
                    }
                    if (key == 32) {
                        try{
                            disparar();
                        }catch(Exception ex){

                        }
                    }
                }
            }

            c.paint(w2);
            for (Asteroide asteroide : a){
                asteroide.dibujar();
                asteroide.prox(this.sizeX, this.sizeY);
            }
            synchronized (b){
                ListIterator<Bala> iter = b.listIterator();
                while(iter.hasNext()){
                    Bala bala = iter.next();
                    if(bala.morir()){
                        iter.remove();
                    }else{
                        bala.dibujar();
                        bala.prox(sizeX, sizeY);
                        bala.tiempoVivo(sleep);
                    }
                    
                }
            }
            n.dibujar();
            n.updateWeaponCooldown(sleep);
            n.prox(this.sizeX, this.sizeY);
            try{
                ListIterator<Asteroide> iterador = a.listIterator();
                while (iterador.hasNext()) {
                    Asteroide asteroide = iterador.next();
                    if (Math.sqrt(Math.pow(n.x - asteroide.x, 2) + Math.pow(n.y - asteroide.y, 2)) < 2*asteroide.radio) {
                        n.dibujar();
                        iterador.remove();
                        Boo=false;
                    } else {
                        synchronized(b){
                            ListIterator<Bala> iter = b.listIterator();
                            while(iter.hasNext()){
                                Bala bala = iter.next();
                                if (Math.sqrt(Math.pow(bala.x - asteroide.x, 2) + Math.pow(bala.y - asteroide.y, 2)) < asteroide.radio) {
                                    this.score+=asteroide.reward;
                                    iter.remove();
                                    iterador.remove();
                                }
                            }
                        }
                    }
                }
                while(a.size()<5){
                    a.add(new Asteroide((Math.random() * this.sizeX), (Math.random() * this.sizeY), (Math.random() * 2 - 1), (Math.random() * 2 - 1), w2));
                }
                
                drawScore();
                w1.drawImage(imagen, 0, 0, null);

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ie) {
                    System.out.println("Me interrumpieron");
                }
            }catch(Exception ex){
                
            }
        }
    }
    
    public void drawScore(){
        Color temp = w2.getColor();
        w2.setColor(Color.YELLOW);
        w2.setFont(w2.getFont().deriveFont(20f));
        w2.drawString("Puntaje: ".concat(String.valueOf(score)), 650, 40);
        w2.setColor(temp);
    }

    public void girarDerecha() {
        n.girarDerecha();
    }

    public void girarIzquierda() {
        n.girarIzquierda();
    }

    public void acelerar() {
        n.acelerar();
    }
    
    public void disparar() throws Exception{
        synchronized (b){
            if(b.size()<=10){
                Bala bala = n.disparar();
                if(bala!=null){
                    b.add(bala);
                }
            }
        }
    }

    public void cerrar() {
        Boo = false;
    }
    
    public boolean overlaps (Nave n1, Asteroide a2) {
        return n1.x < a2.x + a2.bitX*2 && n1.x + n1.bitX*2 > a2.x && n1.y < a2.y + a2.bitY*2 && n1.y + n1.bitY*2 > a2.y;
    }

}
