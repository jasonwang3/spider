package com.spider.core.akka.spring;

import org.springframework.context.ApplicationContext;

/**
 * Created by jason on 16-2-19.
 */
public class SpringServiceHelper {
    private final static SpringServiceHelper SpringServiceHelper = new SpringServiceHelper();

    public static SpringServiceHelper getInstance() {
        return SpringServiceHelper;
    }

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        if (getInstance() != null && getInstance().getApplicationContext() != null) {
            return getInstance().getApplicationContext().getBean(name);
        }
        return null;
    }
}
