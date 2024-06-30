# Creational Design Patterns

**_Singleton_**

Singleton is a **creational design pattern**, which ensures that only one object of its kind/type exists and provides a single point of access to it for any other code.

Singleton examples in Java core libraries:

`java.lang.Runtime#getRuntime()`

`java.awt.Desktop#getDesktop()`

`java.lang.System#getSecurityManager()`

---
**Naïve Singleton (single-threaded)**

```
public final class Singleton {
    private static Singleton instance;
    public String value;

    private Singleton(String value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        if (instance == null) {
            instance = new Singleton(value);
        }
        return instance;
    }
}
```
Client Code

```
public class DemoSingleThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
```

**Naïve Singleton (multithreaded)**

```
public class DemoMultiThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
```
**Thread-safe Singleton with lazy loading**

To fix the problem, we have to synchronize threads during first creation of the Singleton object.
```
public final class Singleton {
    // The field must be declared volatile so that double check lock would work correctly.
    private static volatile Singleton instance;

    public String value;

    private Singleton(String value) {
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        // Approach : double-checked locking (DCL). It exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate instances as a result.
        
        Singleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Singleton.class) {
            if (instance == null) {
                instance = new Singleton(value);
            }
            return instance;
        }
    }
}

public class DemoMultiThread {
    public static void main(String[] args) {
        System.out.println("If same value displayed, then singleton is reused. If we see different values, then not");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}

```
