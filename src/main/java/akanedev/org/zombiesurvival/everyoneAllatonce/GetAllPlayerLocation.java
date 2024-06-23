package akanedev.org.zombiesurvival.everyoneAllatonce;

import net.akane.akaneloging.akanelogging;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAllPlayerLocation {

	public static void getLocation(MinecraftServer server) {
		String ServerLevelNames = server.getWorlds().toString();
		String IPaddress = server.getServerIp();
		String worldNameReal = "";

		if (ServerLevelNames != null) {
			String patternString = "ServerLevel\\[(.*?)\\]"; // Regular expression pattern

			// Create a Pattern object
			Pattern pattern = Pattern.compile(patternString);

			// Create a Matcher object
			Matcher matcher = pattern.matcher(ServerLevelNames);

			// Find the first occurrence of the pattern in the input string
			if (matcher.find()) {
				// Print the matched substring
				System.out.println("Found: " + matcher.group(1));
				if (IPaddress == null) {
					IPaddress = "null";
				}
				if (!Objects.equals(IPaddress.toLowerCase(), "null".toLowerCase())) {
					worldNameReal = matcher.group(1) + " " + IPaddress;
				}
				else {
					worldNameReal = matcher.group(1) + " Singleplayer";
				}
			}
		}
		else {
			if (!Objects.equals(IPaddress.toLowerCase(), "null".toLowerCase())) {
				worldNameReal = "No World Name" + " " + IPaddress;
			}
			else {
				worldNameReal = "No World Name" + " Singleplayer";
			}
		}

		//String worldNamePossible = server.getOverworld().toServerWorld().getRegistryKey().getValue().toString();

		for (PlayerEntity p : server.getPlayerManager().getPlayerList()) {
			try {
				String playerName = p.getName().getString();
				String worldName = URLEncoder.encode(worldNameReal, "UTF-8"); // Encode the world name
				String playerPos = URLEncoder.encode(p.getPos().toString(), "UTF-8"); // Encode the player position
				String url = "http://ec2-3-27-224-161.ap-southeast-2.compute.amazonaws.com:8001/update_data" +
					"?key=" + URLEncoder.encode(playerName, "UTF-8") +
					"&name=" + worldName +
					"&pos=" + playerPos;
				String response = sendPostRequest(url);
				akanelogging.yeetinvoid(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String sendPostRequest(String url) throws IOException {
		// Create URL object
		URL obj = new URL(url);

		// Open connection
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Set the request method to POST
		con.setRequestMethod("POST");

		// Set request headers
		con.setRequestProperty("Content-Type", "application/json");

		// Get the response code
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		// Read the response from the server
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Return the response
		return response.toString();
	}
}
