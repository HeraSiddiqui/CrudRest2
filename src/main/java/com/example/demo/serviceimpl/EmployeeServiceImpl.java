package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;



@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}


	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}


	@Override
	public List<Employee> getAllEmployees() {
		return  employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
	     Optional<Employee> employee = employeeRepository.findById(id); 
	     if(employee.isPresent()) {
	    	 return employee.get();
	    	 }else {
	    		 throw new ResourceNotFoundException("Employee", "Id",id);
	    		 //return employeeRepository.findById(id).orElseThrow(() ->  (Apart from using all those If else lines of code lambda code can be used
	    		                   //new ResourceNotFoundException("Employee", "Id",id));
	    	 }
	     }
	
	@Override
	public Employee updateEmployee(Employee employee,long id) {
		
		//we need to check whether employee with given id is in exist in DB or not
		Employee findById = employeeRepository.findById(id).orElseThrow(() -> 
		                        new ResourceNotFoundException("Employee", "Id",id));
		
		findById.setFirstName(employee.getFirstName());
		findById.setLastName(employee.getLastName());
		findById.setEmail(employee.getEmail());
		findById.setAddress(employee.getAddress());
		//save existing employee to database
		employeeRepository.save(findById);
		return findById;
		
	}
	
	@Override
	public void deleteEmployee(long id) {
		
		// check whether a employee exist in a DB or not
		employeeRepository.findById(id).orElseThrow(() -> 
								new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.deleteById(id);
	}
	}
	

