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
public class StringScan {
    private String subject;
    private int pos;
    
    public StringScan(String s){
        pos = 0;
        subject = s;
    }
    
    public boolean Any(String str){
        int i = pos;
        CSet cs = new CSet(str);
        if(cs.memberOf(subject.charAt(pos))) return true;
        return false;
    }
    
    public boolean Any(char c){
        int i = pos;
        CSet cs = new CSet(c);
        if(cs.memberOf(subject.charAt(pos))) return true;
        return false;
    }
    
    public boolean Any(char[] chars){
        int i = pos;
        CSet cs = new CSet(chars);
        if(cs.memberOf(subject.charAt(pos))) return true;
        return false;
    }
    
    public boolean Any(CSet cs){
        int i = pos;
        if(cs.memberOf(subject.charAt(pos))) return true;
        return false;
    }
    
    public int getPos(){
        return pos;
    }
    
    public int Find(String str) {
        int i = subject.indexOf(str, pos);
        if(i >= 0) {
            return i;
        } else {
            return pos;
        }
    }
    
    public int Many(String str){
        int i = pos;
        CSet cs = new CSet(str);
        while(cs.memberOf(subject.charAt(i))) i++;
        return i;
    }
    
    public int Many(char ch){
        int i = pos;
        CSet cs = new CSet();
        cs.add(ch);
        while(cs.memberOf(subject.charAt(i))) i++;
        return i;
    }
    
    public int Many(char[] chars){
        int i = pos;
        CSet cs = new CSet(chars);
        while(cs.memberOf(subject.charAt(i))) i++;
        return i;
    }
    
    public int Many(CSet cs){
        int i = pos;
        while(cs.memberOf(subject.charAt(i))) i++;
        return i;
    }
    
    public int Match(String str){
        int i = subject.indexOf(str, pos);
        if(i == pos) {
            return pos + str.length();
        } else {
            return pos;
        }
    }
    
    public String Move(int i){
        String result = "";
        if(pos + i < subject.length()){
            result = subject.substring(pos, pos + i);
            pos += i;
        }
        return result;
    }
   
    public void Pos(int p){
        if(p > 0) pos = p - 1;
        else pos = subject.length() + p;
    }
    
    public String PatternMatch(Pattern pattern) {
        String result = "";
        MatchResult match = pattern.match(subject, pos);
        result = match.getResult().toString();
        pos = match.getPos();
        return result;
    }
    
    public String Tab(int p){
        String result = "";
        if(p > 0){
            if(p >= pos){
                result = subject.substring(pos, p - 1);
                pos = p;
            } 
        } else {
            if((subject.length() - p) >= pos){
                result = subject.substring(pos, subject.length() + p);
                pos = subject.length() - p;
            } 
        }
        return result;
    }
    
    public int Upto(String str){
        CSet cs = new CSet(str);
        for(int i = pos; i < subject.length(); i++){
            if(cs.memberOf(subject.charAt(i))) return pos;
        }
        return subject.length();
    }
    
    public int Upto(char c){
        CSet cs = new CSet();
        cs.add(c);
        for(int i = pos; i < subject.length(); i++){
            if(cs.memberOf(subject.charAt(i))) {
                return i;
            }
        }
        return subject.length();
    }
    
    public int Upto(char[] chars){
        CSet cs = new CSet(chars);
        for(int i = pos; i < subject.length(); i++){
            if(cs.memberOf(subject.charAt(i))) return pos;
        }
        return subject.length();
    }
    
    public int Upto(CSet cs){
        for(int i = pos; i < subject.length(); i++){
            if(cs.memberOf(subject.charAt(i))) return pos;
        }
        return subject.length();
    }
}
