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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;


/**
 *
 * @author andrewnyhus
 */
public class SettingsMenu extends JOptionPane{
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    private Color initialUniverseBGColor = SettingsSingleton.getInstance().getUniverseBackgroundColor();
    private Color initialPlayerColor = SettingsSingleton.getInstance().getPlayerColor();
    private Color initialPerimeterColor = SettingsSingleton.getInstance().getPerimeterColor();
    private Dimension initialViewDimension = SettingsSingleton.getInstance().getWindowDimension();
    private int initialPlayerSpeed = SettingsSingleton.getInstance().getPlayerSpeed();
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    //end of initial data members
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private Color currentUniverseBGColor;
    private Color currentPlayerColor;
    private Color currentPerimeterColor;
    private Dimension currentViewDimension;
    private int currentPlayerSpeed;
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    //end of class variables
    
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private EnterNumericalValueField viewWidthField, viewHeightField;
    private JLabel viewWidthLabel, viewHeightLabel;
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
        
        this.currentUniverseBGColor = this.initialUniverseBGColor;
        this.currentPlayerColor = this.initialPlayerColor;
        this.currentPerimeterColor = this.initialPerimeterColor;
        this.currentViewDimension = new Dimension(this.initialViewDimension.width, this.initialViewDimension.height);
        this.currentPlayerSpeed = this.initialPlayerSpeed;
        
        this.initGUIComponents();
        
        this.setMessage(this.getMessageObjectArray());
        
    }

    private void initGUIComponents(){
        
        
        this.colorUpdateObjectSelector.addActionListener((ActionEvent e) -> {
            this.updateColorIcon();
            this.updateApplyChangesButton();
        });
                

        DocumentListener listener = new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handle();
            }
            
            public void handle(){
                if(!viewWidthField.getText().equals("") && !viewHeightField.getText().equals(""))
                    updateCurrentViewDimension();
            }
            
        };
        
        
        this.viewWidthField = new EnterNumericalValueField(this.currentViewDimension.width, listener);
        this.viewHeightField = new EnterNumericalValueField(this.currentViewDimension.height, listener);
        
        
        
        
        this.setColorButton = new JButton("Choose an object in the dropdown and click here\nto customize color.");
        
        this.setColorButton.addActionListener((ActionEvent e) -> {
            //TODO: update color
                    Color c = null, cInit = null;
                    String objectToSet = (String)this.colorUpdateObjectSelector.getSelectedItem();
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
        
        this.viewWidthLabel = new JLabel("Initial Width (" + this.initialViewDimension.width + "):");
        this.viewHeightLabel = new JLabel("Initial Height (" + this.initialViewDimension.height + "):");
        
        this.updateColorIcon();
        this.updatePlayerSpeedLabel();        
        this.updateApplyChangesButton();

    }
    
    private Object[] getMessageObjectArray(){
        String increaseWindowString = "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\nIncrease Window Size (enter only numeric characters)\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n ";
        
        
        
        Object[] message = {
            this.setColorButton, this.colorUpdateObjectSelector, 
            this.playerSpeedLabel, this.playerSpeedSlider,
            increaseWindowString, this.viewWidthLabel, this.viewWidthField,
            this.viewHeightLabel, this.viewHeightField, 
            this.applyChangesButton
        };
        
        return message;
    }
    
    private void updatePlayerSpeedLabel(){
        this.playerSpeedLabel.setText("Player speed was: " + this.initialPlayerSpeed + ", set speed to: " + this.currentPlayerSpeed);
    }
    
    private void updateCurrentViewDimension(){
        
        int addedWidth = 0;
        int addedHeight = 0;
        int currentWidth = 0;
        int currentHeight = 0;

        try{
            addedWidth = Integer.parseInt(this.viewWidthField.getText());
        }catch(NumberFormatException e){
            addedWidth = 0;
        }
        
        
        try{
            addedHeight = Integer.parseInt(this.viewHeightField.getText());
        }catch(NumberFormatException e){
            addedHeight = 0;
        }
        
        
        currentWidth = addedWidth + this.initialViewDimension.width;
        currentHeight = addedHeight + this.initialViewDimension.height;
        
        this.currentViewDimension = new Dimension(currentWidth, currentHeight);
        
        this.viewWidthLabel.setText("Initial Width (" + this.initialViewDimension.width +
                ") + " + addedWidth + " = " + this.currentViewDimension.width + ":");

        this.viewHeightLabel.setText("Initial Height (" + this.initialViewDimension.height +
                ") + " + addedHeight + " = " + this.currentViewDimension.height + ":");
    
        this.updateApplyChangesButton();
    
    }
    
    private void updateApplyChangesButton(){
    
        boolean changesFound = true;
        if(this.initialUniverseBGColor.equals(this.currentUniverseBGColor)&&
                this.initialViewDimension.equals(this.currentViewDimension)&&
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
        this.initialViewDimension = this.currentViewDimension;
        
        SettingsSingleton.getInstance().setUniverseBackgroundColor(this.initialUniverseBGColor);
        SettingsSingleton.getInstance().setPerimeterColor(this.initialPerimeterColor);
        SettingsSingleton.getInstance().setPlayerColor(this.initialPlayerColor);
        SettingsSingleton.getInstance().setPlayerSpeed(this.initialPlayerSpeed);
        SettingsSingleton.getInstance().setWindowDimension(this.initialViewDimension);
        
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
