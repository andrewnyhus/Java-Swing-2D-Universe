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
package java2dscrollinguniverse.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.container.ContainerUniverse;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author andrewnyhus
 */
public class ActorInspectorPane extends JOptionPane{
    
    private Actor actorToInspect;
    private ContainerUniverse containerUniverse;
    private JButton editActorButton;
    
    
    public ActorInspectorPane(Actor a, ContainerUniverse containerUniverse){
    
        this.actorToInspect = a;
        this.containerUniverse = containerUniverse;
        
                
        this.editActorButton = new JButton("Edit Actor");
        
        this.editActorButton.addActionListener((ActionEvent e) -> {

            this.displayActorEditor();

        });
        
        this.setIcon(new ActorPreviewIcon(this.actorToInspect.getColor(),
                                        this.actorToInspect.getShape()));
        
        this.setMessage(this.getObjectArrayMessage());
        
    }


    private void displayActorEditor(){
        
        EditActorPane actorEditor = new EditActorPane(this.actorToInspect, this.containerUniverse);
        JDialog actorEditorDialog = actorEditor.createDialog("Actor Editor");
        
        actorEditorDialog.setSize(450, 500);
        actorEditorDialog.setVisible(true);

        //repaint stuff here if needed

        actorEditor.setVisible(false);
        actorEditorDialog.setVisible(false);
        actorEditorDialog.dispose();
        actorEditor = null;
        actorEditorDialog = null;
        this.containerUniverse.handleDuplicates();
    }

    
    private Object[] getObjectArrayMessage(){
        Object[] message = {this.actorToInspect.getListOfPropertyStrings(), this.editActorButton};
        return message;
    }
    

    private class ActorPreviewIcon implements Icon{
        
        private final int widthBounds = 140;
        private final int heightBounds = 140;
        private Shape shape;
        private Color color;
        
        public ActorPreviewIcon(Color c, Shape s){
            super();
            this.color = c;
            this.shape = s;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(Color.white);
            g2d.drawRect(x, y, widthBounds +2 ,heightBounds +2);
            g2d.setColor(this.color);
            g2d.fill(this.getShapeToDrawWithXY(x, y));

            g2d.dispose();        
        }
        
        private Shape getShapeToDrawWithXY(int x, int y){
            
            
            if(this.shape instanceof Rectangle){
                double heightOverWidthRatio = this.shape.getBounds().getHeight()/
                                                this.shape.getBounds().getWidth();
                int height, width;
                if(heightOverWidthRatio == 1.0){
                    //height == width
                    height = this.heightBounds;
                    width = this.widthBounds;
                }else if(heightOverWidthRatio > 1.0){
                    //height > width
                    height = this.heightBounds;
                    width = (int)((double)height/heightOverWidthRatio);
                }else{
                    //height < width
                    width = this.widthBounds;
                    height = (int)((double)width*heightOverWidthRatio);
                }
                
                Rectangle returnRect = new Rectangle(x, y, width, height);
                return returnRect;
            }if(this.shape instanceof Ellipse2D.Double){
                double heightOverWidthRatio = this.shape.getBounds().getHeight()/
                                                this.shape.getBounds().getWidth();
                
                double width, height;
                
                if(heightOverWidthRatio == 1.0){
                    //height == width
                    width = (double)this.widthBounds;
                    height = (double)this.heightBounds;
                }else if(heightOverWidthRatio > 1.0){
                    //height > width
                    height = (double)this.heightBounds;
                    width = (height/heightOverWidthRatio);
                }else{
                    //height < width
                    width = (double)this.widthBounds;
                    height = width*heightOverWidthRatio;
                }
                Ellipse2D.Double returnEllipse = new Ellipse2D.Double((double)x, (double)y, width, height);
                return returnEllipse;
            }else{     
                //add support for polygons here
                return null;
            }    
            
        }
        
        public void setShape(Shape s){
            this.shape = s;
        }

        @Override
        public int getIconWidth() {
            return this.widthBounds;
        }

        @Override
        public int getIconHeight() {
            return this.heightBounds;
        }
        
    }
    
}
