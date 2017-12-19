/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import utils.FileProperties;

/**
 *
 * @author Pablo
 */
public class StartCanvas extends Canvas {

    private final String resource;
    private Image img;
    
    public StartCanvas() throws IOException{
        super();
        this.resource = FileProperties.getParamProperties("resource", "spacecanvas");
        URL url = getClass().getResource(resource);
        img = Toolkit.getDefaultToolkit().getImage(url);
    }
    
    @Override
    public void paint(Graphics g) {
        // Draw the previously loaded image to Component.
        g.drawImage(img, 0, 0, null);

        // Draw sprites, and other things.
        // ....
    }
}
