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
public class PatternOperatorCursorAssignment extends PatternOperator {
    private PatternVariableMap patternVariableMap;
    private String variable;

    public PatternOperatorCursorAssignment(PatternDefinitionIterator def) {
        patternVariableMap = PatternVariableMap.getInstance();
        this.setDefinition(def);  
        setArguments(new ArrayList());
        setElementName("Pattern Operator .>");
    }

    @Override
    public MatchResult evaluate(String subject, int pos) {
        PatternElemVariable variableElem = (PatternElemVariable)this.getDefinition().getNextNext();
        variable = variableElem.getCharSet();
        patternVariableMap.setPatternVariable(variable, pos);
        this.setResult(new MatchResult(pos, "", true));
        return this.getResult();
    }  
    
    @Override
    public void onMatch(){}
}
