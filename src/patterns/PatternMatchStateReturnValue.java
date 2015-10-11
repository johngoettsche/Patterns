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
public class PatternMatchStateReturnValue implements PatternMatchState {
    PatternMatch patternMatch;

    public PatternMatchStateReturnValue(PatternMatch patternMatch) {
        this.patternMatch = patternMatch;
    }

    @Override
    public void endOfSubject() {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notEndOfSubject() {
        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }
    
    
}
