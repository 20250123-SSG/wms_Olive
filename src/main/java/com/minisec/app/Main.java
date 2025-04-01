package com.minisec.app;

import com.minisec.common.health.HealthCheck;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        // 1. DB 헬스 체크
        boolean healthy = HealthCheck.perform();
        if (!healthy) {
            System.err.println("DB Health check failed.");
            System.exit(1);
        }
        System.out.println("DB Health check successful.");
    }
}