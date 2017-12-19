/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import utils.FileProperties;

/**
 *
 * @author Pablo
 */
public class Nave extends Actor {

    public int bitX;
    public int bitY;
    Image img;
    int weaponCooldown;
    int weaponUsed;

    public Nave(Graphics g, int sizeX, int sizeY) throws Exception {
        this.resource = FileProperties.getParamProperties("resource", "nave");
        URL url = getClass().getResource(resource);
        this.img = Toolkit.getDefaultToolkit().getImage(url);
        this.g = g;
        this.x = sizeX / 2;
        this.y = sizeY / 2;
        this.bitX = Integer.parseInt(FileProperties.getParamProperties("bitX", "nave"));
        this.bitY = Integer.parseInt(FileProperties.getParamProperties("bitY", "nave"));
        this.maxSpeed = Integer.parseInt(FileProperties.getParamProperties("maxSpeed", "nave"));
        this.weaponCooldown = Integer.parseInt(FileProperties.getParamProperties("weaponCooldown", "nave"));
        this.weaponUsed = 0;
    }

    public void acelerar() {
        this.vx = this.vx - 0.5 * Math.sin(this.angulo + Math.PI);
        this.vy = this.vy - 0.5 * Math.cos(this.angulo + Math.PI);

        if (this.vx > this.maxSpeed) {
            this.vx = this.maxSpeed;
        }
        if (this.vx < -1 * this.maxSpeed) {
            this.vx = -1 * this.maxSpeed;
        }
        if (this.vy > this.maxSpeed) {
            this.vy = this.maxSpeed;
        }
        if (this.vy < -1 * this.maxSpeed) {
            this.vy = -1 * this.maxSpeed;
        }

    }

    public void girarDerecha() {
        this.angulo = this.angulo - (Math.PI / 24);
        if (this.angulo < 0) {
            this.angulo = this.angulo + 2 * Math.PI;
        }
    }

    public void girarIzquierda() {
        this.angulo = this.angulo + (Math.PI / 24);
        if (this.angulo > 0) {
            this.angulo = this.angulo - 2 * Math.PI;
        }
    }

    @Override
    public void dibujar() {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(this.x - this.bitX, this.y - this.bitY);
        g2.rotate(Math.PI - this.angulo, this.bitX, this.bitY);
        g2.drawImage(this.img, reset, null);
        g2.setTransform(reset);
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
        if (this.y > sizeY) {
            this.y = this.y - sizeY;
        }
        this.x = this.x + this.vx;
        this.y = this.y + this.vy;
    }

    public Bala disparar() throws Exception {
        if (this.weaponCooldown < this.weaponUsed) {
            weaponUsed = 0;
            return new Bala(this);
        }
        return null;
    }

    public void updateWeaponCooldown(int cooldown) {
        weaponUsed += cooldown;
    }

}
