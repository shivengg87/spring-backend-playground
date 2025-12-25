package com.sm.fundamentals.whatisbean;

import org.springframework.stereotype.Component;

@Component
public class SpringBeanUsingComponet {
    private final DependencyBean dependencyBean;
    SpringBeanUsingComponet(DependencyBean dependencyBean)
    {
       this.dependencyBean = dependencyBean;
        System.out.println("Spring Bean created with dependency injection");
    }
    public void performAction() {
        System.out.println("Performing action with dependency");
        dependencyBean.helperMethod();
    }

}

@Component
class DependencyBean {
    public DependencyBean() {
        System.out.println("Dependency Bean created by Spring");
    }

    public void helperMethod() {
        System.out.println("Helper method called");
    }
}