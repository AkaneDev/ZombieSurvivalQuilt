package net.akane.zombiesurvival.event;

import net.akane.zombiesurvival.ZombieSurvival;
import net.akane.akanedata.DataArray;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

import static net.akane.zombiesurvival.commands.NullIfy.players;

public class PlayerHealthHandler {
    private static final Path DATASAVES_DIR = Paths.get("DataSaves", "ZombieSurvival");
    private static final Path DATA_FILE_PATH = DATASAVES_DIR.resolve("ZombieSurvival.PlayerData.ZombieData");
    private static final File DATA_FILE = new File(String.valueOf(DATA_FILE_PATH));
    public static void GenerateFolderAndFiles() {
        try {
            Files.createDirectories(DATASAVES_DIR);
            boolean file = DATA_FILE.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void savePlayerHealthData(double amount, UUID playerUUID, PlayerEntity player) {
        try {
            Files.createDirectories(DATASAVES_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            boolean file = DATA_FILE.createNewFile();
            FileWriter myWriter = new FileWriter(DATA_FILE);
            myWriter.write(String.format("PlayerName: %s, UUID: %s, HealthAmount: %s", player.getDisplayName().getString(), playerUUID.toString(), amount));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataArray readPlayerHealthData(PlayerEntity player) {
        try {
            Scanner scanner = new Scanner(DATA_FILE);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split the line into parts based on comma separator
                String PlayerName = "null";
                UUID playerUUID = null;
                float healthAmount = 0.0f;
                String[] parts = line.split(",");
                for (String part : parts) {
                    // Split each part into key-value pairs based on colon separator
                    String[] keyValue = part.split(":");
                    // Trim leading and trailing whitespace from keys and values
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    // Print the value (or store it in a variable)
                    if (key.equals("PlayerName")) {
                        PlayerName = value;
                        System.out.println("Player Name: " + value);
                    } else if (key.equals("UUID")) {
                        playerUUID = UUID.fromString(value);
                        System.out.println("Player UUID: " + value);
                    } else if (key.equals("HealthAmount")) {
                        healthAmount = Float.parseFloat(value);
                        System.out.println("Health Amount: " + value);
                    }
                }
                ZombieSurvival.LOGGER.info(player.getDisplayName().getString());
                if (PlayerName.equals(player.getDisplayName().getString()) || Objects.equals(playerUUID, player.getUuid())) {
                    return new DataArray(PlayerName, playerUUID, healthAmount);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		if (players.contains(player.getDisplayName())) {
			return null;
		}
		else {
			return new DataArray("FakePlayer", UUID.randomUUID(), 20.0f);
		}
    }

	public static DataArray readNullPlayer() {
        return null;
	}
}
