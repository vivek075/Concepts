import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ComparatorExamples {
    public static void main(String[] args) {
// Sort a list of strings by their length using a Comparator.
        List<String> words = Arrays.asList("orange", "kiwi", "grapes", "apple", "carrot");
        Stream<String> sorted = words.stream().sorted(Comparator.comparingInt(String::length));
// Sort a list of strings in reverse order using Comparator.
        List<String> newWords = Arrays.asList("grapes", "apple", "banana", "watermelon", "orange");
        newWords.sort(Comparator.reverseOrder());
// Sort a list of integers in ascending order using Comparator.
        List<Integer> numbers = Arrays.asList(7, 2, 8, 1, 5);
        numbers.sort(Comparator.naturalOrder());
// Sort a list of employees by their age using a Comparator
        List<Employee> employees = Arrays.asList(new Employee("Mahesh", 25), new Employee("Suresh", 22), new Employee("Kamlesh", 28));
        employees.sort(Comparator.comparingInt(Employee::getAge));
// Sort a list of employees first by age, then by name using a Comparator.
        List<Employee> newEmployees = Arrays.asList(new Employee("Avi", 25), new Employee("Abhi", 22), new Employee("Vinay", 25));
        newEmployees.sort(Comparator.comparingInt(Employee::getAge).thenComparing(Employee::getName));
// Sort a list of employees by their salary using a Comparator.
        List<Employee> newSalEmployees = Arrays.asList(new Employee("Rohan", 50000.0), new Employee("Mohan", 60000.0), new Employee("Sohan", 40000.0));
        newSalEmployees.sort(Comparator.comparingDouble(Employee::getSalary));
    }
}
class Employee {
    String name;
    int age;
    double salary;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
