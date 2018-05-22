package com.example.shop.controller;

import com.example.shop.socket.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/socket")
public class WebSocketController {

    @Resource
    private WebSocketServer webSocketServer;

    @RequestMapping("/send")
    public Map<String, Object> webSocketClient(@RequestParam(value = "message", required = false) String message, @RequestParam(value = "id", required = false) String id) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        try {
            WebSocketServer.sendOne(message, id);
            stringObjectMap.put("mark", true);
        } catch (Exception e) {
            stringObjectMap.put("mark", true);
        }
        return stringObjectMap;
    }

    @RequestMapping("/sendAll")
    public Map<String, Object> webSocketClients(@RequestParam(value = "message", required = false) String message) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        try {
            WebSocketServer.sendInfo(message);
            stringObjectMap.put("mark", true);
        } catch (Exception e) {
            stringObjectMap.put("mark", true);
        }
        return stringObjectMap;
    }
}
