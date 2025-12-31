package com.mycompany.minecraftweb3.command;

import com.mycompany.minecraftweb3.service.DataService;
import com.mycompany.minecraftweb3.service.LinkService;
import com.mycompany.minecraftweb3.service.BackendClient;
import com.mycompany.minecraftweb3.service.BackendStatus;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class WalletCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Solo jugadores
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede usarse dentro del juego.");
            return true;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        // /wallet
        if (args.length == 0) {
            player.sendMessage("§aWallet Web3");
            player.sendMessage("§7- /wallet link");
            player.sendMessage("§7- /wallet status");
            return true;
        }

        // /wallet link
        if (args[0].equalsIgnoreCase("link")) {

            String token = LinkService.generateToken(uuid);
            DataService.setToken(uuid, token);

            String url = "http://localhost:3001/link?token=" + token + "&uuid=" + uuid.toString();

            Component message
                    = Component.text("Vinculá tu wallet acá: ", NamedTextColor.GREEN)
                            .append(
                                    Component.text(url, NamedTextColor.AQUA)
                                            .clickEvent(ClickEvent.openUrl(url))
                            );

            player.sendMessage(Component.text("Vinculación de Wallet Web3", NamedTextColor.GREEN));
            player.sendMessage(Component.text("Abrí este enlace:", NamedTextColor.GRAY));
            player.sendMessage(message);

            return true;
        }

        // /wallet status
        if (args[0].equalsIgnoreCase("status")) {

            String token = DataService.getToken(uuid);

            if (token == null) {
                player.sendMessage("§cNo tenés un token activo. Usá /wallet link");
                return true;
            }

            BackendStatus status = BackendClient.checkStatus(token);

            switch (status.state) {
                case LINKED:
                    DataService.setLinked(uuid, true);
                    player.sendMessage("§aWallet vinculada correctamente");
                    player.sendMessage("§7Wallet: §b" + status.wallet);
                    break;

                case NOT_LINKED:
                    player.sendMessage("§eWallet aún no vinculada");
                    break;

                case ERROR:
                    player.sendMessage("§cError consultando backend");
                    break;
            }

            return true;
        }

        // Comando desconocido
        player.sendMessage("§cUso: /wallet [link|status]");
        return true;
    }
}
