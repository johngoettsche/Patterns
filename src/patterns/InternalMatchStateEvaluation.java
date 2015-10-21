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
public class InternalMatchStateEvaluation implements InternalMatchState {
    InternalMatch internalMatch;
    
    public InternalMatchStateEvaluation(InternalMatch im) {
        internalMatch = im;
    }

    @Override
    public void hasElements() throws PatternException{
        String matchString = "";

        internalMatch.getCurrentElement().setOldPos(internalMatch.getPatternMatch().getPos());
        internalMatch.getCurrentElement().setSubString((String)internalMatch.getInternalResult().getResult());  //.getSubString());
                //
        if(!internalMatch.getCurrentElement().getClass().equals(PatternOperatorAlternate.class)) { //not Alternate 
        if(internalMatch.getDefinition().hasPrevious()) { //not first element
            if (internalMatch.getDefinition().getPrevious().getClass().getSuperclass().equals(PatternElem.class) || 
                    internalMatch.getDefinition().getPrevious().getClass().getSuperclass().equals(PatternFunction.class) ||
                    internalMatch.getDefinition().getPrevious().getClass().getSuperclass().equals(PatternStructure.class)){
                            //proceded by function, structure, pattern, string
                if(internalMatch.getCurrentElement().getClass().getSuperclass().equals(PatternOperator.class)) {
                                //is an operator
                    internalMatch.getCurrentElement().setSubString(matchString);
                    internalMatch.setInternalResult(internalMatch.getCurrentElement().evaluate(internalMatch.getSubject(), internalMatch.getPatternMatch().getPos()));
                } else { // not an operator
                    throw new PatternException("Pattern Element or Pattern Function must be followed by a Pattern Operator.");
                }
            } else {//proceded by an operator
                internalMatch.setInternalResult(internalMatch.getCurrentElement().evaluate(internalMatch.getSubject(), internalMatch.getPatternMatch().getPos()));
                        }
            } else { //first element
                internalMatch.setInternalResult(internalMatch.getCurrentElement().evaluate(internalMatch.getSubject(), internalMatch.getPatternMatch().getPos()));
            }
        } else { //alternation.
            matchString = "";
            internalMatch.getDefinition().next();
            internalMatch.setState(internalMatch.getPatternFunction);
            internalMatch.getState().hasElements();
        } // end alternation
        
    }

    @Override
    public void noElements() {
        internalMatch.setState(internalMatch.returnValue);
        try {
            internalMatch.getState().noElements();
        } catch (PatternException ex) {
            System.out.println(ex);
        }
    }    
}
