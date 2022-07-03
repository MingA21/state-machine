package org.ming.v1.m2.n1;


public enum State {

    FINISH,

    UN_SUBMIT,

    LEADER_CHECK,

    HR_CHECK;


}

class Test {
    static State getNext(State state) {
        if (state == State.UN_SUBMIT) {
            return State.LEADER_CHECK;
        } else if (state == State.LEADER_CHECK) {
            return State.HR_CHECK;
        } else if (state == State.HR_CHECK) {
            return State.FINISH;
        }
        throw new IllegalArgumentException("非法状态");
    }

    public static void main(String[] args) {
        State state = State.UN_SUBMIT;
        System.out.println(getNext(state));
    }

}


