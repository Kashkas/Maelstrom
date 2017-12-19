
import engine.Animacion;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import scenes.SpaceCanvas;
import utils.FileProperties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo
 */
public class MainWindow extends JFrame implements WindowListener, KeyListener{
    
    int sizeX;
    int sizeY;
    Color background = Color.blue;
    Image img;
    static final List<Integer> keys = Collections.synchronizedList(new ArrayList<>());
    
    Canvas c;
    Animacion o;
    
    public MainWindow() throws Exception{
        super("Asteroides");
        this.sizeX = Integer.parseInt(FileProperties.getParamProperties("sizeX", "mainwindow"));
        this.sizeY = Integer.parseInt(FileProperties.getParamProperties("sizeY", "mainwindow"));
        
        cargarJuego();
        
    }
    
    private void cargarJuego() throws Exception{
        this.c = new SpaceCanvas();
        this.c.setSize(sizeX, sizeY);
        this.c.setBackground(background);
        cargarVentana();       
        this.o = new Animacion(c, sizeX, sizeY, keys);
        o.start();
    }

    private void cargarVentana(){
        this.setLayout(new BorderLayout());
        this.add(this.c);
        this.addWindowListener(this);
        this.addKeyListener(this);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
        o.cerrar();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        synchronized (keys){
            if(!keys.contains(k.getKeyCode())){
                keys.add(k.getKeyCode());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyReleased(KeyEvent k) {
        synchronized(keys){
            if(keys.contains(k.getKeyCode())){
                keys.remove(keys.indexOf(k.getKeyCode()));
            }
        }
    }
    
}
