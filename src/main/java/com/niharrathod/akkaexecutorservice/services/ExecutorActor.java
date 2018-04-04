package com.niharrathod.akkaexecutorservice.services;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.niharrathod.akkaexecutorservice.executors.TaskWrapper;

public class ExecutorActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TaskWrapper.class, this::onExecutor)
                .build();
    }

    public void onExecutor(TaskWrapper taskWrapper) {
        taskWrapper.execute();
    }

    public static Props props() {
        return Props.create(ExecutorActor.class);
    }
}
