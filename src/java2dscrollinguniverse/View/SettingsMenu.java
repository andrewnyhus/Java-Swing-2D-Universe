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
package java2dscrollinguniverse.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java2dscrollinguniverse.SettingsSingleton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author andrewnyhus
 */
public class SettingsMenu extends JOptionPane{
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    private Color initialUniverseBGColor = SettingsSingleton.getInstance().getUniverseBackgroundColor();
    private Color initialPlayerColor = SettingsSingleton.getInstance().getPlayerColor();
    private Color initialPerimeterColor = SettingsSingleton.getInstance().getPerimeterColor();
    private Dimension initialScreenDimension = SettingsSingleton.getInstance().getWindowDimension();
    private int initialPlayerSpeed = SettingsSingleton.getInstance().getPlayerSpeed();
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    //end of initial data members
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private Color currentUniverseBGColor;
    private Color currentPlayerColor;
    private Color currentPerimeterColor;
    private Dimension currentScreenDimension;
    private int currentPlayerSpeed;
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    //end of class variables
    
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private JTextField screenWidthField, screenHeightField;
    private String[] colorObjectChoices = {"Perimeter", "Player", "Background Of Universe"};

    private JComboBox colorUpdateObjectSelector = new JComboBox(colorObjectChoices);
    private JButton setColorButton;
    
    private JLabel playerSpeedLabel = new JLabel();
    private JSlider playerSpeedSlider;
    
    private Icon iconObject = null;

