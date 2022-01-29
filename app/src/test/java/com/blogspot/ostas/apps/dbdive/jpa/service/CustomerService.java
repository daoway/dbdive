package com.blogspot.ostas.apps.dbdive.jpa.service;

import com.blogspot.ostas.apps.dbdive.jpa.domain.AppUser;
import com.blogspot.ostas.apps.dbdive.jpa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final AppUserRepository appUserRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public AppUser saveUser(AppUser appUser) {
		return appUserRepository.save(appUser);
	}

}
