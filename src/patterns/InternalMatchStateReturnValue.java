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
public class InternalMatchStateReturnValue implements InternalMatchState{
    InternalMatch internalMatch;
    
    public InternalMatchStateReturnValue(InternalMatch im){
        internalMatch = im;
    }

    @Override
    public void hasElements() throws PatternException {
        internalMatch.setState(internalMatch.getPatternFunction);
        try {
            internalMatch.getState().hasElements();
        } catch (PatternException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void noElements() throws PatternException {
        if(internalMatch.getInternalResult().isSuccess()) {
            String newSubString = internalMatch.getPatternMatch().getMatchResult().getSubString() + internalMatch.getInternalResult().getSubString();
            internalMatch.getPatternMatch().getMatchResult().setSubString(newSubString);
            internalMatch.getPatternMatch().getMatchResult().setPos(internalMatch.getInternalResult().getPos());
            internalMatch.getPatternMatch().getMatchResult().setSuccess(true);
        } else {
            internalMatch.getPatternMatch().getMatchResult().setPos(internalMatch.getPatternMatch().getMatchResult().getPos() + 1);
            internalMatch.getPatternMatch().getMatchResult().setSuccess(false);
        }
    }
    
}
