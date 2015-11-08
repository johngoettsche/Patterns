/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John H. Goettsche
 */
public class PatternMatchStateInternalMatch implements PatternMatchState {
    PatternMatch patternMatch;
    InternalMatch internalMatch;
    
    public PatternMatchStateInternalMatch(PatternMatch pm) {
        this.patternMatch = pm;
        internalMatch = new InternalMatch(patternMatch.getSubject(), patternMatch);
    }

    @Override
    public void endOfSubject() {
        patternMatch.setState(patternMatch.returnValue);
        patternMatch.getState().endOfSubject();
    }

    @Override
    public void notEndOfSubject() {
        InternalMatch internalMatch = new InternalMatch(patternMatch.getSubject(), patternMatch);
        if(internalMatch.getDefinition().hasNext()) {
            try {
                internalMatch.getState().hasElements();
            } catch (StringAnalysisException ex) {
                System.out.println(ex);
            }
            if(patternMatch.getPos() < patternMatch.getSubject().length()) {
                patternMatch.setState(patternMatch.posState);
                patternMatch.getState().notEndOfSubject();
            } else {
                patternMatch.setState(patternMatch.returnValue);
                patternMatch.getState().endOfSubject();
            }
        } else {
            patternMatch.setState(patternMatch.returnValue);
            patternMatch.getState().endOfSubject();
        }
    }
}
