/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author John H. Goettsche
 */
public class PatternVariableMap {
    private volatile static PatternVariableMap uniqueInstance;
    Map patternVariableMap;
    
    private PatternVariableMap() {
        patternVariableMap = new HashMap();
    }
    
    public static PatternVariableMap getInstance() {
        if (uniqueInstance == null) {
            synchronized (PatternVariableMap.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new PatternVariableMap();
                }
            }
        }
        return uniqueInstance;
    }

    public Map getPatternVariableMap(){
        return patternVariableMap;
    }
    
    public void putPatternVariable(String var, Object val) throws PatternException {
        if(patternVariableMap.containsKey(var)) { 
            throw new PatternException("Variable already exists in Pattern Variable Map.");
        } else {
            patternVariableMap.put(var, val);
        }
    }
    
    public void setPatternVariable(String var, Object val) {
        if(patternVariableMap.containsKey(var)){
            patternVariableMap.remove(var);
            patternVariableMap.put(var, val);
        } else {
            patternVariableMap.put(var, val);
        }
    }
    
    public Object getPatternVariable(String var){
        return patternVariableMap.get(var);
    }
}
