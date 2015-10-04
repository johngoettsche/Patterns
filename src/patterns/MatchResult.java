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
public class MatchResult {
    private int pos;
    private String subString;

    public MatchResult(){
        
    }
    
    public MatchResult(int p, String s){
        pos = p;
        subString = s;
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public int getPos() {
        return pos;
    }

    public String getSubString() {
        return subString;
    }
}
