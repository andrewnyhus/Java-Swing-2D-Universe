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
package java2dscrollinguniverse.Model;

import java.awt.Point;


/**
 *
 * @author andrewnyhus
 */
public class TwoDRectangle {
    
    private final Point topLeftOrigin;
    private final int width;
    private final int height;
    
    /**
     * Constructor where origin is assumed to be (0, 0)
     * @param width
     * @param height 
     */
    public TwoDRectangle(int width, int height){
        this.topLeftOrigin = new Point(0, 0);
        this.width = width;
        this.height = height;
    }
    
    /**
     * Constructor that takes in the origin (loc), the width and height.
     * @param loc
     * @param width
     * @param height 
     */
    public TwoDRectangle(Point loc, int width, int height){
        this.topLeftOrigin = loc;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Getter method for width.
     * @return width
     */
    public int getWidth(){
        return this.width;
    }
    
    /**
     * Getter method for height 
     * @return height
     */
    public int getHeight(){
        return this.height;
    }
    
    /*public Point getLocationOfCorner(Corner corner){

        int originX = this.topLeftOrigin.x;
        int originY = this.topLeftOrigin.y;
        
        if(corner.getValue() == corner.bottomLeft.getValue()){
            return new Point(originX, originY + this.getHeight());
        }else if(corner.getValue() == corner.bottomRight.getValue()){
            return new Point(originX + this.getWidth(), originY + this.getHeight());
        }else if(corner.getValue() == corner.topLeft.getValue()){
            return this.topLeftOrigin;
        }else if(corner.getValue() == corner.topRight.getValue()){
            return new Point(originX + this.getWidth(), originY);
        }else{
            return this.topLeftOrigin;            
        }
        
    }
    
    
    public enum Corner{
        topLeft(0), topRight(1), bottomRight(2), bottomLeft(3);
        
        //denotes which corner 'this' is
        private int num;
        
        private Corner(int num){
            this.num = num;
        }
        
        public int getValue(){
            return this.num;
        }
        
    }*/
    
}
