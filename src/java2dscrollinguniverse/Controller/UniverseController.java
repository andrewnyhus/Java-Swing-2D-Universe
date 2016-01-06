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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.universe.Universe;
import java2dscrollinguniverse.SettingsSingleton;
import java2dscrollinguniverse.View.MainViewComponent;
import java2dscrollinguniverse.View.SettingsMenu;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author andrewnyhus
 */
public class UniverseController implements KeyListener, ActionListener{
    
    private Universe universe;
    private final MainViewComponent view;
    private final JFrame frame;
    private JMenuBar menuBar;
    private final Dimension origSizeOfFrame;
    
    public UniverseController() {
        this.frame = new JFrame("2D Universe");
                    
        //Dimension d = new Dimension(3000, 1000);
        Dimension d = this.getDimensionFromUser("Please enter the size of the universe");
                
        this.universe = new Universe(d);
        
        this.view = new MainViewComponent(this.universe);
        
        this.origSizeOfFrame = this.frame.getSize();
        
        this.frame.add(this.view);
        
        Dimension newSizeOfFrame = 
                new Dimension(
                        origSizeOfFrame.width+ SettingsSingleton.getInstance().getWindowWidth(),
                        origSizeOfFrame.height + 45 + SettingsSingleton.getInstance().getWindowHeight());
        

        this.frame.setSize(newSizeOfFrame);
        this.frame.setResizable(false);

        this.addMenuBar();
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListenerToView();
    }
    
    public Universe getUniverse(){
        return this.universe;
    }
 
    /**
     * Prompts user for a pixel size for height and width
     * @param messageString
     * @return user defined screen Dimension
     */
    private Dimension getDimensionFromUser(String messageString){
        

        boolean userResponseIsValid = false;
        JTextField widthField = null, heightField = null;

        while(!userResponseIsValid){

            widthField = new JTextField();
            heightField = new JTextField();
            
            Object[] message = {
                messageString,
                "Width:", widthField,
                "Height:", heightField
            };
            
            int option = JOptionPane.showConfirmDialog(null, message, "WxH (Pixels)", JOptionPane.DEFAULT_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                if ( this.inputStringIsAValidInt(widthField.getText()) && this.inputStringIsAValidInt(heightField.getText()) ) {
                    userResponseIsValid = true;
                } else {
                    userResponseIsValid = false;
                    JOptionPane.showMessageDialog(this.frame, "Invalid input, please make sure to enter only numerical characters (0-9) and no spaces");
                }

            }else{
                userResponseIsValid = false;
                JOptionPane.showMessageDialog(this.frame, "Invalid input, please make sure to enter only numerical characters (0-9) and no spaces");
            }
        }
        
        @SuppressWarnings("null")
        int widthDimension = Integer.parseInt(widthField.getText());
        @SuppressWarnings("null")
        int heightDimension = Integer.parseInt(heightField.getText());
               
        return new Dimension(widthDimension, heightDimension);
    }
    
    /**
     * Returns a boolean which indicated if String s is able to be 
     * parsed into an int.
     * @param s
     * @return boolean isParsableToInt
     */
    private boolean inputStringIsAValidInt(String s){
        
        try{
            int stringToIntConvertAttempt = Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    /**
     * Setup, and set JMenuBar to this.frame.
     */
    private void addMenuBar(){
        this.menuBar = new JMenuBar();
        
        JMenu newMenu = new JMenu("New");
        
        JMenuItem newWindowItem = new JMenuItem("New Window");
        newWindowItem.setActionCommand("new_window");
        
        
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.setActionCommand("settings");

        newWindowItem.addActionListener(this);
        settingsMenuItem.addActionListener(this);
        
        
        newMenu.add(newWindowItem);
                
        this.menuBar.add(newMenu);
        this.menuBar.add(settingsMenuItem);
        
        this.frame.setJMenuBar(this.menuBar);
        
    }
    
    private void addKeyListenerToView(){
        this.view.addListener(this);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        
        switch (actionCommand) {
            case "new_window":
                
                break;
            case "settings":
                
                displaySettingsMenu();
                
                break;
        }
        
    }

    private void displaySettingsMenu() throws HeadlessException {
        SettingsMenu pane = new SettingsMenu();
        JDialog d = pane.createDialog("Settings");
        d.setSize(750, 500);
        d.setVisible(true);
        
        Dimension newSizeOfFrame =
                new Dimension(
                        this.origSizeOfFrame.width + SettingsSingleton.getInstance().getWindowWidth(),
                        this.origSizeOfFrame.height + 45 + SettingsSingleton.getInstance().getWindowHeight());
        
        
        this.frame.setSize(newSizeOfFrame);
        this.view.setSize(SettingsSingleton.getInstance().getWindowDimension());
        this.view.repaint();
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
        
        int centerOfViewActorVelocity = SettingsSingleton.getInstance().getCenterOfViewActorSpeed();
        //handle various cases of the keycode value.
        switch(keycode){
                
                //if key pressed was left arrow
                case KeyEvent.VK_LEFT:
                    movement = new TwoDimensionalMovement(-centerOfViewActorVelocity, 0);
                    this.getUniverse().attemptToMoveActor(this.universe.getCenterOfViewActor(), movement);
                    this.view.updateUniverse(this.getUniverse());
                    
                    break;
                    
                //if key pressed was right arrow    
                case KeyEvent.VK_RIGHT:
                    movement = new TwoDimensionalMovement(centerOfViewActorVelocity, 0);
                    this.getUniverse().attemptToMoveActor(this.universe.getCenterOfViewActor(), movement);
                    this.view.updateUniverse(this.getUniverse());

                    break;
                
                //if key pressed was up arrow
                case KeyEvent.VK_UP:
                    //***Note: remember that moving "up" visually to the user
                    // is equivalent to a decrease in y value since the origin 0, 0 is
                    //in the top left.  Oh Java Swing, we love you. 
                    movement = new TwoDimensionalMovement(0, -centerOfViewActorVelocity);
                    this.getUniverse().attemptToMoveActor(this.universe.getCenterOfViewActor(), movement);
                    this.view.updateUniverse(this.getUniverse());

                    break;
                    
                //if key pressed was down arrow 
                case KeyEvent.VK_DOWN:
                    //similarly moving down visually is equivalent to an increase in y value
                    movement = new TwoDimensionalMovement(0, centerOfViewActorVelocity);
                    this.getUniverse().attemptToMoveActor(this.universe.getCenterOfViewActor(), movement);
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

    /**
     * @param universe the universe to set
     */
    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
    
}
