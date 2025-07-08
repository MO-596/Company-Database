
--1)Show list of all of employee in the Company
SELECT * FROM C_EMPLOYEE;

--2)Query that shows the salary of employees from greatest to lowest
--Show only the Employees Salary from greater to least
SELECT FIRST_NAME, LAST_NAME, SALARY
FROM C_EMPLOYEE
ORDER BY SALARY DESC;

--3)Query that shows differnet department
--Showing the type of departments
SELECT * FROM C_DEPARTMENT;

--4)Query that shows department locations
SELECT * FROM C_DEPT_LOCATION;

--5)Query that shows what the projects are
SELECT * FROM C_PROJECTS;

--6)Query that shows what project is beening worked on
SELECT * FROM C_WORKS_ON;

--7)Query that shows the employees dependents
SELECT * FROM C_DEPENDENTS;

--8)Query that shows the employees dependents
--Showing Employees Dependents
SELECT DEPENDENTS_NAME, BDATE
FROM C_DEPENDENTS
ORDER BY BDATE DESC;
