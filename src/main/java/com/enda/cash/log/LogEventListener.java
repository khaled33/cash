package com.enda.cash.log;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogEventListener implements ApplicationListener<LogEvent> {

    private LogRepository logRepository;

    @Override
    public void onApplicationEvent(LogEvent event) {
        Log log = new Log(null, event.getMessage(), event.getUser(), event.getDateAction());
        logRepository.save(log);

    }
}
