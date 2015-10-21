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
public class InternalMatch {
    private String subject;
    private PatternMatch patternMatch;
    private PatternDefinitionIterator definition;
    private MatchResult internalResult;
    
    InternalMatchState getPatternFunction;
    InternalMatchState evaluation;
    InternalMatchState returnValue;
    
    private PatternElem currentElement;
    private int elem;
    
    private InternalMatchState state;
    
    public InternalMatch(String s, PatternMatch pm){
        this.subject = s;
        this.patternMatch = pm;
        this.definition = this.patternMatch.getDefinition();
        this.internalResult = new MatchResult();
        this.internalResult.setPos(this.patternMatch.getPos());
        this.internalResult.setResult(""); //.setSubString("");
        
        getPatternFunction = new InternalMatchStateGetPatternFunction(this);
        evaluation = new InternalMatchStateEvaluation(this);
        returnValue = new InternalMatchStateReturnValue(this);
        
        state = getPatternFunction;
    }

    public PatternElem getCurrentElement() {
        return currentElement;
    }

    public PatternDefinitionIterator getDefinition() {
        return definition;
    }

    public int getElem() {
        return elem;
    }

    public PatternMatch getPatternMatch() {
        return patternMatch;
    }

    public MatchResult getInternalResult() {
        return internalResult;
    }
    

    public InternalMatchState getState() {
        return state;
    }

    public String getSubject() {
        return subject;
    }    

    public void setCurrentElement(PatternElem currentElement) {
        this.currentElement = currentElement;
    }

    public void setElem(int elem) {
        this.elem = elem;
    }
    
    public void setState(InternalMatchState state) {
        this.state = state;
    }

    public void setInternalResult(MatchResult internalResult) {
        this.internalResult = internalResult;
    }
    
}
