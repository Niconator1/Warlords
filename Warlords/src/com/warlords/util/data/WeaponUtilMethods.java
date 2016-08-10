package com.warlords.util.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import com.warlords.util.data.types.Weapon;

public class WeaponUtilMethods {
	public static void save(ArrayList<Weapon> wl, File file, String path) {
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
			for (Weapon w : wl) {
				String s = "{";
				s += w.getTitle() + ",";
				s += w.getKlass() + ",";
				s += w.getKat() + ",";
				s += w.getType() + ",";
				s += w.getDmin() + ",";
				s += w.getDmax() + ",";
				s += w.getCc() + ",";
				s += w.getCm() + ",";
				s += w.getSkill() + ",";
				s += w.getBoost() + ",";
				s += w.getHp() + ",";
				s += w.getEnergy() + ",";
				s += w.getCooldown() + ",";
				s += w.getSpeed() + ",";
				s += w.getUa() + ",";
				s += w.getUm() + ",";
				s += w.getCrafted() + ",";
				s += w.getSkillboost() + ",";
				s += w.getSkin()+"";
				s += "}";
				os.write(s + "\n");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Weapon> load(File file, String path) {
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(new FileInputStream(file.getAbsolutePath() + path + ".txt")));
			ArrayList<Weapon> result = new ArrayList<Weapon>();
			while (is.ready()) {
				String weapon = is.readLine();
				weapon = weapon.substring(1, weapon.length() - 1);
				String[] parts = weapon.split(",");
				if (parts.length > 16) {
					if (parts.length > 17) {
						if(parts.length>18){
							Weapon w = new Weapon(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
									Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
									Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]),
									Double.parseDouble(parts[9]), Double.parseDouble(parts[10]),
									Integer.parseInt(parts[11]), Double.parseDouble(parts[12]),
									Double.parseDouble(parts[13]), Integer.parseInt(parts[14]), Integer.parseInt(parts[15]),
									Boolean.parseBoolean(parts[16]), Boolean.parseBoolean(parts[17]),Integer.parseInt(parts[18]));
							result.add(w);
						}
						else{
							Weapon w = new Weapon(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
									Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
									Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]),
									Double.parseDouble(parts[9]), Double.parseDouble(parts[10]),
									Integer.parseInt(parts[11]), Double.parseDouble(parts[12]),
									Double.parseDouble(parts[13]), Integer.parseInt(parts[14]), Integer.parseInt(parts[15]),
									Boolean.parseBoolean(parts[16]), Boolean.parseBoolean(parts[17]));
							result.add(w);
						}	
					} else {
						Weapon w = new Weapon(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
								Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
								Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]),
								Double.parseDouble(parts[9]), Double.parseDouble(parts[10]),
								Integer.parseInt(parts[11]), Double.parseDouble(parts[12]),
								Double.parseDouble(parts[13]), Integer.parseInt(parts[14]), Integer.parseInt(parts[15]),
								Boolean.parseBoolean(parts[16]));
						result.add(w);
					}
				} else {
					Weapon w = new Weapon(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
							Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Integer.parseInt(parts[8]),
							Double.parseDouble(parts[9]), Double.parseDouble(parts[10]), Integer.parseInt(parts[11]),
							Double.parseDouble(parts[12]), Double.parseDouble(parts[13]), Integer.parseInt(parts[14]),
							Integer.parseInt(parts[15]));
					result.add(w);
				}
			}
			is.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
