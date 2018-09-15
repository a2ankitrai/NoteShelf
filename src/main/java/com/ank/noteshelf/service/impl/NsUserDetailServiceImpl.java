package com.ank.noteshelf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ank.noteshelf.input.UserLoginDetail;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.repository.RoleRepository;
import com.ank.noteshelf.repository.UserAuthDetailRepository;
import com.ank.noteshelf.repository.UserRepository;
import com.ank.noteshelf.resource.AccountFlag;
import com.ank.noteshelf.resource.UserConstant;

@Service
public class NsUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthDetailRepository userAuthDetailRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

	NsUser user = userRepository.findByUserName(userName);

	NsUserAuthDetail userAuthDetail = userAuthDetailRepository.findByUserId(user.getUserId());
	AccountFlag accountFlag = new AccountFlag();
	accountFlag.setAccountNonExpired(userAuthDetail.getAccountNonExpired().equals(UserConstant.Y));
	accountFlag.setAccountNonLocked(userAuthDetail.getAccountNonLocked().equals(UserConstant.Y));
	accountFlag.setCredentialsNonExpired(userAuthDetail.getCredentialsNonExpired().equals(UserConstant.Y));
	accountFlag.setEnabled(userAuthDetail.getEnabled().equals(UserConstant.Y));

	List<NsUserRoles> userRoleList = roleRepository.findAllByUserId(user.getUserId());
	List<String> roleList = new ArrayList<>();

	if (userRoleList != null && !userRoleList.isEmpty()) {
	    for (NsUserRoles role : userRoleList) {
		roleList.add(role.getRoleName());
	    }
	}

	UserLoginDetail userLoginDetail = new UserLoginDetail(user, userAuthDetail.getPassword(), roleList,
		accountFlag);

	return userLoginDetail;
    }

}
