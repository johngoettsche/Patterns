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
public class InternalMatchStateGetPatternFunction implements InternalMatchState {
    InternalMatch internalMatch;

    public InternalMatchStateGetPatternFunction(InternalMatch im) {
        internalMatch = im;
    }

    @Override
    public void hasElements() throws PatternException{
        if(internalMatch.getDefinition().hasNext()) {
            internalMatch.setCurrentElement(internalMatch.getDefinition().current());
            internalMatch.getDefinition().next();
            internalMatch.setState(internalMatch.evaluation);
            try {
            internalMatch.getState().hasElements();
            } catch (PatternException ex) {
                System.out.println(ex);
            }
        } else {
            try {
                this.noElements();
            } catch (PatternException ex) {
                System.out.println(ex);
            }
        }
    }

    @Override
    public void noElements() throws PatternException {
        internalMatch.setState(internalMatch.returnValue);
        try {
            internalMatch.getState().noElements();
        } catch (PatternException ex) {
            System.out.println(ex);
        }
    }
}
