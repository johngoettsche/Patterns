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
    //eliminate the following and use result object
    //private String subString;
    //private int intValue;
    //private String stringValue;
    //eliminate the above and use result object
    // look at PatternElemVariable to see how to identify object type
    private Object result;
    private boolean success;

    public MatchResult(){
        
    }
    
    public MatchResult(int p, String s){
        pos = p;
        //subString = s;
        result = s;
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setResult(Object result) {
        this.result = result;
    }
/*
    public void setSubString(String subString) {
        this.subString = subString;
    }*/

    public void setSuccess(boolean success) {
        this.success = success;
    }
/*
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }*/
/*
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }*/
    
    public int getPos() {
        return pos;
    }

    public Object getResult() {
        return result;
    }
/*
    public String getSubString() {
        return subString;
    }*/

    public boolean isSuccess() {
        return success;
    }
/*
    public int getIntValue() {
        return intValue;
    }*/
/*
    public String getStringValue() {
        return stringValue;
    }*/
}
