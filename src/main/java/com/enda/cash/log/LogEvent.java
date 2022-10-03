package com.enda.cash.log;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class LogEvent extends ApplicationEvent {

    private String message;
    private AppUser user;
    private LocalDateTime dateAction;

    public LogEvent(Object source) {
        super(source);
    }

    public LogEvent(Object source, String message, AppUser user) {
        super(source);
        this.message = message;
        this.user = user;
        this.dateAction = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }
}
