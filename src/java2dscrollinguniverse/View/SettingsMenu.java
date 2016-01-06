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
import java2dscrollinguniverse.Model.actors.HUDMap.WindowCorner;
import java2dscrollinguniverse.SettingsSingleton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
    private Color initialContainerBGColor = SettingsSingleton.getInstance().getContainerBackgroundColor();
    private Color initialCenterOfViewActorColor = SettingsSingleton.getInstance().getCenterOfViewActorColor();
    private Color initialPerimeterColor = SettingsSingleton.getInstance().getPerimeterColor();
    private Dimension initialViewDimension = SettingsSingleton.getInstance().getWindowDimension();
    private int initialCenterOfViewActorSpeed = SettingsSingleton.getInstance().getCenterOfViewActorSpeed();
    private boolean initialShouldDisplayHUDMap = SettingsSingleton.getInstance().shouldShowHUDMap();
    private WindowCorner initialWindowCornerHUDMap = SettingsSingleton.getInstance().getHUDMapCorner();
    //---===---===---===---===---===---===---===---===---===---===---===---===
    //end of initial data members
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private Color currentContainerBGColor;
    private Color currentCenterOfViewActorColor;
    private Color currentPerimeterColor;
    private Dimension currentViewDimension;
    private int currentCenterOfViewActorSpeed;
    private boolean currentShouldDisplayHUDMap;
    private WindowCorner currentWindowCornerHUDMap;
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    //end of class variables
    
    
    //---===---===---===---===---===---===---===---===---===---===---===---===
    private EnterNumericalValueField viewWidthField, viewHeightField;
    private JLabel viewWidthLabel, viewHeightLabel;
    private String[] colorObjectChoices = {"Perimeter", "Center of View Actor", "Background Of Universe"};

    private JComboBox colorUpdateObjectSelector = new JComboBox(colorObjectChoices);
    private JButton setColorButton;
    
    private JLabel centerOfViewActorSpeedLabel = new JLabel();
    private JSlider centerOfViewActorSpeedSlider;
    
    private Icon iconObject = null;

    private JButton applyChangesButton;
    
    private JRadioButton displayHUDMap;
    private JRadioButton dontDisplayHUDMap;
    private ButtonGroup hudMapButtonGroup;
    
    private String[] cornerChoices = {"Top Right", "Top Left", "Bottom Left", "Bottom Right"};
    private JComboBox hudMapCornerSelector = new JComboBox(cornerChoices);
    
    
    //---===---===---===---===---===---===---===---===---===---===---===---===    
    //end of GUI components
    

    
    public SettingsMenu(){
        super("", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
        
        this.currentContainerBGColor = this.initialContainerBGColor;
        this.currentCenterOfViewActorColor = this.initialCenterOfViewActorColor;
        this.currentPerimeterColor = this.initialPerimeterColor;
        this.currentViewDimension = new Dimension(this.initialViewDimension.width, this.initialViewDimension.height);
        this.currentCenterOfViewActorSpeed = this.initialCenterOfViewActorSpeed;
        this.currentShouldDisplayHUDMap = this.initialShouldDisplayHUDMap;
        this.currentWindowCornerHUDMap = this.initialWindowCornerHUDMap;
        
        this.initGUIComponents();
        
        this.setMessage(this.getMessageObjectArray());
        
    }

    private void initGUIComponents(){
        
        this.hudMapCornerSelector.setSelectedIndex(SettingsSingleton.getInstance().getHUDMapCorner().getValue());
        
        this.hudMapCornerSelector.addActionListener((ActionEvent e) -> {
            int selectedIndex = this.hudMapCornerSelector.getSelectedIndex();
            this.currentWindowCornerHUDMap = WindowCorner.values()[selectedIndex];

            this.updateApplyChangesButton();
        });
        
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
                            case "Center of View Actor":
                                cInit = this.currentCenterOfViewActorColor;
                                break;
                            case "Background Of Universe":
                                cInit = this.currentContainerBGColor;
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
                            case "Center of View Actor":
                                this.currentCenterOfViewActorColor = c;
                                break;
                            case "Background Of Universe":
                                this.currentContainerBGColor = c;
                                break;
                        }
                        
                    }
            this.updateColorIcon();
            this.updateApplyChangesButton();
        });
        
        
        this.centerOfViewActorSpeedSlider = new JSlider(15, 25);
        this.centerOfViewActorSpeedSlider.setValue(this.currentCenterOfViewActorSpeed);
        
        this.centerOfViewActorSpeedSlider.addChangeListener((ChangeEvent e) -> {
            this.currentCenterOfViewActorSpeed = this.centerOfViewActorSpeedSlider.getValue();
            this.updateCenterOfViewActorSpeedLabel();
            this.updateApplyChangesButton();
        });
        
        this.applyChangesButton = new JButton("Apply Changes");
        
        this.applyChangesButton.addActionListener((ActionEvent e) -> {
            this.applyChanges();
        });
        
        this.viewWidthLabel = new JLabel("Initial Width (" + this.initialViewDimension.width + "):");
        this.viewHeightLabel = new JLabel("Initial Height (" + this.initialViewDimension.height + "):");
        
        this.displayHUDMap = new JRadioButton("Display HUD Menu");
        this.dontDisplayHUDMap = new JRadioButton("Do not display HUD Menu");
        
        this.hudMapButtonGroup = new ButtonGroup();
        this.hudMapButtonGroup.add(this.displayHUDMap);
        this.hudMapButtonGroup.add(this.dontDisplayHUDMap);
        
        if(this.currentShouldDisplayHUDMap){
            this.displayHUDMap.setSelected(true);
            this.dontDisplayHUDMap.setSelected(false);
        }else{
            this.displayHUDMap.setSelected(false);
            this.dontDisplayHUDMap.setSelected(true);        
        }
        
        this.displayHUDMap.addActionListener((ActionEvent e) -> {
            this.currentShouldDisplayHUDMap = true;
            this.updateApplyChangesButton();
        });
        
        this.dontDisplayHUDMap.addActionListener((ActionEvent e) -> {
            this.currentShouldDisplayHUDMap = false;
            this.updateApplyChangesButton();
        });
        
        this.updateColorIcon();
        this.updateCenterOfViewActorSpeedLabel();        
        this.updateApplyChangesButton();

    }
    
    private Object[] getMessageObjectArray(){
        String increaseWindowString = "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\nIncrease Window Size (enter only numeric characters)\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n ";
        
        
        
        Object[] message = {
            this.setColorButton, this.colorUpdateObjectSelector, 
            this.centerOfViewActorSpeedLabel, this.centerOfViewActorSpeedSlider,
            increaseWindowString, this.viewWidthLabel, this.viewWidthField,
            this.viewHeightLabel, this.viewHeightField, 
            this.displayHUDMap, this.dontDisplayHUDMap, 
            "HUD Map Corner: ", this.hudMapCornerSelector, this.applyChangesButton
        };
        
        return message;
    }
    
    private void updateCenterOfViewActorSpeedLabel(){
        this.centerOfViewActorSpeedLabel.setText("Center of View Actor speed was: " + this.initialCenterOfViewActorSpeed + ", set speed to: " + this.currentCenterOfViewActorSpeed);
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
        if(this.initialContainerBGColor.equals(this.currentContainerBGColor)&&
                this.initialViewDimension.equals(this.currentViewDimension)&&
                this.initialCenterOfViewActorSpeed == this.currentCenterOfViewActorSpeed&&
                this.initialCenterOfViewActorColor.equals(this.currentCenterOfViewActorColor)&&
                this.initialPerimeterColor.equals(this.currentPerimeterColor)&&
                this.initialShouldDisplayHUDMap == this.currentShouldDisplayHUDMap&&
                this.initialWindowCornerHUDMap == this.currentWindowCornerHUDMap){
            changesFound = false;            
        }
        
        this.applyChangesButton.setEnabled(changesFound);
        
    }
    
    private void applyChanges(){
        this.initialContainerBGColor = this.currentContainerBGColor;
        this.initialPerimeterColor = this.currentPerimeterColor;
        this.initialCenterOfViewActorColor = this.currentCenterOfViewActorColor;
        this.initialCenterOfViewActorSpeed = this.currentCenterOfViewActorSpeed;
        this.initialViewDimension = this.currentViewDimension;
        this.initialShouldDisplayHUDMap = this.currentShouldDisplayHUDMap;
        this.initialWindowCornerHUDMap = this.currentWindowCornerHUDMap;
        
        SettingsSingleton.getInstance().setContainerBackgroundColor(this.initialContainerBGColor);
        SettingsSingleton.getInstance().setPerimeterColor(this.initialPerimeterColor);
        SettingsSingleton.getInstance().setCenterOfViewActorColor(this.initialCenterOfViewActorColor);
        SettingsSingleton.getInstance().setCenterOfViewActorSpeed(this.initialCenterOfViewActorSpeed);
        SettingsSingleton.getInstance().setWindowDimension(this.initialViewDimension);
        SettingsSingleton.getInstance().setShouldShowHUDMap(this.initialShouldDisplayHUDMap);
        SettingsSingleton.getInstance().setHUDMapCorner(this.initialWindowCornerHUDMap);
        
        this.updateApplyChangesButton();
    }
    
    private void updateColorIcon(){
        String s = (String)this.colorUpdateObjectSelector.getSelectedItem();
        Color c = null;
        
        switch(s){
        
            case "Perimeter":
                c = this.currentPerimeterColor;
                break;
            case "Center of View Actor":
                c = this.currentCenterOfViewActorColor;
                break;
            case "Background Of Universe":
                c = this.currentContainerBGColor;
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

            g2d.setColor(this.color);
            g2d.fillRect(x, y, width -2 ,height -2);
            

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
