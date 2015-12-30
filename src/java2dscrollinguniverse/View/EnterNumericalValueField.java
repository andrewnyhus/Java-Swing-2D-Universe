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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author andrewnyhus
 */
public class EnterNumericalValueField extends JTextField{
    
    private final NumericalType type;
    private String initString;
    public EnterNumericalValueField(int initialValue, DocumentListener listener){
        this.type = NumericalType.INT;
        this.initString = "0";
        this.createAndSetDocument(listener);
    }
    
    public EnterNumericalValueField(double initialValue, DocumentListener listener){
        this.type = NumericalType.DOUBLE;
        this.initString = "0.0";
        this.createAndSetDocument(listener);
    }

    public EnterNumericalValueField(float initialValue, DocumentListener listener){
        this.type = NumericalType.FLOAT;
        this.initString = "0.0f";
        this.createAndSetDocument(listener);
    }
    
    private void createAndSetDocument(DocumentListener listener){
        SwingUtilities.invokeLater(() -> {
            NumericalDocument doc = new NumericalDocument(this, listener);
            this.setDocument(doc);
            this.setText(this.initString);
        });
    }
    
    public void paintRed(){
        this.setBackground(Color.red);
    }
    
    

    /**
     * @return the type
     */
    public NumericalType getType() {
        return this.type;
    }

    
    
    private enum NumericalType{
        INT(0), DOUBLE(1), FLOAT(2);
        private int value;
        
        private NumericalType(int value){
            this.value = value;
        }
        
        public int getValue(){
            return this.value;
        }
    
    }
    
    
    private static class NumericalDocument extends PlainDocument{
        
        private EnterNumericalValueField field;
        private int numOfFCharactersAllowed;
        private int numOfDecimalPointsAllowed;
        
        protected NumericalDocument(EnterNumericalValueField field, DocumentListener listener){
            this.field = field;
            
            if(this.field.getType().getValue() == NumericalType.INT.getValue()){
                this.numOfDecimalPointsAllowed = 0;
                this.numOfFCharactersAllowed = 0;
            }else if(this.field.getType() == NumericalType.DOUBLE){
                this.numOfDecimalPointsAllowed = 1;
                this.numOfFCharactersAllowed = 0;            
            }else if(this.field.getType() == NumericalType.FLOAT){
                this.numOfDecimalPointsAllowed = 1;
                this.numOfFCharactersAllowed = 1;
            }
            
            this.addDocumentListener(listener);
        }
        
        private String getCharactersInS1NotContainedInS2(String s1, String s2){
            String returnString = "";

            for(int i = 0; i < s1.length(); i++){
                char currentChar = s1.charAt(i);
                
                if(!s2.contains(String.valueOf(currentChar))){
                    returnString = returnString + String.valueOf(currentChar);
                }
            }
            return returnString;
        }
        
        private int getNumOfOccurrencesOfCharInString(char c, String s){
        
            int countOfOccurrences = 0;
            for(int i = 0; i < s.length(); i++){
                char currentChar = s.charAt(i);
                if(String.valueOf(c).equals(String.valueOf(currentChar))){
                    countOfOccurrences++;
                }
            }
            return countOfOccurrences;
        
        }
        
        
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{

            if(str == null){
                return;
            }
            
            String originalFieldText = this.field.getText(); 
            String numberString = "0123456789";
            String nonNumericalString = this.getCharactersInS1NotContainedInS2(str, numberString);

            int numOfUnknownCharacters = nonNumericalString.length();
            
            int numOfDecimalPoints = this.getNumOfOccurrencesOfCharInString('.', nonNumericalString);
            int numOfFCharacters = this.getNumOfOccurrencesOfCharInString('f', nonNumericalString);
            
            numOfUnknownCharacters -= (numOfDecimalPoints + numOfFCharacters);
            
            int preExistingDecimalPointsCount = this.getNumOfOccurrencesOfCharInString('.', originalFieldText);
            int preExistingFCharactersCount = this.getNumOfOccurrencesOfCharInString('f', originalFieldText);

            //if we are trying to add a string with more non numeric values outside of f and .
            //then we end this call without inserting the string
            if(numOfUnknownCharacters > 0)
                return;
            
            
            //if inserting the string would result in more decimal points than this field is allowed, 
            //then end the call without inserting the string
            if(preExistingDecimalPointsCount + numOfDecimalPoints > this.getNumOfDecimalPointsAllowed())
                return;

            //if inserting the string would result in more 'f' characters than this field is allowed,
            //then end the call without inserting the string
            if(preExistingFCharactersCount + numOfFCharacters > this.getNumOfFCharactersAllowed())
                return;
            
            if(this.getNumOfFCharactersAllowed() > 0){
                int indexOfFCharacter = originalFieldText.indexOf("f");
                if(indexOfFCharacter != -1 && offs > indexOfFCharacter){
                    return;
                }
            }
            
            
            super.insertString(offs, str, a);
        }
        

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            String currentTextFieldString = this.field.getText();
            String substringToBeRemoved = currentTextFieldString.substring(offs, offs+len);
            
            if(substringToBeRemoved.contains(".") || substringToBeRemoved.contains("f"))
                return;
            
            if(currentTextFieldString.length() - substringToBeRemoved.length() <= 0)
                return;
            
            
            super.remove(offs, len);
            
            
            
            if(this.field.getText().contains(".")){
                int indexOfDecimal = this.field.getText().indexOf(".");
                
                //if decimal is the first character, add a zero before it
                if(indexOfDecimal == 0){
                    this.insertString(0, "0", null);

                    //if decimal is last character, add a zero on the end
                }else if(indexOfDecimal + 1 == this.field.getText().length()){
                    this.insertString(indexOfDecimal+1, "0", null);
                    //this.field.setText(this.field.getText() + "0");
                    
                    //or if the field's text ends in '.f' insert a zero in between . and f
                }else if(this.field.getText().contains("f") &&
                        (this.field.getText().indexOf("f") - 1) == indexOfDecimal){
                    
                    int indexOfFCharacter = this.field.getText().indexOf("f");
                    
                    this.insertString(indexOfFCharacter, "0", null);
                }
                
                
                
            }
            
        }
        
        /**
         * @return the numOfNonNumericCharacters 
         */
        public int getNumOfNonNumericCharactersAllowed(){
            return (this.getNumOfDecimalPointsAllowed() + this.getNumOfFCharactersAllowed());
        }
        
        
        /**
         * @return the numOfFCharactersAllowed
         */
        public int getNumOfFCharactersAllowed() {
            return numOfFCharactersAllowed;
        }

        /**
         * @return the numOfDecimalPointsAllowed
         */
        public int getNumOfDecimalPointsAllowed() {
            return numOfDecimalPointsAllowed;
        }    
    
    }
    
    
}
