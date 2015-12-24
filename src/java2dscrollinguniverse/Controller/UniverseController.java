/* 
 * The MIT License
 *
 * Copyright 2015 andrewnyhus.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package java2dscrollinguniverse.Controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.universe.Universe;
import java2dscrollinguniverse.SettingsSingleton;
import java2dscrollinguniverse.View.MainViewComponent;
import javax.swing.JFrame;

/**
 *
 * @author andrewnyhus
 */
public class UniverseController implements KeyListener{
    
    private Universe universe;
    private MainViewComponent view;
    private int playerSpeed = 5; //to be implemented later in player class
    
    public UniverseController(){
        
        JFrame frame = new JFrame("2D Universe");
        
        this.universe = new Universe(SettingsSingleton.getInstance().getViewRectangle());
        this.view = new MainViewComponent(this.universe);
        
        this.view.addControllerAsListener(this);
        
        Dimension origSizeOfFrame = frame.getSize();
        frame.add(this.view);
        Dimension newSizeOfFrame = new Dimension((int)origSizeOfFrame.getWidth() + 500, (int)origSizeOfFrame.getHeight() + 25 + 500);
        frame.setSize(newSizeOfFrame);
        frame.setResizable(false);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public Universe getUniverse(){
        return this.universe;
    }
    
    
    
    
    
    //Below is the implementation for the KeyListener methods.
    //These handle detection of various keys being pressed on the keyboard.
    
 //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    @Override
    public void keyTyped(KeyEvent e) {
        //ignore
    }

    /**
     * We will only pay attention to this KeyListener method, 
     * this method fires when the user presses a key on their 
     * keyboard, and the reason we must handle this event 
     * is to determine if the key pressed is a directional arrow key
     * (up, down, left, or right), and handle this event accordingly.
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        //store the int that corresponds to the key that was pressed
        int keycode = e.getKeyCode();
        
        
        TwoDimensionalMovement movement;
        
        
        //handle various cases of the keycode value.
        switch(keycode){
                
                //if key pressed was left arrow
                case KeyEvent.VK_LEFT:
                    movement = new TwoDimensionalMovement(-playerSpeed, 0);
                    this.getUniverse().movePlayer(movement);
                    this.view.updateUniverse(this.getUniverse());
                    
                    break;
                    
                //if key pressed was right arrow    
                case KeyEvent.VK_RIGHT:
                    movement = new TwoDimensionalMovement(playerSpeed, 0);
                    this.getUniverse().movePlayer(movement);
                    this.view.updateUniverse(this.getUniverse());
                    break;
                
                //if key pressed was up arrow
                case KeyEvent.VK_UP:
                    //***Note: remember that moving "up" visually to the user
                    // is equivalent to a decrease in y value since the origin 0, 0 is
                    //in the top left.  Oh Java Swing, we love you. <3
                    movement = new TwoDimensionalMovement(0, -playerSpeed);
                    this.getUniverse().movePlayer(movement);
                    this.view.updateUniverse(this.getUniverse());
                    break;
                    
                //if key pressed was down arrow 
                case KeyEvent.VK_DOWN:
                    //similarly moving down visually is equivalent to an increase in y value
                    movement = new TwoDimensionalMovement(0, playerSpeed);
                    this.getUniverse().movePlayer(movement);
                    this.view.updateUniverse(this.getUniverse());
                    
                    break;
                    
                //otherwise
                default:
                    //do nothing here, because if the user presses a key
                    //other then the up down left or right arrow key, the program 
                    //will land in this default case.
                    break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //ignore
    }
 //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
}
