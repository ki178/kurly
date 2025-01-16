package com.bhs.sssss.entities;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "index")
public class LoginAttemptEntity {
    private int index;
    private String clientIp;
    private String clientId;
    private String clientUa;
    private String result;
    private LocalDateTime createdAt;
}