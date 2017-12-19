/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import java.awt.Graphics;

/**
 *
 * @author Pablo
 */
public abstract class Actor{

    protected int maxSpeed = 10;
    public double x = 0; 
    public double y = 0;
    public double vx = 0;
    public double vy = 0;
    protected String resource = "";
    double angulo = Math.PI;
    protected Graphics g = null;
    public int radio = 0;
    
    public abstract void dibujar();
    public abstract void prox(int sizeX, int sizeY);

}
