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
public class PatternOperatorConditionalAssignment extends PatternOperator {
    private PatternVariableMap patternVariableMap;
    private String variable;

    public PatternOperatorConditionalAssignment(PatternDefinitionIterator def) {
        patternVariableMap = PatternVariableMap.getInstance();
        this.setDefinition(def);
        PatternElemVariable variableElem = (PatternElemVariable)this.getDefinition().getNextNext();
        variable = variableElem.getVariable().toString();
    }

    @Override
    public MatchResult evaluate(String subject, int pos) {
        this.setResult(new MatchResult(pos, 
                this.getDefinition().getPrevious().getResult().getResult(), 
                this.getDefinition().getPrevious().getResult().isSuccess()));
        return this.getResult();
    }
    
    public void onMatch() {
        patternVariableMap.setPatternVariable(variable, 
                this.getDefinition().getPrevious().getResult().getResult());
    }
}
