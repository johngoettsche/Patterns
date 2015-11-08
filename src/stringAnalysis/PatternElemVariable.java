/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

import java.util.ArrayList;

/**
 *
 * @author John H. Goettsche
 */
public class PatternElemVariable extends PatternElem {
    private PatternVariableMap patternVariableMap;
    private Object variable;
    
    public PatternElemVariable(String st) {
        patternVariableMap = PatternVariableMap.getInstance();
        if(!patternVariableMap.variableExists(st)) {
            try {
                patternVariableMap.putPatternVariable(st, null);
            } catch(StringAnalysisException ex){
                System.out.println(ex);
            }
        }
        variable = patternVariableMap.getPatternVariable(st);
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
        
        this.setResult(new MatchResult());
        Object value = patternVariableMap.getPatternVariable(this.getCharSet());
        this.setResult(new MatchResult(pos, value, true));
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}

    public Object getVariable() {
        return variable;
    }
    
    public void setVariable(Object val) {
        patternVariableMap.setPatternVariable(this.getCharSet(), val);
        variable = patternVariableMap.getPatternVariable(this.getCharSet());
    }
}
