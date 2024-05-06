package com.bhulai.waran.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhulai.waran.Entity.UserProfile;
import com.bhulai.waran.Service.JwtService;
import com.bhulai.waran.Service.UserProfileService;

@RestController
@RequestMapping("/api")
public class UserProfileController {

	@Autowired
	private UserProfileService profileService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<UserProfile> register(@RequestBody UserProfile profile){
		System.out.println(profile);
		return ResponseEntity.ok(profileService.saveUser(profile));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/admin/users")
	public ResponseEntity<List<UserProfile>> getAllUsers(){
		List<UserProfile> users = profileService.findAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/user/info")
	public ResponseEntity<UserProfile> getUserInfo(){
		UserProfile user = profileService.getCurrentAuthenticatedUser();
		return ResponseEntity.ok((user));
	}
	
	@PostMapping("/authenticate")
	public String AuthenticateAndGetToken(@RequestBody UserProfile profile) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getEmail(), profile.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(profile.getEmail());
		}
		else {
			throw new UsernameNotFoundException("User is inValid");
		}
		
	}
	
}
