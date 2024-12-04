# How will you simulate concurrent modification through stream api in java 8

In Java 8, we can simulate concurrent modification using the Stream API by manipulating a collection from multiple threads while a stream is being processed. Here's an example to demonstrate this:
```
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificationExample {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Task to modify the list concurrently
        Runnable modifier = () -> {
            for (int i = 0; i < 5000; i++) {
                list.remove(i);
            }
        };

        // Task to process the list using Stream API
        Runnable processor = () -> {
            try {
                list.stream().forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("Concurrent modification detected: " + e);
            }
        };

        // Execute the tasks
        executorService.submit(modifier);
        executorService.submit(processor);

        // Shutdown the executor service
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Processing finished");
    }
}
```

Points to Note:

- Concurrent Modification Exception:

Since the list is being modified while it is being processed by a stream, a ConcurrentModificationException is likely to occur. This example demonstrates handling this exception.

- Synchronized List:

Using a synchronized list (Collections.synchronizedList) ensures that list operations are thread-safe, but it does not prevent concurrent modification exceptions when the list is modified while iterating over it.

- Awaiting Termination:

awaitTermination is used to wait for the executor service to complete its tasks within a specified time.

This example helps illustrate how concurrent modification can be simulated and handled in Java 8 using the Stream API and multi-threading.

# How many thread will open for parallel stream and how parallel stream internally works ?

Number of Threads for Parallel Streams
In Java, when we use parallel streams, the number of threads used is typically equal to the number of available processors. This is determined by Runtime.getRuntime().availableProcessors(). The actual number of threads can vary based on the implementation and the workload, but this is the default behavior.

How Parallel Streams Internally Work

1. Splitting:

The source data (e.g., a collection) is split into multiple parts. This is done using the Spliterator interface, which is designed to efficiently partition the data for parallel processing.

2. Fork/Join Framework:

Java's parallel streams use the Fork/Join framework under the hood. This framework is designed to efficiently manage a pool of worker threads. It recursively splits tasks into smaller tasks until they are simple enough to be processed in parallel.

3. Processing:

Each part of the data is processed in parallel by different threads from the Fork/Join pool. The operations defined in the stream (e.g., filter, map, reduce) are applied to these data parts concurrently.

4. Combining:

The results from the parallel processing tasks are combined back together. For example, in a reduction operation, partial results are merged to produce a final result.
```
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = IntStream.rangeClosed(1, 1000)
                                      .boxed()
                                      .collect(Collectors.toList());

        List<Integer> evenNumbers = list.parallelStream()
                                        .filter(num -> num % 2 == 0)
                                        .collect(Collectors.toList());

        System.out.println(evenNumbers);
    }
}
```

Detailed Steps

Source Splitting: The list.parallelStream() call creates a parallel stream. The underlying Spliterator splits the list into smaller parts.

Task Submission: Each split part is submitted as a task to the Fork/Join pool. The filter operation is applied in parallel.

Concurrent Execution: Multiple threads from the Fork/Join pool process these tasks concurrently, each filtering a part of the list.

Combining Results: The filtered results from all threads are combined into a final list of even numbers.

Customizing the Fork/Join Pool

If we need to customize the number of threads or other aspects of the Fork/Join pool, you can do so by setting the java.util.concurrent.ForkJoinPool instance for your parallel stream:

```
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomParallelStream {
    public static void main(String[] args) {
        List<Integer> list = IntStream.rangeClosed(1, 1000)
                                      .boxed()
                                      .collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(4); // Custom thread pool with 4 threads

        List<Integer> evenNumbers = customThreadPool.submit(() ->
            list.parallelStream()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList())
        ).join();

        System.out.println(evenNumbers);
    }
}
```
In this example, a custom Fork/Join pool with 4 threads is used to process the parallel stream. This allows for greater control over the parallel execution behavior.

# What is internal working of thread pool executor ?

The `ThreadPoolExecutor` is the most commonly used implementation of the `ExecutorService` interface in Java. It manages a pool of worker threads to execute tasks concurrently. Here's an in-depth look at how ThreadPoolExecutor manages its threads, including how it keeps track of active and dead threads.

**Internal Working of ThreadPoolExecutor**

**1. Thread Pool Configuration Parameters:**

Core Pool Size: The minimum number of threads to keep in the pool, even if they are idle.

Maximum Pool Size: The maximum number of threads allowed in the pool.

Keep-Alive Time: The maximum time that excess idle threads will wait for new tasks before terminating.

Work Queue: A blocking queue that holds tasks before they are executed by a thread.

**2. Task Submission:**

When a task is submitted to the ThreadPoolExecutor, the executor decides how to handle it based on the current state of the pool.

**3. Worker Threads Management:**

Core Threads: If the number of active threads is less than the core pool size, a new thread is created to handle the task.

