package com.warlords.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

public class AllyUtilMethods {

	public static void save(ArrayList<UUID> wl, File file,String path) {
		File f = new File(file + path+".txt");
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
					new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath() + path+".txt")));
			for (UUID w : wl) {
				String s = "{";
				s += w.toString() + "";
				s += "}";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<UUID> load(File file, String path) {
		File f = new File(file + path+".txt");
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
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + path+".txt")));
			ArrayList<UUID> result = new ArrayList<UUID>();
			while (is.ready()) {
				String uuid = is.readLine();
				uuid = uuid.substring(1, uuid.length() - 1);
				result.add(UUID.fromString(uuid));
			}
			is.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
