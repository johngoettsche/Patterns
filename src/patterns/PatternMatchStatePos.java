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
public class PatternMatchStatePos implements PatternMatchState {
    PatternMatch patternMatch;
    
    public PatternMatchStatePos(PatternMatch pm){
        this.patternMatch = pm;
    }

    @Override
    public void endOfSubject() {
        patternMatch.setState(patternMatch.returnValue);
        patternMatch.getState().endOfSubject();
    }

    @Override
    public void notEndOfSubject() {
        if(patternMatch.getPos() < patternMatch.getSubject().length()) {
            patternMatch.setState(patternMatch.internalMatch);
            patternMatch.getState().notEndOfSubject();
        } else {
            patternMatch.setState(patternMatch.returnValue);
            patternMatch.getState().endOfSubject();
        }
    }
    
    
}
