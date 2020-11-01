package com.mcml.space.util;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public abstract class Perms {
    private static Permission common;

    public static void bind(String commonPerm) {
        bind(new Permission(commonPerm));
    }

    public static void bind(Permission commonPerm) {
        common = commonPerm;
    }

    public static boolean has(CommandSender sender) {
        return sender.isOp() || sender.hasPermission(common);
    }
}
