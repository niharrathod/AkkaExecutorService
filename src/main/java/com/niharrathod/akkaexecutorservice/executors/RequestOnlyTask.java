package com.niharrathod.akkaexecutorservice.executors;


import java.util.function.Consumer;

public class RequestOnlyTask<Request> implements ITask {

    Request request;
    Consumer<Request> consumer;

    public RequestOnlyTask(Consumer<Request> consumer, Request request) {
        this.request = request;
        this.consumer = consumer;
    }

    @Override
    public final void execute() {
        this.consumer.accept(request);
    }

}
