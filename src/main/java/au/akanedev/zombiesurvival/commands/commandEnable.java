package au.akanedev.zombiesurvival.commands;

import au.akanedev.zombiesurvival.Zombiesurvival;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class commandEnable {
    commandEnable() {
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerEnable() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("enable").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).executes((context) -> {
            ServerCommandSource source = (ServerCommandSource) context.getSource();
            MinecraftServer server = source.getServer();
            source.sendFeedback(() -> {
                Zombiesurvival.spawnHelper.enabled = true;
                Zombiesurvival.SaveConfig();
                return Text.literal("Enabled Zombie Survival Spawning");
            }, true);
            return 1;
        });
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerDisable() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("disable").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).executes((context) -> {
            ServerCommandSource source = (ServerCommandSource) context.getSource();
            MinecraftServer server = source.getServer();
            source.sendFeedback(() -> {
                Zombiesurvival.spawnHelper.enabled = false;
                Zombiesurvival.SaveConfig();
                return Text.literal("Disabled Zombie Survival Spawning");
            }, true);
            return 1;
        });
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerSetSpawnTime() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("SetTime").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).then(CommandManager.argument("Amountoftime", FloatArgumentType.floatArg())
                .executes(ctx -> {
//                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                    float amount = FloatArgumentType.getFloat(ctx, "Amountoftime");
                    ServerCommandSource source = (ServerCommandSource) ctx.getSource();
                    MinecraftServer server = source.getServer();
                    source.sendFeedback(() -> {
                        Zombiesurvival.spawnHelper.maxtimeTick = amount;
                        Zombiesurvival.spawnHelper.current = amount * 60;
                        Zombiesurvival.SaveConfig();
                        return Text.literal("Time Set to: " + amount + " ticks");
                    }, true);
                    return 1;
                }));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerSetSpawnTimeTicks() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("SetTimeTicks").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).then(CommandManager.argument("AmountoftimeTicks", IntegerArgumentType.integer())
                .executes(ctx -> {
//                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                    int amount = IntegerArgumentType.getInteger(ctx, "AmountoftimeTicks");
                    ServerCommandSource source = (ServerCommandSource) ctx.getSource();
                    MinecraftServer server = source.getServer();
                    source.sendFeedback(() -> {
                        Zombiesurvival.spawnHelper.maxtimeTick = amount / 60;
                        Zombiesurvival.spawnHelper.current = amount;
                        Zombiesurvival.SaveConfig();
                        return Text.literal("Time Set to: " + amount + " ticks");
                    }, true);
                    return 1;
                }));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerOrbitalStrike() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("OrbitalStrike").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).then(CommandManager.argument("x", DoubleArgumentType.doubleArg())
                    .then(CommandManager.argument("z", DoubleArgumentType.doubleArg())
                        .executes(ctx -> {
        //                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                                double posX = DoubleArgumentType.getDouble(ctx, "x");
                                double posZ = DoubleArgumentType.getDouble(ctx, "z");
                                ServerCommandSource source = (ServerCommandSource) ctx.getSource();
                                MinecraftServer server = source.getServer();
                                source.sendFeedback(() -> {
                                    for (int i = 319; i >= -64; i--) {
                                        TntEntity tnt = new TntEntity(ctx.getSource().getWorld(), posX, i, posZ, null);
                                        tnt.setFuse(0);
                                        ctx.getSource().getWorld().spawnEntity(tnt);
                                    }
                                    return Text.literal("Orbital Strike at: " + posX + ", " + ", " + posZ);
                                }, true);
                                return 1;
                        })));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerRaycastOrbitalStrike() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("RaycastOrbitalStrike").requires((cs) -> {
            return cs.hasPermissionLevel(2);
        })).executes(ctx -> {
            ServerCommandSource source = (ServerCommandSource) ctx.getSource();
            MinecraftServer server = source.getServer();
            source.sendFeedback(() -> {
                // Perform raycast
                Vec3d startPos = source.getPosition();
                Vec3d lookVec = source.getEntity().getRotationVec(1);
                Vec3d endPos = startPos.add(lookVec.multiply(1000)); // Raycast range of 100 blocks
                HitResult hitResult = source.getWorld().raycast(new RaycastContext(startPos, endPos, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, source.getEntity()));

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    double posX = blockHitResult.getBlockPos().getX();
                    double posZ = blockHitResult.getBlockPos().getZ();

                    if (isWithin10Blocks(source.getPosition().x, source.getPosition().z, posX, posZ)) {
                        return Text.literal("To Close to activator.");
                    }

                    for (int i = 319; i >= -64; i--) {
                        TntEntity tnt = new TntEntity(((ServerCommandSource) ctx.getSource()).getWorld(), posX, i, posZ, null);
                        tnt.setFuse(0);
                        ((ServerCommandSource) ctx.getSource()).getWorld().spawnEntity(tnt);
                    }
                    return Text.literal("Orbital Strike at: " + posX + ", " + posZ);
                } else {
                    return Text.literal("No block hit within range.");
                }
            }, true);
            return 1;
        });
    }

    public static boolean isWithin10Blocks(double x1, double y1, double x2, double y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance <= 5;
    }
}
