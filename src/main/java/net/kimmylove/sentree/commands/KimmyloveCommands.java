package net.kimmylove.sentree.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kimmylove.sentree.commands.AllowedUsersState;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.UUID;

public class KimmyloveCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("kimmylove")

                        // Let anyone see/use list (or you can gate it too)
                        .then(CommandManager.literal("list")
                                .executes(KimmyloveCommands::list))

                        // Only OP can allow
                        .then(CommandManager.literal("allow")
                                .requires(src -> src.hasPermissionLevel(2))
                                .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                                        .executes(KimmyloveCommands::allow)))

                        // Only OP can deny
                        .then(CommandManager.literal("deny")
                                .requires(src -> src.hasPermissionLevel(2))
                                .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                                        .executes(KimmyloveCommands::deny)))
        );
    }

    private static int allow(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        MinecraftServer server = ctx.getSource().getServer();
        AllowedUsersState state = AllowedUsersState.get(server);

        Collection<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(ctx, "player");

        int added = 0;
        for (GameProfile p : profiles) {
            UUID id = p.getId();
            if (id != null && state.add(id)) {
                added++;
            }
        }

        int finalAdded = added;
        ctx.getSource().sendFeedback(
                () -> Text.literal("kimmylove: allowed " + finalAdded + " player(s)."),
                true
        );
        return added;
    }

    private static int deny(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        MinecraftServer server = ctx.getSource().getServer();
        AllowedUsersState state = AllowedUsersState.get(server);

        Collection<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(ctx, "player");

        int removed = 0;
        int blocked = 0;

        for (GameProfile p : profiles) {
            UUID id = p.getId();
            if (id == null) continue;

            if (AllowedUsersState.isDefault(id)) {
                blocked++;
                continue;
            }

            if (state.remove(id)) {
                removed++;
            }
        }

        int fRemoved = removed;
        int fBlocked = blocked;

        ctx.getSource().sendFeedback(
                () -> Text.literal("kimmylove: removed " + fRemoved + " player(s)"
                        + (fBlocked > 0 ? " (kept " + fBlocked + " default UUIDs)" : "")
                        + "."),
                true
        );
        return removed;
    }

    private static int list(CommandContext<ServerCommandSource> ctx) {
        AllowedUsersState state = AllowedUsersState.get(ctx.getSource().getServer());

        StringBuilder sb = new StringBuilder("kimmylove allowed UUIDs (" + state.getAllowed().size() + "):");
        for (UUID id : state.getAllowed()) {
            sb.append("\n- ").append(id);
        }

        ctx.getSource().sendFeedback(() -> Text.literal(sb.toString()), false);
        return state.getAllowed().size();
    }
}
