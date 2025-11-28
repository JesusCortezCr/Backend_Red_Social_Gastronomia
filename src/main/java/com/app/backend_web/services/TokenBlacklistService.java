package com.app.backend_web.services;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

//Este servicio permite anular el tiempo de vida de los tokens de los usuarios que cierran sesion de manera rapida
@Service
public class TokenBlacklistService {

    private final ConcurrentHashMap<String,Long> blacklistedTokens  =new ConcurrentHashMap<>();

    public void blacklistToken(String token){
        blacklistedTokens.put(token, System.currentTimeMillis());
    }

    public boolean estaElTokenEnListaNegra(String token){
        return blacklistedTokens.contains(token);
    }

    //Limpiar tokens expirados
    public void cleanExpiredTokens(){
        long now =System.currentTimeMillis();
        blacklistedTokens.entrySet().removeIf(entry->{
            return (now-entry.getValue())>(25*60*60*1000);
        });
    }

}
