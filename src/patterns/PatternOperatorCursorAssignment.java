/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

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
        PatternElemVariable variableElem = (PatternElemVariable)this.getDefinition().getNextNext();
        variable = variableElem.getVariable().toString();
    }

    @Override
    public MatchResult evaluate(String subject, int pos) {
        patternVariableMap.setPatternVariable(variable, pos);
        this.setResult(new MatchResult(pos, pos, true));
        return this.getResult();
    }  
}
