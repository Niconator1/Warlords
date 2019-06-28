package recreate.data;

import com.google.gson.Gson;
import recreate.main.Warlords;
import recreate.weapon.Weapon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WarlordsDataManager
{
    private final File weaponFolder;
    private final File playerFolder;
    private final Gson gson = new Gson();

    public WarlordsDataManager(final File configFolder)
    {
        this.weaponFolder = new File(configFolder.getAbsolutePath() + "\\weapon\\");
        this.playerFolder = new File(configFolder.getAbsolutePath() + "\\player\\");
    }

    private void saveWeapons(final UUID uuid,
        final WarlordsPlayer warlordsPlayer)
    {
        final String json = this.gson.toJson(warlordsPlayer.getWeaponInventory());
        writeJson(new File(this.weaponFolder.getAbsolutePath() + uuid + ".json"), json);
    }

    public void savePlayerConfig(final UUID uuid,
        final WarlordsPlayer warlordsPlayer)
    {
        final String json = this.gson.toJson(warlordsPlayer);
        writeJson(new File(this.playerFolder.getAbsolutePath() + uuid + ".json"), json);
    }

    public WarlordsPlayer readData(final UUID uuid)
    {
        WarlordsPlayer warlordsPlayer = null;
        if (this.playerFolder.exists())
        {

            final File playerFile = new File(this.playerFolder.getAbsolutePath() + uuid + ".json");
            if (playerFile.exists())
            {
                final String json = readJson(new File(this.playerFolder.getAbsolutePath() + uuid + ".json"));
                warlordsPlayer = this.gson.fromJson(json, WarlordsPlayer.class);
            }
            final File weaponFile = new File(this.weaponFolder.getAbsolutePath() + uuid + ".json");
            if (weaponFile.exists())
            {
                final String json = readJson(new File(this.weaponFolder.getAbsolutePath() + uuid + ".json"));
                final ArrayList<Weapon> weaponList = this.gson.fromJson(json, ArrayList.class);
                if (Objects.nonNull(warlordsPlayer))
                {
                    warlordsPlayer.setWeaponInventory(weaponList);
                }
            }

        }
        return warlordsPlayer;
    }

    private String readJson(final File file)
    {
        if (file.exists())
        {
            try
            {
                final BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(file
                    ));
                String json = "";
                String line;

                while ((line = bufferedReader.readLine()) != null)
                {
                    json += line;
                }
                bufferedReader.close();
                return json;
            } catch (final IOException e)
            {
                Warlords.get().getLogger().info("File saving failed for: " + file.getAbsolutePath());
                Warlords.get().getLogger().info("" + e.getStackTrace());

            }
        }
        return null;
    }

    public void writeJson(final File file,
        final String json)
    {
        file.getParentFile().mkdirs();
        try
        {
            final BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(file
                ));
            bufferedWriter.write(json);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (final IOException e)
        {
            Warlords.get().getLogger().info("File saving failed for: " + file.getAbsolutePath());
            Warlords.get().getLogger().info("" + e.getStackTrace());
        }
    }
}
