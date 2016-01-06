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
public class TwoDimensionalMovement {
    private final int xMovement;
    private final int yMovement;
    
    public TwoDimensionalMovement(int x, int y){
        this.xMovement = x;
        this.yMovement = y;
    }
    
    public int getXMovement(){
        return this.xMovement;
    }
    
    public int getYMovement(){
        return this.yMovement;
    }
    
    public Point getPointWithMovementAppliedFromPoint(Point p){
        return new Point(this.xMovement + p.x, this.yMovement + p.y);
    }
    
    public TwoDimensionalMovement getMovementDividedByFactor(int factor){
        int xMovementNew = this.xMovement / factor;
        int yMovementNew = this.yMovement / factor;
        
        return new TwoDimensionalMovement(xMovementNew, yMovementNew);        
    }
    
}