Work Queue: If the number of active threads is at least the core pool size, the task is added to the work queue.

Maximum Threads: If the work queue is full and the number of active threads is less than the maximum pool size, a new thread is created to handle the task.

Rejection: If the work queue is full and the number of active threads is at the maximum pool size, the task is rejected according to the rejection policy.

**4. Thread Lifecycle Management:**

Idle Threads: Threads that are idle (i.e., not executing tasks) for longer than the keep-alive time are terminated if there are more than the core pool size.

Thread Creation: New threads are created as needed, up to the maximum pool size.

Thread Termination: Threads terminate after being idle for the keep-alive time, reducing the pool size back to the core pool size.

**5. Task Execution:**

Worker threads continuously poll the work queue for new tasks and execute them.

After completing a task, a worker thread checks the queue for more tasks and processes them.

**Key Methods and Components**

ThreadPoolExecutor Constructor:
```
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```
Worker Class:

An inner class representing a worker thread.

Manages the lifecycle of a single thread and the execution of tasks.

Worker's Run Method:

The worker thread's main run loop that processes tasks from the work queue.

Task Submission Method:

`execute(Runnable command)`: Submits a new task for execution.

Checks the current state of the thread pool and decides how to handle the task (e.g., create a new thread, add to the queue, or reject).

Thread Management Methods:

addWorker(Runnable firstTask, boolean core): Adds a new worker thread.

getTask(): Retrieves a task from the work queue, blocking if necessary.

Example of ThreadPoolExecutor in Action
```
import java.util.concurrent.*;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        // Creating a ThreadPoolExecutor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                      // corePoolSize
            4,                      // maximumPoolSize
            60,                     // keepAliveTime
            TimeUnit.SECONDS,       // keepAliveTime unit
            new LinkedBlockingQueue<>(2), // workQueue
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy() // rejection policy
        );

        // Submitting tasks to the executor
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is executing a task");
                try {
                    Thread.sleep(1000); // Simulating task duration
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutting down the executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
```
Key Points in the Example

Core Pool Size and Maximum Pool Size: The executor starts with 2 core threads and can grow to 4 threads.

Keep-Alive Time: Threads beyond the core size will terminate after being idle for 60 seconds.

Work Queue: A LinkedBlockingQueue with a capacity of 2 is used to hold tasks before execution.

Rejection Policy: If the work queue is full and no more threads can be created, the AbortPolicy will throw a RejectedExecutionException.

# What are the changes of permgen in JDK 8. (PermGen and Meta)

In JDK 8, significant changes were made regarding the management of class metadata, which was previously stored in the Permanent Generation (PermGen) space. Here’s an overview of these changes:

PermGen Removal and Metaspace Introduction

1. **PermGen (Permanent Generation):**

JDK 7 and earlier: The Permanent Generation, part of the Java heap, stored class metadata, interned strings, and static variables.

Issues with PermGen: It had a fixed maximum size, leading to potential OutOfMemoryError: PermGen space errors, especially in applications with a large number of classes (e.g., applications that dynamically load classes or use a lot of frameworks).

2. **Metaspace:**

Introduced in JDK 8: The Permanent Generation was replaced with Metaspace, a native memory area (outside the Java heap) used for storing class metadata.

Benefits of Metaspace:

Dynamic Sizing: Unlike PermGen, Metaspace can grow dynamically based on application needs, reducing the likelihood of running out of space for class metadata.

Elimination of Fixed Limits: This removes the need for tuning the size of the PermGen space, making it easier to manage memory.

Improved Garbage Collection: Metaspace helps improve garbage collection efficiency, as class metadata is no longer part of the heap.

Key Differences and Improvements

Memory Allocation:

PermGen: Allocated within the Java heap, with a fixed maximum size specified by the -XX:MaxPermSize option.

Metaspace: Allocated in native memory, with a default initial size and dynamic growth. The maximum size can be controlled with the -XX:MaxMetaspaceSize option, though it typically does not need to be set.

Garbage Collection:

PermGen: Collected during full garbage collections, which could be a performance bottleneck.

Metaspace: Managed separately from the Java heap, leading to more efficient garbage collection.

Configuration Options:

PermGen: -XX:PermSize (initial size) and -XX:MaxPermSize (maximum size).

Metaspace:
-XX:MetaspaceSize: Initial size of Metaspace.

-XX:MaxMetaspaceSize: Maximum size of Metaspace.

-XX:MinMetaspaceFreeRatio and -XX:MaxMetaspaceFreeRatio: Control the amount of free space in Metaspace.

Here’s an example of how you might configure Metaspace in a Java application:
```
java -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=512M -XX:MinMetaspaceFreeRatio=50 -XX:MaxMetaspaceFreeRatio=80 -jar myapp.jar
```
In this example:

`-XX:MetaspaceSize=128M`: Sets the initial Metaspace size to 128 MB.

