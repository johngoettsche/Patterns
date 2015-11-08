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
public class InternalMatchStateReturnValue implements InternalMatchState{
    InternalMatch internalMatch;
    
    public InternalMatchStateReturnValue(InternalMatch im){
        internalMatch = im;
    }

    @Override
    public void hasElements() throws StringAnalysisException {
        internalMatch.setState(internalMatch.getPatternFunction);
        try {
            internalMatch.getState().hasElements();
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void noElements() throws StringAnalysisException {
        if(internalMatch.getInternalResult().isSuccess()) {
            String newSubString = (String)internalMatch.getPatternMatch().getMatchResult().getResult() + //.getSubString() + 
                    (String)internalMatch.getInternalResult().getResult(); //.getSubString();
            internalMatch.getPatternMatch().getMatchResult().setResult(newSubString); //.setSubString(newSubString);
            internalMatch.getPatternMatch().getMatchResult().setPos(internalMatch.getInternalResult().getPos());
            internalMatch.getPatternMatch().getMatchResult().setSuccess(true);
        } else {
            internalMatch.getPatternMatch().getMatchResult().setPos(internalMatch.getPatternMatch().getMatchResult().getPos() + 1);
            internalMatch.getPatternMatch().getMatchResult().setSuccess(false);
        }
    }
    
}
