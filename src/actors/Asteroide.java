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
public class Asteroide extends Actor {
    
    double spin;
    public int bitX;
    public int bitY;
    public int reward;
    Image img;

    public Asteroide(double x, double y, double vx, double vy, Graphics g) throws Exception {
        super();
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.g = g;
        this.radio = Integer.parseInt(FileProperties.getParamProperties("radio", "asteroide"));
        this.bitX = Integer.parseInt(FileProperties.getParamProperties("bitX", "asteroide"));
        this.bitY = Integer.parseInt(FileProperties.getParamProperties("bitY", "asteroide"));
        this.resource = FileProperties.getParamProperties("resource", "asteroide");
        URL url = getClass().getResource(resource);
        this.img = Toolkit.getDefaultToolkit().getImage(url);
        this.spin = Math.random() * 10 % 2;
        this.reward = 15;
    }

    @Override
    public void dibujar() {
        //g.fillOval((int) x - radio, (int) y - radio, 2 * radio, 2 * radio);
        angulo+=spin;
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(this.x - this.bitX, this.y - this.bitY);
        g2.rotate(Math.PI - Math.toRadians(this.angulo), this.bitX, this.bitY);
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

}