`-XX:MaxMetaspaceSize=512M`: Sets the maximum Metaspace size to 512 MB.

`-XX:MinMetaspaceFreeRatio=50`: Ensures that at least 50% of the Metaspace remains free after GC.

`-XX:MaxMetaspaceFreeRatio=80`: Allows up to 80% of the Metaspace to be free before resizing

Monitoring Metaspace Usage

Monitoring Metaspace usage can help in understanding the memory footprint of class metadata and in tuning memory settings if necessary. Tools like jstat or VisualVM can be used for monitoring:

`jstat -gc <pid>`

The transition from PermGen to Metaspace in JDK 8 was a significant change aimed at improving the management of class metadata, reducing configuration complexity, and enhancing performance and reliability. By leveraging native memory for class metadata, Metaspace offers a more flexible and robust solution compared to the fixed-size PermGen.

# Difference Between Web Server and Application Server
**Web Server**:

A web server is designed to _serve static content such as HTML, CSS, JavaScript, and images to the client_. It handles HTTP requests and responses, and it _can also serve dynamic content using server-side scripts, but it primarily focuses on serving web pages_.

- Primary Function: Serve static content and handle HTTP requests.
- Examples: Apache HTTP Server, Nginx, Microsoft IIS.
- Use Cases: Hosting websites, serving static files, reverse proxying.

**Application Server**:

An application server _provides a runtime environment for executing business logic and dynamic content_. It _supports a variety of services such as transaction management, messaging, security, and resource pooling_. It can also serve web content, but its primary role is to host and manage applications.

- Primary Function: Serve dynamic content, manage business logic, and provide various enterprise-level services.
- Examples: Apache Tomcat, JBoss EAP, IBM WebSphere, Oracle WebLogic, GlassFish.
- Use Cases: Enterprise applications, backend services, complex business logic, multi-tier applications.

**When to Use Which Server**:

Web Server:

Simple websites with static content.

When you need to serve content quickly with minimal processing.

Use cases include static sites, serving media files, load balancing, and reverse proxying.

**Application Server**:

Complex, enterprise-level applications requiring business logic execution.

Applications needing transaction management, security, messaging, etc.

Use cases include e-commerce platforms, enterprise resource planning (ERP) systems, and customer relationship management (CRM) systems.

**Examples of Servers**:

1. Apache HTTP Server (Web Server): Widely used, open-source web server known for its flexibility and rich feature set.

2. Nginx (Web Server): High-performance web server and reverse proxy server known for its speed and scalability.

3. Apache Tomcat (Application Server): Open-source application server for Java Servlets and JSP, often used for lightweight Java web applications.

4. JBoss EAP (Application Server): Enterprise-level Java application server with robust features for large-scale applications.

5. IBM WebSphere (Application Server): Comprehensive application server with extensive enterprise features for critical business applications.

**Do We Still Require an Application Server for Java Development?**

The need for an application server in Java development depends on the complexity and requirements of the application:

Lightweight Applications: For simple web applications or microservices, a web server like Apache Tomcat or even an embedded server (like Spring Boot's embedded Tomcat) may be sufficient.

Enterprise Applications: For applications requiring robust features like distributed transactions, messaging, and complex security, a full-fledged application server like JBoss EAP, IBM WebSphere, or Oracle WebLogic is more appropriate.

Microservices Architecture: Modern microservices architectures often prefer lightweight servers or containerized environments, reducing the reliance on traditional heavyweight application servers.

# Techniques for Session Persistence
To ensure that subsequent requests after login go to the same web server, especially in a distributed or load-balanced environment, several techniques can be used. This is crucial for maintaining session state and ensuring a seamless user experience. Here’s a breakdown of how this can be achieved:

**1. Sticky Sessions (Session Affinity)**

Description: Sticky sessions, also known as session affinity, direct all requests from a particular user to the same web server instance after the initial login.

Implementation: Load balancers can be configured to use sticky sessions by setting a special cookie (like JSESSIONID in Java applications) to identify the user's session and route their requests accordingly.

Pros: Simple to implement and works well for applications with low to moderate traffic.

Cons: Can lead to uneven load distribution, as some servers might become overloaded while others are underutilized.

**2. Distributed Sessions (Session Replication)**

Description: Session data is replicated across all servers in the cluster so that any server can handle subsequent requests.

Implementation: Application servers like Apache Tomcat, JBoss, and others support session replication. They share session state across the cluster, ensuring consistency.

Pros: Ensures high availability and fault tolerance.

Cons: Can introduce overhead due to session replication traffic, potentially affecting performance.

Examples with Apache Tomcat:
```
<Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster">
    <Manager className="org.apache.catalina.ha.session.DeltaManager"
             expireSessionsOnShutdown="false"
             notifyListenersOnReplication="true"/>
    <Channel className="org.apache.catalina.tribes.group.GroupChannel">
        <Membership className="org.apache.catalina.tribes.membership.McastService"
                    address="228.0.0.4"
                    port="45564"
                    frequency="500"
                    dropTime="3000"/>
        <Sender className="org.apache.catalina.tribes.transport.ReplicationTransmitter">
            <Transport className="org.apache.catalina.tribes.transport.nio.PooledParallelSender"/>
        </Sender>
        <Receiver className="org.apache.catalina.tribes.transport.nio.NioReceiver"
                  address="auto"
                  port="4000"
                  autoBind="100"
                  selectorTimeout="5000"
                  maxThreads="6"/>
        <Interceptor className="org.apache.catalina.tribes.group.interceptors.TcpFailureDetector"/>
        <Interceptor className="org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor"/>
    </Channel>
</Cluster>
```

**3. External Session Stores**

Description: Store session data in an external datastore such as a database, Redis, or another distributed cache.

Implementation: Sessions are stored in an external system, allowing any server to retrieve the session state.

Pros: Decouples session management from the application server, improving scalability and fault tolerance.

Cons: Adds latency due to external storage access and requires additional infrastructure.

Example with Spring Session and Redis:

```
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

Configuration:
```
@Configuration
@EnableRedisHttpSession
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
}
```

**4. Token-Based Authentication (Stateless Sessions)**

Description: Use JWT (JSON Web Tokens) or similar tokens for stateless authentication. Each request includes a token that contains all the necessary user information.

Implementation: After login, the server issues a token to the client. The client includes this token in the Authorization header of each subsequent request.

Pros: Scalability is improved as no session state is stored on the server.

Cons: Managing token security and invalidation can be complex.

Spring Security and JWT:
```
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

