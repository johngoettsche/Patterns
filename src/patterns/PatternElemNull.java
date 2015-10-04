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
public class PatternElemNull extends PatternElem{
    public PatternElemNull(){
        setArguments(new ArrayList());
        setElementName("Pattern Element Null");
    }

    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult(0, "");
        return matchResult;
    }
}
