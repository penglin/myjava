package enums;

import java.util.EnumMap;
import java.util.EnumSet;

public class EnumTest {  
    // 定义一个enum枚举类型，包括两个实例ON，OFF  
    public enum State {  
        ON, OFF  
    };  
    // 测试函数  
    public static void main(String[] args) {  
        // 直接变量enum  
        for (State s : State.values())  
            System.out.println(s.name());  
        // switch与enum的结合使用  
        State switchState = State.OFF;  
        switch (switchState) {  
        case OFF:  
            System.out.println("OFF");  
            break;  
        case ON:  
            System.out.println("ON");  
            break;  
        }  
        // EnumSet的使用  
        EnumSet<State> stateSet = EnumSet.allOf(State.class);  
        for (State s : stateSet) {  
            System.out.println(s);  
        }  
        // EnumMap的使用  
        EnumMap<State,String> stateMap = new EnumMap<State,String>(State.class);  
        stateMap.put(State.ON, "is On");  
        stateMap.put(State.OFF, "is off");  
        for (State s : State.values()) {  
            System.out.println(s.name() + ":" + stateMap.get(s));  
        }  
    }  
} 