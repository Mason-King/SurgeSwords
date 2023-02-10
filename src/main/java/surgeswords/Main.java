package surgeswords;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import surgeswords.Commands.SwordCommand;
import surgeswords.Events.SwordEvents;
import surgeswords.Storage.Database;
import surgeswords.Storage.SQLite;
import surgeswords.Utils.GemUtils;

public final class Main extends JavaPlugin {

//    inquisitive
//    nightfall: Chance to get a wither rose from killing mobs
//    intensity (1-3): gives you the chance to earn strength 1, 2, or 3 for 7 seconds (random proc based on level)
//    gem pouch: Chance to get a random amount of gems
//    money pouch: Chance to get a random amount of money
//    haste: boost haste
//    Looting: Chance to drop extra loot
//    annihilation (1-5): gives you a chance to kill multiple mobs , level 5 will give chance to kill full stack
    private Database db;

    private skysurge.net.Database.Database database;
    private GemUtils gemUtils;

    private NamespacedKey swordKey = new NamespacedKey(this, "mobsword");

    private NamespacedKey levelKey = new NamespacedKey(this, "level");
    private NamespacedKey xpKey = new NamespacedKey(this, "xp");

    private NamespacedKey inquisitiveKey = new NamespacedKey(this, "inquisitive");
    private NamespacedKey nightfallKey = new NamespacedKey(this, "nightfall");
    private NamespacedKey intensityKey = new NamespacedKey(this, "intensity");
    private NamespacedKey gemPouchKey = new NamespacedKey(this, "gempouch");
    private NamespacedKey moneyPouchKey = new NamespacedKey(this, "moneypouch");
    private NamespacedKey hasteKey = new NamespacedKey(this, "haste");
    private NamespacedKey lootingKey = new NamespacedKey(this, "looting");
    private NamespacedKey annihilationKey = new NamespacedKey(this, "annihilation");

    private NamespacedKey inquisitiveEnabledKey = new NamespacedKey(this, "inquisitiveEnabled");
    private NamespacedKey nightfallEnabledKey = new NamespacedKey(this, "nightFallEnabled");
    private NamespacedKey intensityEnabledKey = new NamespacedKey(this, "intensityEnabled");
    private NamespacedKey gemPouchEnabledKey = new NamespacedKey(this, "gemPouchEnabled");
    private NamespacedKey moneyPouchEnabledKey = new NamespacedKey(this, "moneyPouchEnabled");
    private NamespacedKey hasteEnabledKey = new NamespacedKey(this, "hasteEnabled");
    private NamespacedKey lootingEnabledKey = new NamespacedKey(this, "lootingEnabled");
    private NamespacedKey annihilationEnabledKey = new NamespacedKey(this, "annihilationEnabled");

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.database = new skysurge.net.Database.Database("mongodb+srv://Core:ciWECEGO7EEr9b83@skysurge.yjqzj2q.mongodb.net/?retryWrites=true&w=majority");
        this.db = new SQLite(this);
        this.db.load();


        this.gemUtils = new GemUtils(this);
        new SwordCommand(this);
        new SwordEvents(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public NamespacedKey getSwordKey() {
        return swordKey;
    }

    public NamespacedKey getLevelKey() {
        return levelKey;
    }

    public NamespacedKey getXpKey() {
        return xpKey;
    }

    public NamespacedKey getInquisitiveKey() {
        return inquisitiveKey;
    }

    public NamespacedKey getNightfallKey() {
        return nightfallKey;
    }

    public NamespacedKey getIntensityKey() {
        return intensityKey;
    }

    public NamespacedKey getGemPouchKey() {
        return gemPouchKey;
    }

    public NamespacedKey getMoneyPouchKey() {
        return moneyPouchKey;
    }

    public NamespacedKey getHasteKey() {
        return hasteKey;
    }

    public NamespacedKey getLootingKey() {
        return lootingKey;
    }

    public NamespacedKey getAnnihilationKey() {
        return annihilationKey;
    }

    public NamespacedKey getInquisitiveEnabledKey() {
        return inquisitiveEnabledKey;
    }

    public NamespacedKey getNightfallEnabledKey() {
        return nightfallEnabledKey;
    }

    public NamespacedKey getIntensityEnabledKey() {
        return intensityEnabledKey;
    }

    public NamespacedKey getGemPouchEnabledKey() {
        return gemPouchEnabledKey;
    }

    public NamespacedKey getMoneyPouchEnabledKey() {
        return moneyPouchEnabledKey;
    }

    public NamespacedKey getHasteEnabledKey() {
        return hasteEnabledKey;
    }

    public NamespacedKey getLootingEnabledKey() {
        return lootingEnabledKey;
    }

    public NamespacedKey getAnnihilationEnabledKey() {
        return annihilationEnabledKey;
    }

    public Database getDb() {
        return db;
    }

    public GemUtils getGemUtils() {
        return gemUtils;
    }
}
