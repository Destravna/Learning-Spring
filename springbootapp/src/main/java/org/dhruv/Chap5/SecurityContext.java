package org.dhruv.Chap5;

import org.springframework.stereotype.Component;

@Component
public class SecurityContext {
    private ThreadLocal<String> userRole = new ThreadLocal<>();

    public void setUserRole(String userRole) {
        this.userRole.set(userRole);
    }

    public String getUserRole() {
        return userRole.get();
    }

    public void clear(){
        userRole.remove();
    }

}
