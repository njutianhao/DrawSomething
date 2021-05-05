package com.th.drawsomething;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Room {
    private String name;
    private int id;
    private int players;
    private final int maxPlayers=8;
}
