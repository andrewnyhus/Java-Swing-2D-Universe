/*
 * The MIT License
 *
 * Copyright 2016 andrewnyhus.
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
package Examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;
import neuLayout.Controller.UniverseController;
import neuLayout.Model.TwoDimensionalMovement;
import neuLayout.Model.actors.Actor;
import neuLayout.Model.actors.ActorLabel;
import neuLayout.Model.actors.ActorLabel.PositionOfLabel;
import neuLayout.Model.actors.ActorType;
import neuLayout.SettingsSingleton;
import neuLayout.SettingsSingleton.ViewScrollMode;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author andrewnyhus
 */
public class Football {
    private static Dimension game_size = new Dimension(340, 600);
    private static JLabel ballLocationLabel;
    private static Color backgroundGrassColor = new Color(26, 108, 0);
    private static Color solidWhiteColor = new Color(255, 255, 255);
    private static int[] yValOfYardLines;
    private static UniverseController mainController;
    private static Actor football;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Football.updateFrameTitle();
            }
        };
        Timer timer = new Timer();

        
        SwingUtilities.invokeLater(() -> {
    
            Actor[] listOfInitialMembers = setupActors();
            
            loadSettings();

            mainController = new UniverseController("Football", game_size, listOfInitialMembers);
            
            timer.scheduleAtFixedRate(timerTask, 10, 300);
            
            
        });
    }

    
    /**
     * Creates and returns all actors for the game.
     * @return listOfInitialMembers, an Actor array with all actors needed in the game.
     */
    private static Actor[] setupActors() {
        
        
        Actor[] yardLines = generateYardLines(game_size);
        yValOfYardLines = new int[yardLines.length];
        
        Actor[] listOfInitialMembers = new Actor[2+yardLines.length];
        
        for(int x = 0; x < yardLines.length; x++){
            listOfInitialMembers[x] = yardLines[x];
            
            yValOfYardLines[x] = yardLines[x].getTopLeftLocation().y;
        }
        
        listOfInitialMembers[yardLines.length] = generateMidField(Color.WHITE);
        listOfInitialMembers[yardLines.length + 1] = generateFootball();
        return listOfInitialMembers;
    }

    
    /**
     * Modifies default settings to fit the needs of our football game.
     */
    private static void loadSettings() {
        SettingsSingleton.getInstance().setContainerBackgroundColor(backgroundGrassColor);
        SettingsSingleton.getInstance().setPerimeterColor(solidWhiteColor);
        
        
        SettingsSingleton.getInstance().setScrollMode(ViewScrollMode.DONT_SCROLL);
        Dimension window_size = new Dimension(game_size.width + 150, game_size.height);
        SettingsSingleton.getInstance().setWindowDimension(window_size);
    }

    
    /**
     * Creates and returns a circle of color C for the middle of the field.
     * @param c
     * @return Actor that is a circle of color c.
     */
    private static Actor generateMidField(Color c){
    
        int diameter = game_size.width/4;

        Actor midField = new Actor(ActorType.miscObject,new Point(game_size.width/2 - diameter/2,
                                                                    game_size.height/2 - diameter/2),
                                                c, new Ellipse2D.Double(0, 0, diameter, diameter));
        
        return midField;
    }

    /**
     * Creates and returns an actor representing the football.
     * @return Actor for the football, with the laces as children to it.
     */
    private static Actor generateFootball(){

        TwoDimensionalMovement initialVelocity = new TwoDimensionalMovement(1, 1);

        Color brown = new Color(140, 40, 40);
        Point location = new Point(game_size.width/2, game_size.height/2);
        
        Ellipse2D.Double ballShape = new Ellipse2D.Double(0, 0, 60, 25);

        Actor mainLace = new Actor(ActorType.miscObject, new Point(15, 11),
                                                        Color.WHITE, new Rectangle(0, 0, 30, 3));

        Actor[] childActors = new Actor[9];
        childActors[0] = mainLace;
        
        for(int i = 0; i < 8; i++){
            int width = 1;
            int height = 5;
            
            int xLoc = 15 + i*(30/7);
            int yLoc = 11 - (height/3);
            
            Actor verticalLace = new Actor(ActorType.miscObject, new Point(xLoc,yLoc),
                                                    Color.WHITE, new Rectangle(0, 0, width, height));
            childActors[i+1] = verticalLace;

        }

        
        football = new Actor(ActorType.miscObject, location, brown, ballShape, childActors);
        football.setVelocity(initialVelocity);
        return football;
    }

    /**
     * Creates and returns an array of actors which represent the yard lines (endzone, 10yd, 20yd...).
     * @param fieldDimensions
     * @return Actor array of lines.
     */
    private static Actor[] generateYardLines(Dimension fieldDimensions){

        int width = fieldDimensions.width - 20;
        int height = fieldDimensions.height - 20;//accounting for perimeter 

        int equivTenYards = 10*(height/110);
        
        int NUM_LINES = 11;
        String[] labelStringsForLine = new String[NUM_LINES];
        Actor[] arrayOfLines = new Actor[NUM_LINES];

        labelStringsForLine[0] = "HOME TEAM";
        labelStringsForLine[1] = "10";
        labelStringsForLine[2] = "20";
        labelStringsForLine[3] = "30";
        labelStringsForLine[4] = "40";
        labelStringsForLine[5] = "50";
        labelStringsForLine[6] = "40";
        labelStringsForLine[7] = "30";
        labelStringsForLine[8] = "20";
        labelStringsForLine[9] = "10";
        labelStringsForLine[10] = "AWAY TEAM";
        
        
        
        for(int i = 0; i < NUM_LINES; i++){
            int x = 10;
            int y = equivTenYards*(i+1);
            PositionOfLabel position = PositionOfLabel.LEFT_OF_BOTTOM;

            if(i==0)
                position = PositionOfLabel.RIGHT_OF_TOP;
            
            Actor currLine = new Actor(ActorType.miscObject, new Point(x, y),
                                       Color.WHITE, new Rectangle(0, 0, width, 3),null,
                                        new ActorLabel(labelStringsForLine[i], position));
            
            SettingsSingleton.getInstance().setLabelColor(Color.WHITE);
            arrayOfLines[i] = currLine;
        }
        
        return arrayOfLines;
    }
    
    /**
     * Gets current location of the ball and updates the Frame's title
     * accordingly.
     */
    private static void updateFrameTitle(){

        String[] arrOfBallLocations = new String[12];
        arrOfBallLocations[0] = "in the home teams end zone";
        arrOfBallLocations[1] = "behind home teams 10 yard line";
        arrOfBallLocations[2] = "between home teams 10 and 20 yard line";
        arrOfBallLocations[3] = "between home teams 20 and 30 yard line";
        arrOfBallLocations[4] = "between home teams 30 and 40 yard line";
        arrOfBallLocations[5] = "between home teams 40 yard line and the 50 yard line";
        arrOfBallLocations[6] = "between the away teams 40 yard line and the 50 yard line";
        arrOfBallLocations[7] = "between the away teams 30 and 40 yard line";
        arrOfBallLocations[8] = "between the away teams 20 and 30 yard line";
        arrOfBallLocations[9] = "between the away teams 10 and 20 yard line";
        arrOfBallLocations[10] = "behind the away teams 10 yard line";
        arrOfBallLocations[11] = "in the away teams end zone";
        
        int ballLocationIndex = getBallLocationIndex();
        if(ballLocationIndex != -1){
            mainController.getFrame().setTitle("Ball is: " + arrOfBallLocations[ballLocationIndex]);
        }else{
            mainController.getFrame().setTitle("");
        }
    
    }
    
    /**
     * Calculates which portion of the field the ball is located in, to provide an
     * index representing a rough estimate of its location;  0 = home end zone, 1 = behind home 10
     * 2 = between home 10 and 20, and so on, until 11 = away end zone.
     * @return locIndex value = 0-11 , -1 if ball is in invalid location.
     */
    private static int getBallLocationIndex(){
        int ballYValue = football.getTopLeftLocation().y;
        int locIndex;
        
        if(ballYValue < yValOfYardLines[0]){
            locIndex = 0;
        }else if(ballYValue < yValOfYardLines[1]){
            locIndex = 1;
        }else if(ballYValue < yValOfYardLines[2]){
            locIndex = 2;
        }else if(ballYValue < yValOfYardLines[3]){
            locIndex = 3;
        }else if(ballYValue < yValOfYardLines[4]){
            locIndex = 4;
        }else if(ballYValue < yValOfYardLines[5]){
            locIndex = 5;
        }else if(ballYValue < yValOfYardLines[6]){
            locIndex = 6;
        }else if(ballYValue < yValOfYardLines[7]){
            locIndex = 7;
        }else if(ballYValue < yValOfYardLines[8]){
            locIndex = 8;
        }else if(ballYValue < yValOfYardLines[9]){
            locIndex = 9;
        }else if(ballYValue < yValOfYardLines[10]){
            locIndex = 10;
        }else if(ballYValue > yValOfYardLines[10]){
            locIndex = 11;
        }else{
            locIndex = -1;
        }
        
        return locIndex;
    }
    
}
