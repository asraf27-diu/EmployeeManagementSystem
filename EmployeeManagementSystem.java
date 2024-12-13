// Employee Management System 


import java.util.*;

// Employee Class
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %d, Name: %s, Department: %s, Salary: $%.2f", id, name, department, salary);
    }
}

// Employee Manager Class
class EmployeeManager {
    private List<Employee> employees;
    private int nextId;
    private Set<String> registeredUsers;
    private String currentUser;

    public EmployeeManager() {
        employees = new ArrayList<>();
        nextId = 1;
        registeredUsers = new HashSet<>();
        currentUser = null;
    }

    public void register(String username) {
        if (registeredUsers.contains(username)) {
            System.out.println("User already registered.");
        } else {
            registeredUsers.add(username);
            System.out.println("User registered successfully: " + username);
        }
    }

    public void signIn(String username) {
        if (!registeredUsers.contains(username)) {
            System.out.println("User not found. Please register first.");
        } else if (currentUser != null) {
            System.out.println("Another user is already signed in. Please sign out first.");
        } else {
            currentUser = username;
            System.out.println("User signed in successfully: " + username);
        }
    }

    public void signOut() {
        if (currentUser == null) {
            System.out.println("No user is currently signed in.");
        } else {
            System.out.println("User signed out successfully: " + currentUser);
            currentUser = null;
        }
    }

    public boolean isUserSignedIn() {
        if (currentUser == null) {
            System.out.println("Please sign in to perform this action.");
            return false;
        }
        return true;
    }

    public void addEmployee(String name, String department, double salary) {
        if (!isUserSignedIn()) return;
        Employee employee = new Employee(nextId++, name, department, salary);
        employees.add(employee);
        System.out.println("Employee added successfully:");
        System.out.println(employee);
    }

    public void viewAllEmployees() {
        if (!isUserSignedIn()) return;
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("Employee List:");
        employees.forEach(System.out::println);
    }

    public void updateEmployee(int id, String name, String department, double salary) {
        if (!isUserSignedIn()) return;
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setDepartment(department);
            employee.setSalary(salary);
            System.out.println("Employee updated successfully:");
            System.out.println(employee);
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    public void removeEmployee(int id) {
        if (!isUserSignedIn()) return;
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employees.remove(employee);
            System.out.println("Employee removed successfully: " + employee);
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    public void searchEmployeeByName(String name) {
        if (!isUserSignedIn()) return;
        List<Employee> foundEmployees = employees.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .toList();
        if (foundEmployees.isEmpty()) {
            System.out.println("No employees found with the name: " + name);
        } else {
            System.out.println("Search Results:");
            foundEmployees.forEach(System.out::println);
        }
    }

    public void displayEmployeesByDepartment(String department) {
        if (!isUserSignedIn()) return;
        List<Employee> departmentEmployees = employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .toList();
        if (departmentEmployees.isEmpty()) {
            System.out.println("No employees found in the department: " + department);
        } else {
            System.out.println("Employees in " + department + " Department:");
            departmentEmployees.forEach(System.out::println);
        }
    }

    public void calculateTotalSalary() {
        if (!isUserSignedIn()) return;
        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        System.out.printf("Total salary expenditure: $%.2f%n", totalSalary);
    }

    private Employee findEmployeeById(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}

// Main Class
public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();

        System.out.println("Welcome to the Professional Employee Management System!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Sign In");
            System.out.println("3. Sign Out");
            System.out.println("4. Add Employee");
            System.out.println("5. View All Employees");
            System.out.println("6. Update Employee");
            System.out.println("7. Remove Employee");
            System.out.println("8. Search Employee by Name");
            System.out.println("9. Display Employees by Department");
            System.out.println("10. Calculate Total Salary Expenditure");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter username to register: ");
                    String registerUsername = scanner.nextLine();
                    manager.register(registerUsername);
                    break;
                case "2":
                    System.out.print("Enter username to sign in: ");
                    String signInUsername = scanner.nextLine();
                    manager.signIn(signInUsername);
                    break;
                case "3":
                    manager.signOut();
                    break;
                case "4":
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = getValidDouble(scanner);
                    manager.addEmployee(name, department, salary);
                    break;
                case "5":
                    manager.viewAllEmployees();
                    break;
                case "6":
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = getValidInt(scanner);
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDepartment = scanner.nextLine();
                    System.out.print("Enter New Salary: ");
                    double newSalary = getValidDouble(scanner);
                    manager.updateEmployee(updateId, newName, newDepartment, newSalary);
                    break;
                case "7":
                    System.out.print("Enter Employee ID to remove: ");
                    int removeId = getValidInt(scanner);
                    manager.removeEmployee(removeId);
                    break;
                case "8":
                    System.out.print("Enter Employee Name to search: ");
                    String searchName = scanner.nextLine();
                    manager.searchEmployeeByName(searchName);
                    break;
                case "9":
                    System.out.print("Enter Department to display employees: ");
                    String searchDepartment = scanner.nextLine();
                    manager.displayEmployeesByDepartment(searchDepartment);
                    break;
                case "10":
                    manager.calculateTotalSalary();
                    break;
                case "11":
                    System.out.println("Thank you for using the Employee Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static double getValidDouble(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
