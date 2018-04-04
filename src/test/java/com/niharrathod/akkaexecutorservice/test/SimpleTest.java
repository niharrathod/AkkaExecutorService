package com.niharrathod.akkaexecutorservice.test;

import com.niharrathod.akkaexecutorservice.executors.ITask;
import com.niharrathod.akkaexecutorservice.services.AkkaExecutorService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTest {
    Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @Test
    public void simpleTest() {

        /*
         *   An ITask interface which does not take any request and does not give any response.
         *   AkkaExecutorService takes task implementation,
         *   and run it in async using akka actor system.
         * */
        ITask task = new ITask() {
            @Override
            public void execute() {
                logger.info("Simple task");
            }
        };

        AkkaExecutorService akkaExecutorService = new AkkaExecutorService(5);

        akkaExecutorService.execute(task);
    }
}
