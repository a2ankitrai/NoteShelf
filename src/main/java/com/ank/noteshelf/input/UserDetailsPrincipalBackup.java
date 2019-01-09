package com.ank.noteshelf.input;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.resource.AccountFlag;

import lombok.Data;

//Principal implementation.
@Data
public class UserDetailsPrincipalBackup implements UserDetails {
    
    /**
     * This class needs to implement OidcUser, CredentialsContainer also other than UserDetails for supporting oauth2 login.
     * */

    private static final long serialVersionUID = 1L;
    private byte[] userId;
    private String userName;
    private String password;
    private List<String> roles;
    private AccountFlag accountFlag;

    public UserDetailsPrincipalBackup(NsUser user, String password, List<String> roles, AccountFlag accountFlag) {
	this.userId = user.getUserId();
	this.userName = user.getUserName();
	this.password = password;
	this.roles = roles;
	this.accountFlag = accountFlag;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

	return getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
	return this.password;
    }

    @Override
    public String getUsername() {

	return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {

	return accountFlag.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {

	return accountFlag.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {

	return accountFlag.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {

	return accountFlag.isEnabled();
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public List<String> getRoles() {
	return roles;
    }

    public void setRoles(List<String> roles) {
	this.roles = roles;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
