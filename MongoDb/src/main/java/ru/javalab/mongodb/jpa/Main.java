package ru.javalab.mongodb.jpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GoodRepository goodRepository = context.getBean(GoodRepository.class);
        CpuRepository cpuRepository = context.getBean(CpuRepository.class);
//        Cpu cpu = Cpu.builder()
//                .price(530)
//                .title("m1")
//                .producer("apple")
//                .build();
//        Cpu saved = cpuRepository.save(cpu);
//        Good good = Good.builder()
//                .title("macbook")
//                .price(7000)
//                .producer("apple")
//                .cpu(cpu)
//                .build();
//        goodRepository.save(good);
        System.out.println(goodRepository.find("apple", "phone", true));
    }
}