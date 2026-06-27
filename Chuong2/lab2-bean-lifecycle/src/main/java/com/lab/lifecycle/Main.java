package com.lab.lifecycle;

import com.lab.lifecycle.config.AppConfig;
import com.lab.lifecycle.managed.ManagedBean;
import com.lab.lifecycle.prototype.RequestProcessor;
import com.lab.lifecycle.singleton.ServiceA;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Before context ---");
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("--- After context  ---\n");

        // PHẦN 1: Xem thứ tự khởi tạo dựa trên Dependency Graph
        System.out.println("=== Phan 1: Dependency Order ===");
        ctx.getBean(ServiceA.class).doWork();

        // PHẦN 2: Xem vòng đời thực tế của Bean thông qua Hook
        System.out.println("\n=== Phan 2: Vong doi Bean ===");
        var bean = ctx.getBean(ManagedBean.class);
        bean.doWork();

        // PHẦN 3: So sánh Singleton vs Prototype
        System.out.println("\n=== Phan 3: Singleton ===");
        var a1 = ctx.getBean(ServiceA.class);
        var a2 = ctx.getBean(ServiceA.class);
        System.out.println("a1 == a2 ? " + (a1 == a2));

        System.out.println("\n=== Phan 3: Prototype ===");
        var p1 = ctx.getBean(RequestProcessor.class);
        var p2 = ctx.getBean(RequestProcessor.class);
        var p3 = ctx.getBean(RequestProcessor.class);
        p1.process("Request A");
        p2.process("Request B");
        p3.process("Request C");
        System.out.println("p1 == p2 ? " + (p1 == p2));
        System.out.println("p1 == p3 ? " + (p1 == p3));

        // Đóng context để kích hoạt @PreDestroy dọn dẹp
        System.out.println("\n--- Dong context (ctx.close()) ---");
        ctx.close();
    }
}