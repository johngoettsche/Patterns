/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

import java.util.List;

/**
 *
 * @author John H. Goettsche
 */
public abstract class PatternFunction extends PatternElem{

    @Override
    public abstract MatchResult evaluate(String subject, int pos);

    @Override
    public abstract void onMatch();
}
