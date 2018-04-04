package com.niharrathod.akkaexecutorservice.test;

import com.niharrathod.akkaexecutorservice.services.AkkaExecutorService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class RequestOnlyTask {

    Logger logger = LoggerFactory.getLogger(RequestOnlyTask.class);

    @Test
    public void test() {

        /*
         *   Consumer which takes argument but does not return any thing.
         *   AkkaExecutorService takes the request and consumer implementation,
         *   Internally it passes the request argument to the consumer and runs it in async mode.
         * */
        Consumer<String> pingConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                logger.info(s);
            }
        };

        AkkaExecutorService akkaExecutorService = new AkkaExecutorService(5);
        akkaExecutorService.execute(pingConsumer, "Hello World");
        akkaExecutorService.execute(pingConsumer, "Hello World again");
        akkaExecutorService.execute(pingConsumer, "Hello World again again");
    }
}
