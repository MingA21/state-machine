package org.ming.v1.m2.n3;

import com.google.common.collect.HashBasedTable;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

enum Event {

    SUBMIT,

    PASS,

    REJECT;
}

public enum State {

    REJECT,

    FINISH,

    UN_SUBMIT,

    LEADER_CHECK,

    HR_CHECK ;



}
class StateMachine {

    private static HashBasedTable<State, Event, State> hashBasedTable = HashBasedTable.create();


    static {
        hashBasedTable.put(State.UN_SUBMIT, Event.SUBMIT, State.LEADER_CHECK);
        hashBasedTable.put(State.LEADER_CHECK, Event.PASS, State.HR_CHECK);
        hashBasedTable.put(State.UN_SUBMIT, Event.REJECT, State.REJECT);
        hashBasedTable.put(State.HR_CHECK, Event.PASS, State.FINISH);
        hashBasedTable.put(State.UN_SUBMIT, Event.REJECT, State.REJECT);
    }


    public static State getNext(State state, Event event) {
        State result = hashBasedTable.get(state, event);
        if (result == null) {
            throw new IllegalArgumentException("未找到状态");
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(StateMachine.getNext(State.LEADER_CHECK, Event.PASS));
    }

}



