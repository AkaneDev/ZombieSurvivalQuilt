package au.akanedev.zombiesurvival.commands;

import au.akanedev.zombiesurvival.Zombiesurvival;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class commandEnable {
    commandEnable() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerEnable() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("enable").requires((cs) -> {
            return cs.hasPermission(2);
        })).executes((context) -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            MinecraftServer server = source.getServer();
            source.sendSuccess(() -> {
                Zombiesurvival.spawnHelper.enabled = true;
                Zombiesurvival.SaveConfig();
                return Component.literal("Enabled Zombie Survival Spawning");
            }, true);
            return 1;
        });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerDisable() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("disable").requires((cs) -> {
            return cs.hasPermission(2);
        })).executes((context) -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            MinecraftServer server = source.getServer();
            source.sendSuccess(() -> {
                Zombiesurvival.spawnHelper.enabled = false;
                Zombiesurvival.SaveConfig();
                return Component.literal("Disabled Zombie Survival Spawning");
            }, true);
            return 1;
        });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerSetSpawnTime() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("SetTime").requires((cs) -> {
            return cs.hasPermission(2);
        })).then(Commands.argument("Amountoftime", FloatArgumentType.floatArg())
                .executes(ctx -> {
//                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                    float amount = FloatArgumentType.getFloat(ctx, "Amountoftime");
                    CommandSourceStack source = (CommandSourceStack)ctx.getSource();
                    MinecraftServer server = source.getServer();
                    source.sendSuccess(() -> {
                        Zombiesurvival.spawnHelper.maxtimeTick = amount;
                        Zombiesurvival.spawnHelper.current = amount * 60;
                        Zombiesurvival.SaveConfig();
                        return Component.literal("Time Set to: " + amount + " ticks");
                    }, true);
                    return 1;
                }));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerSetSpawnTimeTicks() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("SetTimeTicks").requires((cs) -> {
            return cs.hasPermission(2);
        })).then(Commands.argument("AmountoftimeTicks", IntegerArgumentType.integer())
                .executes(ctx -> {
//                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                    int amount = IntegerArgumentType.getInteger(ctx, "AmountoftimeTicks");
                    CommandSourceStack source = (CommandSourceStack)ctx.getSource();
                    MinecraftServer server = source.getServer();
                    source.sendSuccess(() -> {
                        Zombiesurvival.spawnHelper.maxtimeTick = amount / 60;
                        Zombiesurvival.spawnHelper.current = amount;
                        Zombiesurvival.SaveConfig();
                        return Component.literal("Time Set to: " + amount + " ticks");
                    }, true);
                    return 1;
                }));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerOrbitalStrike() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("OrbitalStrike").requires((cs) -> {
            return cs.hasPermission(2);
        })).then(Commands.argument("x", DoubleArgumentType.doubleArg())
                    .then(Commands.argument("z", DoubleArgumentType.doubleArg())
                        .executes(ctx -> {
        //                        ctx.getSource().sendSystemMessage(Component.literal("Attempting to get int"));
                                double posX = DoubleArgumentType.getDouble(ctx, "x");
                                double posZ = DoubleArgumentType.getDouble(ctx, "z");
                                CommandSourceStack source = (CommandSourceStack)ctx.getSource();
                                MinecraftServer server = source.getServer();
                                source.sendSuccess(() -> {
                                    for (int i = 319; i >= -64; i--) {
                                        PrimedTnt tnt = new PrimedTnt(ctx.getSource().getLevel(), posX, i, posZ, null);
                                        tnt.setFuse(0);
                                        ctx.getSource().getLevel().addFreshEntity(tnt);
                                    }
                                    return Component.literal("Orbital Strike at: " + posX + ", " + ", " + posZ);
                                }, true);
                                return 1;
                        })));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registerRaycastOrbitalStrike() {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("RaycastOrbitalStrike").requires((cs) -> {
            return cs.hasPermission(2);
        })).executes(ctx -> {
            CommandSourceStack source = (CommandSourceStack) ctx.getSource();
            MinecraftServer server = source.getServer();
            source.sendSuccess(() -> {
                // Perform raycast
                Vec3 startPos = source.getPosition();
                Vec3 lookVec = source.getEntity().getLookAngle();
                Vec3 endPos = startPos.add(lookVec.scale(1000)); // Raycast range of 100 blocks
                HitResult hitResult = source.getLevel().clip(new ClipContext(startPos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, source.getEntity()));

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    double posX = blockHitResult.getBlockPos().getX();
                    double posZ = blockHitResult.getBlockPos().getZ();

                    if (isWithin10Blocks(source.getPosition().x, source.getPosition().z, posX, posZ)) {
                        return Component.literal("To Close to activator.");
                    }

                    for (int i = 319; i >= -64; i--) {
                        PrimedTnt tnt = new PrimedTnt(((CommandSourceStack) ctx.getSource()).getLevel(), posX, i, posZ, null);
                        tnt.setFuse(0);
                        ((CommandSourceStack) ctx.getSource()).getLevel().addFreshEntity(tnt);
                    }
                    return Component.literal("Orbital Strike at: " + posX + ", " + posZ);
                } else {
                    return Component.literal("No block hit within range.");
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
