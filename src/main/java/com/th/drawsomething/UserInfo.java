package com.th.drawsomething;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private int id;
    private String name;
    private String password;
}
