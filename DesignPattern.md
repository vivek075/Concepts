# Creational Design Patters

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
