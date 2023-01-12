package ru.eduforum.challenge.securityConfig;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.eduforum.challenge.repositories.appCustomUser_repo;
import ru.eduforum.challenge.repositories.rights_repo;
import ru.eduforum.challenge.units.appCustomUser;
import ru.eduforum.challenge.units.rights;

@Service
public class CustomUserDetails implements UserDetailsService{
	private appCustomUser_repo userRepo;
	
	@Autowired
	rights_repo rightsRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		appCustomUser user = findByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException(login+" user not found ");
		}
		Collection<GrantedAuthority> roles = mapRolesToGA(rightsRepo.findAllByLogin(login));
		
		System.out.println("user password is" + user.getPassword());
		
		return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), roles);
	}
	private appCustomUser findByLogin(String login) {
		
		return userRepo.findByLogin(login);
	}
	@Autowired
	public void setMyrepo(appCustomUser_repo myrepo) {
		this.userRepo = myrepo;
	}
	private Collection<GrantedAuthority> mapRolesToGA(Collection<rights> roles){
		return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRightType())).collect(Collectors.toList());
		
	}
	
}