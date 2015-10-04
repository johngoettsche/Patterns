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
public class PatternMatch {
    public String subject;
    public Pattern pattern;
    public int pos;
    
    public PatternMatch(String s, Pattern pat){
        subject = s;
        pattern = pat;
        pos = 0;
    }

    public String getSubject() {
        return subject;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getPos() {
        return pos;
    }
    
    public String match(){
        String result = "";
        
        return result;
    }
}
