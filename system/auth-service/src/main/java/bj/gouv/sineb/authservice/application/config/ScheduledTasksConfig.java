package bj.gouv.sineb.authservice.application.config;

import bj.gouv.sineb.authservice.application.code.service.impl.PolicieServiceImpl;
import bj.gouv.sineb.authservice.application.code.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledTasksConfig {

    // ALL SCHEDELED TASKS

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PolicieServiceImpl policieService;

    @Scheduled(cron = "0 0 * * * *")
    private void deleteTemporaryPasswordValue(){
        userService.deleteTemporaryPasswordValue();
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void revokeExpiredPolicies(){
        policieService.revokeExpiredPolicies();
    }
}
