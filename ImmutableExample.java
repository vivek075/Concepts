import java.util.HashMap;
import java.util.Map;
/*
The class must be declared as final so that child classes can’t be created.
Data members in the class must be declared private so that direct access is not allowed.
Data members in the class must be declared as final so that we can’t change the value of it after object creation.
A parameterized constructor should initialize all the fields performing a deep copy so that data members can’t be modified with an object reference.
Deep Copy of objects should be performed in the getter methods to return a copy rather than returning the actual object reference)
**/
final class Student {
	private final String name;
	private final int regNo;
	private final Map<String, String> metadata;

	public Student(String name, int regNo, Map<String, String> metadata) {
		this.name = name;
		this.regNo = regNo;
		Map<String, String> tempMap = new HashMap<>();

		for (Map.Entry<String, String> entry : metadata.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue());
		}
		this.metadata = tempMap;
	}
	public String getName() { return name; }
	public int getRegNo() { return regNo; }

	public Map<String, String> getMetadata() {
		Map<String, String> tempMap = new HashMap<>();
		for (Map.Entry<String, String> entry :
			this.metadata.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue());
		}
		return tempMap;
	}
}
public class ImmutableExample {
	public static void main(String[] args)	{
		Map<String, String> map = new HashMap<>();
		map.put("1", "One");		map.put("2", "Two");
		Student s = new Student("Test-1", 101, map);
		System.out.println(s.getName());
		System.out.println(s.getRegNo());
		System.out.println(s.getMetadata());
		map.put("3", "Three");
		System.out.println(s.getMetadata());
		s.getMetadata().put("4", "Four");

		System.out.println(s.getMetadata());
	}
}
