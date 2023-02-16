package com.otot.mikdah.websocket.server;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Defines {

    public static final String END_POINT = "/websocket-server";
    public static final String BROKER = "/topic";
    public static final String DEST_PREFIX = "/app";
    public static final String MAPPPING = "/process-message";
    public static final String MSG_FROM_SERVER = BROKER + "/messages";
    public static final String MSG_TO_SERVER = DEST_PREFIX + MAPPPING;
}
