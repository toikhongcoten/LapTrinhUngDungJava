package com.lab.lifecycle.singleton;

import org.springframework.stereotype.Service;

@Service
public class ServiceA {
    private final ServiceB serviceB;

    // Spring tự động tiêm ServiceB vào đây vì ServiceA cần nó
    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
        System.out.println("[INIT] ServiceA created (ServiceB injected)");
    }
    public void doWork() {
        System.out.println("ServiceA.doWork() ->");
        serviceB.assist();
    }
}