package dev.codescreen.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ping {
    private LocalDateTime serverTime;
    public Ping(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }
    public Ping() {}
}
