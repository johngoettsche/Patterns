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
public class PatternGlobals {
    CSets csets = new CSets();
    
    public PatternGlobals(){
        
    }
    
    public int findClosingSymbol(String subject, String startingSymbol, int left){
        String closingSymbol = "";
        //System.out.println(subject + " : " + startingSymbol + " : " + left);
        if(startingSymbol.equals("'") || startingSymbol.equals("`")) closingSymbol = startingSymbol;
        else if(startingSymbol.equals("(")) closingSymbol = ")";
        else if(startingSymbol.equals("[")) closingSymbol = "]";
        else if(startingSymbol.equals("{")) closingSymbol = "}";
        //System.out.println("Closing Symbol: " + closingSymbol);
        int count = 1;
        for(int pos = left + 1; pos < subject.length(); pos++){
            if(String.valueOf(subject.charAt(pos)).equals(closingSymbol)) count--;
            else if(String.valueOf(subject.charAt(pos)).equals(startingSymbol)) count++;
            //System.out.println(count);
            if(count == 0) {
                //System.out.println(left + " : " + pos);
                return pos;
            }
        }
        return -1;
    }
    
    public int findOperator(String args){
        int alt = args.indexOf("|");
        int con = args.indexOf("+");
        if(alt < con) return alt;
        else return con;
    }
    
    public boolean isInteger(String st){
            if(st.matches("[0-9]")){
                return true;
            } else {
                return false;
            }
    }
    
    public boolean memberOfCSet(char c, char[] cset){
        for(int i = 0; i < cset.length; i++){
            if(c == cset[i]) return true;
        }
        return false;
    }
    
    public int beginCSet(String subject, int pos, String cset){
        for(int i = 0; i < cset.length(); i++) {
            if(subject.charAt(pos) == cset.charAt(i)) return pos;
        }
        pos++;
        return -1;
    }
    
    public boolean atCSet(String subject, int pos, String cset){
        for(int i = pos; i < cset.length(); i++) {
            if(subject.charAt(pos) == cset.charAt(i)) return true;
        }
        return false;
    }
    
    public int endCSet(String subject, int pos, String cset){
        int p = pos;
        while(p < subject.length()) {
            boolean success = false;
            for(int i = 0; i < cset.length(); i++) {
                if(subject.charAt(p) == cset.charAt(i)) success = true;
            }
            if(!success)return p;
            p++;
        }
        return subject.length() + 1;
    }
    
    public int endLetters(String subject, int pos) {
        for(int i = pos; i < subject.length(); i++){
            if(!memberOfCSet(subject.charAt(i), csets.letters)) return i;
        }
        return subject.length();
    }
}
