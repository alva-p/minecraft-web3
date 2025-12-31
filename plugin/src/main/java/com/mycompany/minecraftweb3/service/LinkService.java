/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minecraftweb3.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Servicio encargado de generar y mantener tokens de vinculación
 * entre jugadores de Minecraft y wallets Web3.
 */
public class LinkService {

    // Tokens temporales en memoria (UUID jugador -> token)
    private static final Map<UUID, String> pendingLinks = new HashMap<>();

    /**
     * Genera un token nuevo para un jugador.
     */
    public static String generateToken(UUID playerUuid) {
        String token = UUID.randomUUID().toString();
        pendingLinks.put(playerUuid, token);
        return token;
    }

    /**
     * Obtiene el token activo de un jugador (si existe).
     */
    public static String getToken(UUID playerUuid) {
        return pendingLinks.get(playerUuid);
    }

    /**
     * Elimina el token (cuando se vincula o expira).
     */
    public static void removeToken(UUID playerUuid) {
        pendingLinks.remove(playerUuid);
    }
}
