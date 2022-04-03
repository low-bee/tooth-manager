package com.xiaolong.toothmanager.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description: JwtUserDto 实现了 UserDetails 查询数据库后返回
 * @Author xiaolong
 * @Date 2022/3/28 23:00
 */
@Getter
@RequiredArgsConstructor
public class JwtUserDto implements UserDetails {

    private final UserDto user;

//    private final List<Long> dataScopes;

//    @JSONField(serialize = false)
//    private final List<GrantedAuthority> authorities;

//    public Set<String> getRoles() {
//        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
