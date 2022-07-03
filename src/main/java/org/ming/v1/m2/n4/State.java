package org.ming.v1.m2.n4;

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

    HR_CHECK;

}

interface IStateHandle<T, R> {
    R handle(T t);
}


class LeaderPassHandle implements IStateHandle<String, String> {

    @Override
    public String handle(String s) {
        System.out.println(String.format("收到了%s", s));
        return "业务处理完了";
    }
}


@Data
class SopProcess {

    private State from;
    private State to;
    private Event event;
    private IStateHandle stateHandle;

}

class SopProcessBuilder {

    private SopProcess sopProcess;

    public void setSopProcess(SopProcess sopProcess) {
        this.sopProcess = sopProcess;
    }

    public static SopProcessBuilder getInstance() {
        SopProcessBuilder sopBuilder = new SopProcessBuilder();
        sopBuilder.setSopProcess(new SopProcess());
        return sopBuilder;
    }

    public SopProcessBuilder from(State state) {
        sopProcess.setFrom(state);
        return this;
    }

    public SopProcessBuilder handle(IStateHandle stateHandle) {
        sopProcess.setStateHandle(stateHandle);
        return this;
    }

    public SopProcessBuilder to(State state) {
        sopProcess.setTo(state);
        return this;
    }

    public SopProcessBuilder event(Event state) {
        sopProcess.setEvent(state);
        return this;
    }

    public SopProcess build() {
        return sopProcess;
    }
}

abstract class AbstractStateMachine {

    @Data
    static class SopExec {
        private State nextState;
        private IStateHandle stateHandle;
    }

    private HashBasedTable<State, Event, SopExec> hashBasedTable = HashBasedTable.create();

    {
        List<SopProcess> sopProcesses = init();
        sopProcesses.forEach(item -> {
            SopExec sopExec = new SopExec();
            sopExec.setNextState(item.getTo());
            sopExec.setStateHandle(item.getStateHandle());
            hashBasedTable.put(item.getFrom(), item.getEvent(), sopExec);
        });
    }

    abstract List<SopProcess> init();

    public State getNext(State state, Event event) {
        SopExec result = hashBasedTable.get(state, event);
        if (result == null) {
            throw new IllegalArgumentException("未找到状态");
        }
        return result.getNextState();
    }

    public IStateHandle getHandle(State state, Event event) {
        SopExec result = hashBasedTable.get(state, event);
        if (result == null) {
            throw new IllegalArgumentException("未找到状态");
        }
        return result.getStateHandle();
    }
}

class NewStateMachine extends AbstractStateMachine {


    @Override
    List<SopProcess> init() {
        return Arrays.asList(
                SopProcessBuilder.getInstance()
                        .from(State.UN_SUBMIT)
                        .event(Event.SUBMIT)
                        .to(State.LEADER_CHECK)
                        .build(),
                SopProcessBuilder.getInstance()
                        .from(State.LEADER_CHECK)
                        .event(Event.PASS)
                        .handle(new LeaderPassHandle())
                        .to(State.HR_CHECK)
                        .build()
        );
    }

    public static void main(String[] args) {
        NewStateMachine newStateMachine = new NewStateMachine();
        State state = newStateMachine.getNext(State.LEADER_CHECK, Event.PASS);
        System.out.println(state);
        IStateHandle<String, String> stateHandle = newStateMachine.getHandle(State.LEADER_CHECK, Event.PASS);
        String result = stateHandle.handle("入参内容");
        System.out.println(result);
    }
}




