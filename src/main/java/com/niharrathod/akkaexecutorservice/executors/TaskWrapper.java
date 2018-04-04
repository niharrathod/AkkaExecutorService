package com.niharrathod.akkaexecutorservice.executors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

public class TaskWrapper {

    private static final Logger logger = LoggerFactory.getLogger(TaskWrapper.class);

    ITask task;

    public TaskWrapper(ITask task) {
        this.task = task;
    }

    public <Request> TaskWrapper(Consumer<Request> consumer, Request request) {
        task = new RequestOnlyTask(consumer, request);
    }

    public <Request, Response> TaskWrapper(Function<Request, Response> function,
                                           Request request,
                                           BlockingQueue<Response> resultQueue) {
        task = new RequestWithQueuedResponseTask(function, request, resultQueue);
    }

    public void execute() {
        try {
            task.execute();
        } catch (Exception e) {
            logger.error("Exception while executing", e);
        }
    }
}
