package com.stry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stry.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService
{

	    @Autowired
	    private AdminRepository adminRepository;

	    public boolean validateCredentials(String username, String password) {
	        return adminRepository.findByUsername(username)
	                .map(admin -> admin.getPassword().equals(password))
	                .orElse(false);
	    }

	
}
