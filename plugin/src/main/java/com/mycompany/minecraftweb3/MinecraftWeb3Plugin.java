/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.minecraftweb3;

import com.mycompany.minecraftweb3.command.WalletCommand;
import com.mycompany.minecraftweb3.service.DataService;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftWeb3Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MinecraftWeb3Plugin habilitado correctamente");

        // Inicializar persistencia
        DataService.init(this);

        // Registrar comando /wallet con verificación
        if (getCommand("wallet") == null) {
            getLogger().severe("ERROR: comando /wallet NO encontrado. Revisar plugin.yml");
        } else {
            getLogger().info("Comando /wallet registrado correctamente");
            getCommand("wallet").setExecutor(new WalletCommand());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MinecraftWeb3Plugin deshabilitado");
    }
}
