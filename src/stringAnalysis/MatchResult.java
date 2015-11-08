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
public class MatchResult {
    private int pos;
    private Object result;
    private boolean success;

    public MatchResult(){
        
    }
    
    public MatchResult(int p, Object s, boolean suc){
        pos = p;
        result = s;
        success = suc;
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public int getPos() {
        return pos;
    }

    public Object getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }
}
