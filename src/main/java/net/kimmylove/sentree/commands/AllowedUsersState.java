package net.kimmylove.sentree.commands;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AllowedUsersState extends PersistentState {

    private static final String SAVE_KEY = "kimmylove_allowed_users";
    private static final String NBT_ALLOWED = "allowed";

    // Defaults that are ALWAYS allowed
    private static final Set<UUID> DEFAULT_ALLOWED = Set.of(
            UUID.fromString("c52855ed-a8f6-4a15-89bd-394dc8a15294"),
            UUID.fromString("42090b34-c494-4587-a385-d4d506656355")
    );

    private final Set<UUID> allowed = new HashSet<>();

    // New state (fresh world or no save yet)
    public AllowedUsersState() {
        allowed.addAll(DEFAULT_ALLOWED);
    }

    // Create a brand new state instance
    public static AllowedUsersState createNew() {
        return new AllowedUsersState();
    }

    // Load from disk
    public static AllowedUsersState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        AllowedUsersState state = new AllowedUsersState(); // includes defaults already

        if (nbt.contains(NBT_ALLOWED, NbtElement.LIST_TYPE)) {
            NbtList list = nbt.getList(NBT_ALLOWED, NbtElement.STRING_TYPE);
            for (int i = 0; i < list.size(); i++) {
                try {
                    state.allowed.add(UUID.fromString(list.getString(i)));
                } catch (IllegalArgumentException ignored) { }
            }
        }

        // enforce defaults even if file is missing them
        state.allowed.addAll(DEFAULT_ALLOWED);
        return state;
    }

    // Save to disk (1.21.1 signature)
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = new NbtList();
        for (UUID id : allowed) {
            list.add(NbtString.of(id.toString()));
        }
        nbt.put(NBT_ALLOWED, list);
        return nbt;
    }

    // Access helper
    public static AllowedUsersState get(MinecraftServer server) {
        ServerWorld overworld = server.getWorld(World.OVERWORLD);
        if (overworld == null) throw new IllegalStateException("Overworld is null");

        PersistentStateManager psm = overworld.getPersistentStateManager();

        // NOTE: uses PersistentState.Type (inner class), not PersistentStateType
        Type<AllowedUsersState> type = new Type<>(
                AllowedUsersState::createNew,
                AllowedUsersState::createFromNbt,
                null
        );

        return psm.getOrCreate(type, SAVE_KEY);
    }

    public static boolean isDefault(UUID uuid) {
        return DEFAULT_ALLOWED.contains(uuid);
    }

    public boolean isAllowed(UUID uuid) {
        return allowed.contains(uuid);
    }

    public Set<UUID> getAllowed() {
        return Collections.unmodifiableSet(allowed);
    }

    public boolean add(UUID uuid) {
        boolean changed = allowed.add(uuid);
        if (changed) markDirty();
        return changed;
    }

    public boolean remove(UUID uuid) {
        if (DEFAULT_ALLOWED.contains(uuid)) return false; // can't remove defaults
        boolean changed = allowed.remove(uuid);
        if (changed) markDirty();
        return changed;
    }
}