```
@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Authentication logic
        String jwt = generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
```

**Summary**:

Sticky Sessions: Simple, but can lead to uneven load distribution.

Distributed Sessions: Ensures availability and fault tolerance but can introduce replication overhead.

External Session Stores: Decouples session management but adds latency and requires additional infrastructure.

Token-Based Authentication: Improves scalability but requires careful management of token security and invalidation.

---
# What is the difference between Monolithic, SOA and Microservices Architecture?
These are architecture styles. Each have distinct characteristics, advantages, and challenges.

**Monolithic Architecture**

**Characteristics**:
- Single Codebase: The entire application is developed and deployed as a single unit.
- Tightly Coupled: All components of the application are tightly integrated.
- Single Database: Typically, a monolithic application uses a single, centralized database.

**Advantages**:
- Simplicity: Easier to develop, test, and deploy initially.
- Performance: No inter-process communication, leading to potentially faster performance for tightly coupled operations.

**Challenges**:
- Scalability: Difficult to scale individual components; the whole application needs to be scaled.
- Maintenance: As the application grows, it becomes harder to maintain and update.
- Deployment: Any change requires redeploying the entire application, which can lead to longer downtimes.

**Service-Oriented Architecture (SOA)**

**Characteristics**:
- Service Contracts: Services communicate through well-defined interfaces (often using protocols like SOAP).
- Middleware: Often relies on an Enterprise Service Bus (ESB) to handle communication, transformation, and routing of messages between services.
- Reusable Services: Designed to be reused across different applications.

**Advantages**:
- Reusability: Services can be reused in different applications, reducing redundancy.
- Interoperability: Services can be developed in different technologies as long as they adhere to the communication protocol.

**Challenges**:
- Complexity: Managing and coordinating services can be complex, especially with an ESB.
- Performance: Additional overhead from ESB and communication protocols can impact performance.
- Deployment: Still relatively complex, as services might have dependencies that need careful management.

**Microservices Architecture**

**Characteristics**:
- Independence: Each service is developed, deployed, and scaled independently.
- Decentralized Data Management: Each microservice can have its own database.
- Lightweight Communication: Services often communicate using lightweight protocols like HTTP/REST or messaging queues.

**Advantages**:
- Scalability: Services can be scaled independently based on demand.
- Flexibility: Teams can develop, deploy, and scale services independently, often using different technologies.
- Resilience: Failures in one service do not directly impact other services, improving overall system resilience.

**Challenges**:
- Complexity: Increased number of services leads to higher operational complexity.
- Distributed Systems Issues: Challenges related to network latency, data consistency, and inter-service communication.
- DevOps: Requires a mature DevOps culture for continuous integration, deployment, and monitoring.

**_Summary_**

Monolithic: Simple and efficient for small to medium applications but can become unwieldy and difficult to manage as it grows.

SOA: Focuses on reusability and interoperability through service contracts and often uses an ESB, which can introduce complexity.

Microservices: Emphasizes independence and scalability of services, often with decentralized data management, suitable for large and complex applications but requires sophisticated management and DevOps practices.
