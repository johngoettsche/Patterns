/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John H. Goettsche
 */
public class PatternDefinitionIterator {
    List<PatternElem> definition;
    int position;
    PatternElem nullElem = new PatternElemNull();

    public PatternDefinitionIterator() {
        definition = new ArrayList();
        position = 0;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void start() {
        position = 0;
    }
    
    public boolean hasNext() {
        if(position >= definition.size() || 
                definition.get(position) == null
                ){
            return false;
        } else {
            return true;
        }
    }
    
    public void next() {
        position++;
    }
    
    public PatternElem current() {
        if(position < definition.size()) return definition.get(position);
        else return nullElem;
    }
    
    public boolean hasPrevious() {
        if(position == 0){
            return false;
        } else {
            return true;
        }
    }
    
    public PatternElem getNextNext() {
        if(position + 1 >= definition.size() || 
                definition.get(position) == null
                ){
            return nullElem;
        } else {
            return definition.get(position + 1);
        }
    }
    
    public PatternElem getPrevious() {
        if (hasPrevious()) {
            return definition.get(position - 1);
        } else {
            return nullElem;
        }
    }
    
    public void add(PatternElem newElement){
        definition.add(newElement);
    }
    
    public int size() {
        return definition.size();
    }
}
