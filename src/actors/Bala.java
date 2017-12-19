/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import java.awt.Color;
import utils.FileProperties;

/**
 *
 * @author Pablo
 */
public class Bala extends Actor{
        int ttl; 
        int time;
        
        public Bala(Nave n) throws Exception{
            angulo = n.angulo;
            this.x = n.x + 31 * Math.sin(angulo);
            this.y = n.y + 29 * Math.cos(angulo);
            this.g = n.g;
            vx = n.vx + maxSpeed * Math.sin(angulo);
            vy = n.vy + maxSpeed * Math.cos(angulo);
            this.maxSpeed = Integer.parseInt(FileProperties.getParamProperties("maxSpeed", "bala"));
            this.radio = Integer.parseInt(FileProperties.getParamProperties("radio", "bala"));
            this.ttl = Integer.parseInt(FileProperties.getParamProperties("ttl", "bala"));
            this.time = Integer.parseInt(FileProperties.getParamProperties("time", "bala"));
        }
        
        @Override
        public void dibujar() {
            Color orig = g.getColor();
            g.setColor(Color.YELLOW);
            g.fillOval((int) x - radio, (int) y - radio, 2 * radio, 2 * radio);
            g.setColor(orig);
            
        }       
        
        @Override
        public void prox(int sizeX, int sizeY) {
            if (this.x < 0) {
                this.x = this.x + sizeX;
            }
            if (this.x > sizeX) {
                this.x = this.x - sizeX;
            }
            if (this.y < 0) {
                this.y = this.y + sizeY;
            }
            if (this.y > sizeY){
                this.y = this.y - sizeY;
            }
            this.x = this.x + this.vx;
            this.y = this.y + this.vy;
        }
        
        public void tiempoVivo(int t){
            this.time+=3*t;
        }
        
        public boolean morir(){
            return this.time>this.ttl;
        }
    }
