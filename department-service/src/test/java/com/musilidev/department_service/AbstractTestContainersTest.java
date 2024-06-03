package com.musilidev.department_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
public class AbstractTestContainersTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));


    @Test
    public void canEstablishConnection() {
        assertThat(mysqlContainer.isCreated()).isTrue();
        assertThat(mysqlContainer.isRunning()).isTrue();
    }
}
