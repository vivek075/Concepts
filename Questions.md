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

# what is internal working of thread pool executor ?

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
