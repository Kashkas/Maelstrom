/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Pablo
 */
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FileProperties {
    
    public static String getParamProperties(String key, String nomProperties) throws IOException, MissingResourceException{
    	ResourceBundle rb = ResourceBundle.getBundle("configurations/".concat(nomProperties));       
        String pathTmp = rb.getString(key);
        return pathTmp;
    }
    
}
