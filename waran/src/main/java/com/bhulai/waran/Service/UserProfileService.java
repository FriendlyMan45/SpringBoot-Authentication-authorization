package com.bhulai.waran.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bhulai.waran.Entity.UserProfile;
import com.bhulai.waran.Repository.UserProfileRepository;

@Service
public class UserProfileService implements UserDetailsService{
	
	@Autowired
	private UserProfileRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserProfile user = repository.findByEmail(email);
		if(user == null ) {
			throw new UsernameNotFoundException("User Not Found : "+ email);
		}
		return new User(user.getEmail(),
						user.getPassword(),
						Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())));
	}
	
	public UserProfile saveUser(UserProfile profile) {
		profile.setPassword(passwordEncoder.encode(profile.getPassword()));
		return repository.save(profile);
	}
	
	public List<UserProfile> findAllUsers() {
	    return repository.findAll();
	}
	
	public UserProfile getCurrentAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			return repository.findByEmail(authentication.getName());
		}
		return null;
	}
	

}
