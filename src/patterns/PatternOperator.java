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
public abstract class PatternOperator extends PatternElem{
    private PatternDefinitionIterator definition;
    private PatternElem nullElem = new PatternElemNull();

    public PatternDefinitionIterator getDefinition() {
        return definition;
    }

    public void setDefinition(PatternDefinitionIterator def) {
        this.definition = def;
    }
    
    public PatternElem getPrevious() {
        if(definition.hasPrevious()){
            return definition.getPrevious();
        }
        return nullElem;
    }
    
    public PatternElem getNextElem() {
        return definition.getNextNext();
    }
}
