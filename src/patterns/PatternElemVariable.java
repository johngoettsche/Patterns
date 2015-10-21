/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.ArrayList;

/**
 *
 * @author John H. Goettsche
 */
public class PatternElemVariable extends PatternElem {
    PatternVariableMap patternVariableMap;
    
    public PatternElemVariable(String st) {
        patternVariableMap = PatternVariableMap.getInstance();
        patternVariableMap.setPatternVariable(st, null);
        setCharSet(st);
        setArguments(new ArrayList());
        setElementName("Pattern Elem Variable " + st);
    }
    
    public MatchResult evaluate(String subject, int pos) {
        /* find the variable or declare it 
         * then return its value or pointer.
         *
         * at this point it assumes this is a local
         * pattern variable.
         */
        
        MatchResult result = new MatchResult();
        Object value = patternVariableMap.getPatternVariable(this.getCharSet());
        result.setResult(value);
        /*if(value.getClass().equals(Integer.class)) {
            result.setIntValue((int)value);
        } else if(value.getClass().equals(String.class)) {
            result.setStringValue((String)value);
        }*/
        result.setSuccess(true);
        return result;
    }
    
    public void setVariable(Object val) {
        patternVariableMap.setPatternVariable(this.getCharSet(), val);
    }
}
