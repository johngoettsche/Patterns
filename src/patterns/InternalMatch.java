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
public class InternalMatch {
    private String subject;
    private int pos;
    private Pattern pattern;
    
    public InternalMatch(String s, int p, Pattern pat){
        subject = s;
        pos = p;
        pattern = pat;
    }

    public String getSubject() {
        return subject;
    }

    public int getPos() {
        return pos;
    }

    public Pattern getPattern() {
        return pattern;
    }
    
    
}
