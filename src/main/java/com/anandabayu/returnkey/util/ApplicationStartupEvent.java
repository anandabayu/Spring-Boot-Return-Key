package com.anandabayu.returnkey.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Component
public class ApplicationStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Application is Starting");
    }
}
