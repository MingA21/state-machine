package org.ming.v1.m2.n2;

public enum State {


    FINISH {
        @Override
        State getNext() {
            return this;
        }
    },

    UN_SUBMIT {
        @Override
        State getNext() {
            return LEADER_CHECK;
        }
    },

    LEADER_CHECK {
        @Override
        State getNext() {
            return HR_CHECK;
        }
    },

    HR_CHECK {
        @Override
        State getNext() {
            return FINISH;
        }
    };


    abstract State getNext();

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
        System.out.println(state.getNext());
    }

}


