package com.warlords.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;

import com.warlords.main.Warlords;

public class PlayerUtil {
	public static void save(WarlordsPlayer wp, File file, String path) {
		File f = new File(file + path + ".txt");
		if (!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Fehler beim erstellen");
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter os = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath() + path + ".txt")));
			String s = "{";
			s += wp.getUUID() + ",";
			s += wp.getWeaponSlot() + ",";
			s += wp.getKlasse() + ",";
			s += wp.getMode() + ",";
			s += wp.getPlayerAttackMode() + ",";
			s += wp.getVoidShards();
			s += "}";
			os.write(s + "\n");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WarlordsPlayer getWlPlayer(File file, String path) {
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + path + ".txt")));
			WarlordsPlayer wp = null;
			while (is.ready()) {
				String param = is.readLine();
				param = param.substring(1, param.length() - 1);
				String[] parts = param.split(",");
				if (parts.length > 5) {
					wp = new WarlordsPlayer(parts[0]);
					wp.setWeaponSlot(Integer.parseInt(parts[1]));
					wp.setKlasse(parts[2]);
					wp.setMode(Boolean.parseBoolean(parts[3]));
					wp.setPlayerAttackMode(Boolean.parseBoolean(parts[4]));
					wp.setVoidShards(Integer.parseInt(parts[5]));
				} else if (parts.length > 4) {
					wp = new WarlordsPlayer(parts[0]);
					wp.setWeaponSlot(Integer.parseInt(parts[1]));
					wp.setKlasse(parts[2]);
					wp.setMode(Boolean.parseBoolean(parts[3]));
					wp.setPlayerAttackMode(Boolean.parseBoolean(parts[4]));
				}
			}
			is.close();
			return wp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isAttackingPlayers(Player shooter) {
		WarlordsPlayer wp = PlayerUtil.getWlPlayer(Warlords.getPlugin(Warlords.class).getDataFolder(),
				"/players/" + shooter.getUniqueId());
		if (wp != null) {
			return wp.getPlayerAttackMode();
		}
		return false;
	}
}
