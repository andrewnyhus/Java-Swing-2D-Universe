/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples.PingPong;

import java.awt.Dimension;
import java2dscrollinguniverse.SettingsSingleton;
import javax.swing.SwingUtilities;

/**
 *
 * @author andrewnyhus
 */
public class PingPongMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        SwingUtilities.invokeLater(() -> {
            
            Dimension d = new Dimension(600, 400);
            
            //you want to set the window dimension before instantiating 
            //the controller, unless you want for the window size to 
            //be the default 500 pixels by 500 pixels.
            SettingsSingleton.getInstance().setWindowDimension(d);
            
            //create controller for ping pong game with dimension d
            PingPongController ppc = new PingPongController(d);
            
        });
        
    }

}
