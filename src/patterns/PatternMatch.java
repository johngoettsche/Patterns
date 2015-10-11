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
public class PatternMatch {
    private PatternDefinitionIterator definition;
    private String subject;
    private int pos;
    private MatchResult matchResult;
    
    PatternMatchState posState;
    PatternMatchState internalMatch;
    PatternMatchState returnValue;
    
    PatternMatchState state;
    
    public PatternMatch(String s, PatternDefinitionIterator def, int p){
        this.definition = def;
        this.subject = s;
        this.pos = p;
        
        posState = new PatternMatchStatePos(this);
        matchResult = new MatchResult();
        matchResult.setPos(p);
        matchResult.setSuccess(false);
        internalMatch = new PatternMatchStateInternalMatch(this);
        
        state = posState;
    }
    
    public void setState(PatternMatchState newState){
        state = newState;
    }

    public void setPattern(PatternDefinitionIterator def) {
        this.definition = def;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setMatchResult(MatchResult matchResult) {
        this.matchResult = matchResult;
    }
    
    public PatternMatchState getState(){
        return state;
    }

    public PatternDefinitionIterator getDefinition() {
        return definition;
    }

    public String getSubject() {
        return subject;
    }

    public int getPos() {
        return pos;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }
    
}
