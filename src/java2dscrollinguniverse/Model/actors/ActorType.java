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
package java2dscrollinguniverse.Model.actors;

/**
 *
 * @author andrewnyhus
 */
public enum ActorType {

    /**
     *Different values that correspond to
     */
    miscObject(0), wall(1), player(2), HUDElement(3);
    
    private int value;
    
    private ActorType(int value){
        this.value = value;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public boolean positionShouldBeChangeableWithinGUI(){
        return (this.value == 0 || this.value == 1);
    }
    
}
