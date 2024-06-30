# Creational Design Patterns

**_Singleton_**

Singleton is a **creational design pattern**, which ensures that only one object of its kind/type exists and provides a single point of access to it for any other code.

Singleton examples in Java core libraries:

`java.lang.Runtime#getRuntime()`

`java.awt.Desktop#getDesktop()`

`java.lang.System#getSecurityManager()`

---
Naïve Singleton (single-threaded)

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
        System.out.println("If you see the same value, then singleton is reused" + "\n" +
                "If you see different values, then not" + "\n\n" +
                "RESULT:" + "\n");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
```

Naïve Singleton (multithreaded)

```
