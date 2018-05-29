**AkkaExecutorService**

AkkaExecutorService is a small library built on Akka Actor model. It simplifies the asynchronous task execution using
Akka actors. It internally has pool of Akka actors and exposes an API for assigning tasks to Actor pool. 

Please see the sample test cases.

[SimpleTask](src/test/java/com/niharrathod/akkaexecutorservice/test/SimpleTest.java)

[RequestOnlyTask](src/test/java/com/niharrathod/akkaexecutorservice/test/RequestOnlyTask.java)

[RequestResponseTask](src/test/java/com/niharrathod/akkaexecutorservice/test/RequestResponseTest.java)