    private JButton applyChangesButton;
    
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    //end of GUI components
    
    
    public SettingsMenu(){
        super("", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
        this.currentUniverseBGColor = initialUniverseBGColor;
        this.currentPlayerColor = initialPlayerColor;
        this.currentPerimeterColor = initialPerimeterColor;
        this.currentScreenDimension = initialScreenDimension;
        this.currentPlayerSpeed = initialPlayerSpeed;
        
        this.initGUIComponents();
        
        this.setMessage(this.getMessageObjectArray());
        
    }

    private void initGUIComponents(){
        
        
        this.colorUpdateObjectSelector.addActionListener((ActionEvent e) -> {
            this.updateColorIcon();
            this.updateApplyChangesButton();
        });
        
        
        this.screenWidthField = new JTextField(this.currentScreenDimension.width);
        this.screenHeightField = new JTextField(this.currentScreenDimension.height);
        
        this.setColorButton = new JButton("Set Color of: ");
        
        this.setColorButton.addActionListener((ActionEvent e) -> {
            //TODO: update color
            System.out.println("update color");
                    Color c = null, cInit = null;
                    String objectToSet = (String)this.colorUpdateObjectSelector.getSelectedItem();
                    System.out.println(":" + objectToSet + ":");
                        switch(objectToSet){
                            case "Perimeter":
                                cInit = this.currentPerimeterColor;
                                break;
                            case "Player":
                                cInit = this.currentPlayerColor;
                                break;
                            case "Background Of Universe":
                                cInit = this.currentUniverseBGColor;
                                break;
                        }

                    if(cInit != null){
                        c = JColorChooser.showDialog(((Component) e.getSource()).getParent(), ("Set Color Of " + objectToSet), 
                            cInit);
                        
                    }

                    if(c != null){
                        switch(objectToSet){
                            case "Perimeter":
                                this.currentPerimeterColor = c;
                                break;
                            case "Player":
                                this.currentPlayerColor = c;
                                break;
                            case "Background Of Universe":
                                this.currentUniverseBGColor = c;
                                break;
                        }
                        
                    }
            this.updateColorIcon();
            this.updateApplyChangesButton();
        });
        
        
        this.playerSpeedSlider = new JSlider(15, 25);
        this.playerSpeedSlider.setValue(this.currentPlayerSpeed);
        
        this.playerSpeedSlider.addChangeListener((ChangeEvent e) -> {
            this.currentPlayerSpeed = this.playerSpeedSlider.getValue();
            this.updatePlayerSpeedLabel();
            this.updateApplyChangesButton();
        });
        
        this.applyChangesButton = new JButton("Apply Changes");
        
        this.applyChangesButton.addActionListener((ActionEvent e) -> {
            this.applyChanges();
        });
        
        //this.setIcon(new ColorIcon(Color.RED));
        this.updateColorIcon();
        this.updatePlayerSpeedLabel();        
        this.updateApplyChangesButton();

    }
    
    private Object[] getMessageObjectArray(){
        Object[] message = {
            this.setColorButton, this.colorUpdateObjectSelector, 
            "Change Window Size (enter only numeric characters)", "Width:", this.screenWidthField,
            "Height:", this.screenHeightField, 
            this.playerSpeedLabel, this.playerSpeedSlider, this.applyChangesButton
        };
        
        return message;
    }
    
    private void updatePlayerSpeedLabel(){
        this.playerSpeedLabel.setText("Player speed was: " + this.initialPlayerSpeed + ", set speed to: " + this.currentPlayerSpeed);
    }
    
    private void updateApplyChangesButton(){
    
        boolean changesFound = true;
        if(this.initialUniverseBGColor.equals(this.currentUniverseBGColor)&&
                this.initialScreenDimension.equals(this.currentScreenDimension)&&
                this.initialPlayerSpeed == this.currentPlayerSpeed&&
                this.initialPlayerColor.equals(this.currentPlayerColor)&&
                this.initialPerimeterColor.equals(this.currentPerimeterColor)){
            changesFound = false;            
        }
        
        this.applyChangesButton.setEnabled(changesFound);
        
    }
    
    private void applyChanges(){
        this.initialUniverseBGColor = this.currentUniverseBGColor;
        this.initialPerimeterColor = this.currentPerimeterColor;
        this.initialPlayerColor = this.currentPlayerColor;
        this.initialPlayerSpeed = this.currentPlayerSpeed;
        this.initialScreenDimension = this.currentScreenDimension;
        
        SettingsSingleton.getInstance().setUniverseBackgroundColor(this.initialUniverseBGColor);
        SettingsSingleton.getInstance().setPerimeterColor(this.initialPerimeterColor);
        SettingsSingleton.getInstance().setPlayerColor(this.initialPlayerColor);
        SettingsSingleton.getInstance().setPlayerSpeed(this.initialPlayerSpeed);
        SettingsSingleton.getInstance().setWindowDimension(this.initialScreenDimension);
        
        this.updateApplyChangesButton();
    }
    
    private void updateColorIcon(){
        String s = (String)this.colorUpdateObjectSelector.getSelectedItem();
        Color c = null;
        
        switch(s){
        
            case "Perimeter":
                c = this.currentPerimeterColor;
                break;
            case "Player":
                c = this.currentPlayerColor;
                break;
            case "Background Of Universe":
                c = this.currentUniverseBGColor;
                break;        
        }
        
        if(c != null){
            this.setIcon(new ColorIcon(c));
        }
        
    }
    
    
    public class ColorIcon implements Icon{
        
        private final int width = 60;
        private final int height = 60;
        
        private Color color;
        
        public ColorIcon(Color c){
            super();
            this.color = c;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();

            //g2d.setColor(Color.WHITE);
            //g2d.fillRect(x +1 ,y + 1,width -2 ,height -2);

            g2d.setColor(this.color);
            g2d.fillRect(x, y, width -2 ,height -2);

            //g2d.setColor(Color.RED);

            //g2d.setStroke(stroke);
            //g2d.drawLine(x +10, y + 10, x + width -10, y + height -10);
            //g2d.drawLine(x +10, y + height -10, x + width -10, y + 10);*/
            

            g2d.dispose();        
        }

        @Override
        public int getIconWidth() {
            return this.width;
        }

        @Override
        public int getIconHeight() {
            return this.height;
        }
    }


}
