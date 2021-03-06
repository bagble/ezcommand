package me.kingcjy.ezcommand.minecraft.command.definition;

import me.kingcjy.ezcommand.annotations.Component;

import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Component
public class PlayerTypeDefinition extends MinecraftCommandTypeDefinition {

    public static final String PATTERN = "[^\\s]*";
    public static final Class<?> TYPE = Player.class;
    public static final String TYPE_STRING = "Player";

    public PlayerTypeDefinition() {
        super(TYPE, TYPE_STRING, PATTERN);
    }

    @Override
    public Object transform(CommandArgument commandArgument, String command) {
        Player player = Bukkit.getPlayer(command);
        return player;
    }
}
