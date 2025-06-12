package BANK;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private static int counter = 0;
    private String id;
    private String name;
    private List<Employee> employees;

    public Branch(String name) {
        this.id = "B" + (++counter);
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void showAllEmployees() {
        System.out.println("کارمندهای شعبه " + id + ":");
        for (Employee e : employees) {
            System.out.println(e.getEmployeeIdentity());
        }
    }

    @Override
    public String toString() {
        return "Branch ID: " + id + ", Name: " + name;
    }
}
