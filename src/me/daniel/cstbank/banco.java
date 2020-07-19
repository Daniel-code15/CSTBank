package me.daniel.cstbank;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class banco implements CommandExecutor {

    private static final T_Config jogador = new T_Config(Main.getPlugin(Main.class), "banco.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        Player player = (Player)sender;

        if(args.length == 0) {
            player.sendMessage("§7Uso Incorreto, Use §c/banco <status/guardar/sacar> §7para utilizar o banco virtual");
            return true;
        }
        
        if(args[0].toString() != "status" && args[0] != "sacar" && args[0] != "guardar") {
            player.sendMessage("§7Uso Incorreto, Use §c/banco <status/guardar/sacar> §7para utilizar o banco virtual");
            return true;
        }
        
        if(args[1].length() == 0) {
            player.sendMessage("§7Uso Incorreto, Use §c/banco <status/guardar/sacar> §7para utilizar o banco virtual");
            return true;
        }
        
        final String prefix = args[0];
        
        final int atual = jogador.getConfig().getInt("Player."+player.getName());
        
        if(prefix.equalsIgnoreCase("guardar")) {
            if(args[1].isEmpty())return true;
            final Integer value = convertToPrimitive(args[1]);
            if(value == null || value < 1) {
                player.sendMessage("§7Especifique valores reais");
                return true;
            }

            final double playerBalance = Main.econ.getBalance(player);

            if(value > playerBalance) {
                player.sendMessage("§cVocê não tem dinheiro suficiente para guardar.");
                return true;
            }


            //Main.econ.depositPlayer(player, value);
            Main.econ.withdrawPlayer(player, value);
            jogador.set("Player."+player.getName(), atual+value);
            jogador.saveConfig();
            player.sendMessage("§eVocê Guardou §c"+value+"§eCoins Na Sua Conta Do Banco.");
            return true;
        }

        if(prefix.equalsIgnoreCase("sacar")) {
            if(args[1].isEmpty())return true;
            final Integer value = convertToPrimitive(args[1]);
            //final double playerBalance = Main.econ.getBalance(player);

            if(value == null || value < 1) {
                player.sendMessage("§7Especifique valores reais");
                return true;
            }

            if(value > atual) {
                player.sendMessage("§cVocê não tem dinheiro suficiente na sua conta para este saque.");
                return true;
            } 
            
            //Main.econ.depositPlayer(player, s);
            Main.econ.depositPlayer(player, value);
            jogador.set("Player."+player.getName(), atual-value);
            jogador.saveConfig();
            player.sendMessage("§eVocê Retirou §c"+value+"§eCoins Da Sua Conta Do Banco.");
            return true;
        }

        if(prefix.equalsIgnoreCase("status")) {
            player.sendMessage("§eVocê Tem Atualmente §c"+atual+"§eCoins no banco");
            return true;
        }else {player.sendMessage("§e§7Uso Incorreto, Use §c/banco <status/guardar/sacar> §7para utilizar o banco virtual");}
        return true;
    }

    public Integer convertToPrimitive(String unstable) {
        try {
            return Integer.valueOf(unstable);
        } catch (Exception e) {
            return null;
        }
    }
}