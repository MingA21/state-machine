package org.ming.v1.m1.n2;

import lombok.Data;

public enum State {

    RAIN,
    SUNNY,
    THURSDAY
}

interface IState {
    void handle();
}

class RainState  implements IState{

    @Override
    public void handle() {
        System.out.println("今天下雨， 坐公交车上班吧");
    }
}



@Data
class Worker {

    private String name;

    private State state;

    private IState iState;

    public void oneDay () {
        System.out.println("9：起床");
        if (state == State.RAIN) {
            System.out.println("今天下雨， 坐公交车上班吧");
        } else if (state == State.SUNNY) {
            System.out.println("晴天，骑车上班");
        } else  if (state == State.THURSDAY) {
            System.out.println("今天周四，吃顿肯德基");
        }
        System.out.println("21：下班");
    }

    public void oneDay4MachineModel () {
        System.out.println("9：起床");
        iState.handle();
        System.out.println("21：下班");
    }


    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.setName("张三");
        worker.setState(State.SUNNY);
        worker.oneDay();
        System.out.println("===========");
        worker.setIState(new RainState());
        worker.oneDay4MachineModel();
    }

}

