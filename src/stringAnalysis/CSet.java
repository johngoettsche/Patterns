/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John H. Goettsche
 */
public class CSet {
    char[] charSet;
    
    public CSet(){
        charSet = new char[0];
    }
    
    public CSet(char c) {
        charSet = new char[1];
        charSet[0] = c;
    }
    
    public CSet(char[] cs){
        charSet = cs;
        clean();
    }
    
    public CSet(String cs){
        charSet = cs.toCharArray();
        clean();
    }
    
    public char[] getCharSet(){
        return charSet;
    }
    
    public void add(char c){
        char[] newCharSet = new char[charSet.length + 1];
        System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
        newCharSet[newCharSet.length - 1] = c;
        charSet = newCharSet;
    }
    
    public void add(char[] cs){
        char[] newCharSet = new char[charSet.length + cs.length];
        System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
        System.arraycopy(cs, 0, newCharSet, charSet.length, cs.length);
        charSet = newCharSet;
    }
    
    public void add(CSet cs){
        char[] addedSet = cs.getCharSet();
        char[] newCharSet = new char[charSet.length + addedSet.length];
        System.arraycopy(charSet, 0, newCharSet, 0, charSet.length);
        System.arraycopy(cs, 0, newCharSet, charSet.length, addedSet.length);
        charSet = newCharSet;
    }
    
    public boolean memberOf(char c){
        for(int i = 0; i < charSet.length; i++){
            if(c == charSet[i]) {
                return true;
            }
        }
        return false;
    }
    
    private void clean(){
        CSet newSet = new CSet();
        for(int i = 0; i < charSet.length; i++){
            if(!newSet.memberOf(charSet[i])) {
                newSet.add(charSet[i]);
            }
        }
        charSet = newSet.getCharSet();
    }
}
