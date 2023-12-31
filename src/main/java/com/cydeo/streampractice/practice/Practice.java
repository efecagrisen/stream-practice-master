package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    //1 Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    //2 Display all the countries
    public static List<Country> getAllCountries() {
        
        return countryService.readAll();
    }

    //3 Display all the departments
    public static List<Department> getAllDepartments() {
        
        return departmentService.readAll();
    }

    //4 Display all the jobs
    public static List<Job> getAllJobs() {
        
        return jobService.readAll();
    }

    //5 Display all the locations
    public static List<Location> getAllLocations() {
        
        return locationService.readAll();
    }

    //6 Display all the regions
    public static List<Region> getAllRegions() {
        
        return regionService.readAll();
    }

    //7 Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        
        return jobHistoryService.readAll();
    }

    //8 Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        
        return getAllEmployees().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    //9 Display all the countries' names
    public static List<String> getAllCountryNames() {
        
        return countryService.readAll().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());
    }

    //10 Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        
        return departmentService.readAll().stream()
                .map(p->p.getManager().getFirstName())
                .collect(Collectors.toList());
    }

    //11 Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        

        return departmentService.readAll().stream()
                .filter(p->p.getManager().getFirstName().equalsIgnoreCase("steven"))
                .collect(Collectors.toList());
    }

    //12 Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        
        return departmentService.readAll().stream()
                .filter(p->p.getLocation().getPostalCode().equals(98199))
                .collect(Collectors.toList());
    }

    //13 Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        
        List<Region> regionList = departmentService.readAll().stream()
                .filter(p->p.getDepartmentName().equalsIgnoreCase("it"))
                .map(p->p.getLocation().getCountry().getRegion())
                .collect(Collectors.toList());
        return regionList.get(0);
    }

    //14 Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        
        return departmentService.readAll().stream()
                .filter(p->p.getLocation().getCountry().getRegion().getRegionName().equalsIgnoreCase("Europe"))
                .collect(Collectors.toList());
    }

    //15 Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        
        return employeeService.readAll().stream()
                .noneMatch(p->p.getSalary()<1000);
    }

    //16 Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        
        return employeeService.readAll().stream()
                .filter(p->p.getDepartment().getDepartmentName().equalsIgnoreCase("IT"))
                .anyMatch(p->p.getSalary()>2000);
    }

    //17 Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        
        return employeeService.readAll().stream()
                .filter(p->p.getSalary()<5000)
                .collect(Collectors.toList());
    }

    //18 Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        
        return employeeService.readAll().stream()
                .filter(p->p.getSalary()>6000 && p.getSalary()<7000)
                .collect(Collectors.toList());
    }

    //19 Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        
        return employeeService.readAll().stream()
                .filter(p->p.getFirstName().equalsIgnoreCase("douglas") && p.getLastName().equalsIgnoreCase("grant")).map(p->p.getSalary())
                .findFirst().get();
    }

    //20 Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        return employeeService.readAll().stream()
                .max(Comparator.comparing(p->p.getSalary())).get().getSalary();
    }

    //21 Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        
        return employeeService.readAll().stream()
                .filter(p-> {
                    try {
                        return p.getSalary().equals(getMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    //22 Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        // return  getMaxSalaryEmployee().get(0).getJob();
        return employeeService.readAll().stream()
                .max(Comparator.comparing(p->p.getSalary())).get().getJob();
    }

    //23 Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        
        return employeeService.readAll().stream()
                .filter(p->p.getDepartment().getLocation().getCountry()
                .getRegion().getRegionName().equalsIgnoreCase("americas"))
                .max(Comparator.comparing(p->p.getSalary())).get().getSalary();
    }

    //24 Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        
//            return employeeService.readAll().stream()
//                    .filter(employee -> employee.getSalary().compareTo(getMaxSalary()) < 0)
//                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                    .findFirst().get().getSalary();

//        return employeeService.readAll().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .map(Employee::getSalary)
//                .distinct()
//                .skip(1)
//                .findFirst().get();

        //my solution
        Long maxSalary = getMaxSalary();
        return employeeService.readAll().stream()
               .filter(p->p.getSalary() < maxSalary)
                .collect(Collectors.toList()).get(0).getSalary();


    }

    //25 Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() throws Exception {
        
        Long maxSalary = getMaxSalary();
        Long secondMaxSalary = employeeService.readAll().stream()
                .filter(p->p.getSalary() < maxSalary).collect(Collectors.toList()).get(0).getSalary();
        return employeeService.readAll().stream()
                .filter(p->p.getSalary().equals(secondMaxSalary))
                .collect(Collectors.toList());
    }

    //26 Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        
        return employeeService.readAll().stream()
                .min(Comparator.comparing(p->p.getSalary())).get().getSalary();

    }

    //27 Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() throws Exception {
        
        Long minSalary = getMinSalary();
        return employeeService.readAll().stream()
                .filter(p->p.getSalary().equals(minSalary))
                        .collect(Collectors.toList());
    }

    //28 Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        
        Long minSalary = getMinSalary();
        return employeeService.readAll().stream()
                .filter(p->p.getSalary()>minSalary)
                .mapToLong(Employee::getSalary)
                .min().getAsLong();
    }

    //29 Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() throws Exception {
        
        Long minSalary = getMinSalary();
        Long secondMinSalary = employeeService.readAll().stream()
                .filter(p->p.getSalary()>minSalary)
                .mapToLong(Employee::getSalary)
                .min().getAsLong();

        return employeeService.readAll().stream()
                .filter(p->p.getSalary().equals(secondMinSalary))
                .collect(Collectors.toList());
    }

    //30 Display the average salary of the employees
    public static Double getAverageSalary() {
        
        Double averageSalary = employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        return averageSalary;
    }

    //31 Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        
        Double averageSalary = getAverageSalary();
        return employeeService.readAll().stream()
                .filter(p->p.getSalary()>averageSalary)
                .collect(Collectors.toList());

    }

    //32 Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        
        Double averageSalary = getAverageSalary();
        return employeeService.readAll().stream()
                .filter(p->p.getSalary()<averageSalary)
                .collect(Collectors.toList());
    }

    //33 Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {

        return employeeService.readAll().stream()
                .collect(Collectors.groupingBy(p->p.getDepartment().getId()));

    }

    //34 Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        
        return departmentService.readAll().stream()
                .count();
    }

    //35 Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        
        return employeeService.readAll().stream()
                .filter(p->p.getFirstName().equalsIgnoreCase("alyssa"))
                .filter(p->p.getManager().getFirstName().equalsIgnoreCase("eleni"))
                .filter(p->p.getDepartment().getDepartmentName().equalsIgnoreCase("sales"))
                .findFirst().get();


    }

    //36 Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());


    }

    //37 Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {

        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    //38 Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        
        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getStartDate().isAfter(LocalDate.of(2005,01,01)))
                .collect(Collectors.toList());
    }

    //39 Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
return jobHistoryService.readAll().stream()
        .filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007,12,31)))
        .filter(jobHistory -> jobHistory.getJob().getJobTitle().equals("programmer"))
        .collect(Collectors.toList());
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {

        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getStartDate().equals(LocalDate.of(2007,1,1)) && jobHistory.getEndDate().isEqual(LocalDate.of(2007,12,31)))
                .filter(jobHistory -> jobHistory.getDepartment().getDepartmentName().equals("Shipping"))
                .findFirst().get().getEmployee();

    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
return employeeService.readAll().stream()
        .filter(employee -> employee.getFirstName().startsWith("A"))
        .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
return employeeService.readAll().stream()
        .filter(employee -> employee.getJob().getId().contains("IT"))
        .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {



    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        
        return new ArrayList<>();
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        
        return new ArrayList<>();
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        
        return new ArrayList<>();
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        
        return 1;
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        
        return new ArrayList<>();
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        
        return new ArrayList<>();
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        
        return new ArrayList<>();
    }

}
