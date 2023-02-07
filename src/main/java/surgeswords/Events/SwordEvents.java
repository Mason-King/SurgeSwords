package surgeswords.Events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.apache.commons.lang.StringUtils;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import surgeswords.Main;
import surgeswords.Utils.Utils;

import javax.annotation.meta.Exclusive;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwordEvents implements Listener {

    private Main main;
    private String[] upgrades = {"inquisitive", "nightfall", "intensity", "gem pouch", "money pouch", "haste", "looting", "annihilation"};

    public SwordEvents(Main main) {
        this.main = main;

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();

        ItemStack sword = p.getInventory().getItemInMainHand();
        ItemMeta im = sword.getItemMeta();
        PersistentDataContainer pdc = im.getPersistentDataContainer();

        if(pdc.get(main.getSwordKey(), PersistentDataType.STRING).equals("true")) {
            e.setDamage(20);

            int level = pdc.getOrDefault(main.getLevelKey(), PersistentDataType.INTEGER, 1);
            int xp = pdc.getOrDefault(main.getXpKey(), PersistentDataType.INTEGER, 0);

            xp += 5;
            int xpNeeded = (int) (Math.pow(level, 2) * 1000);
            if(xp >= xpNeeded) {
                //level up!
                level++;
                xp = 0;
                im.setDisplayName(Utils.color("&f&lHarvester Hoe &7| ("+level+")"));

                pdc.set(main.getLevelKey(), PersistentDataType.INTEGER, level);

                p.sendTitle(Utils.color("&b&lHoe Upgraded!"), Utils.color("&7Your sword has been upgraded to level " + level), 10, 50, 20);
            }

            pdc.set(main.getXpKey(), PersistentDataType.INTEGER, xp);
            im.setLore(generateLore(sword, xpNeeded));
            sword.setItemMeta(im);

            Random rand = new Random();
            int chance = rand.nextInt(100);

            //makes a 5% chance
            if(chance < 5) {
                if(this.main.getDb().exists("playerData", "uuid", p.getUniqueId().toString())) {
                    this.main.getDb().execute("REPLACE into playerData (uuid, gems) VALUES ('"+ p.getUniqueId().toString() + "', '" + (this.main.getGemUtils().getGems(p) + 1) + "')");
                    p.sendMessage(Utils.color("&f&lSkySurge &7| You have received 1 gem from harvesting sugar cane."));
                } else return;
            }
        } else return;
    }

    public List<String> generateLore(ItemStack stack, int xpNeeded) {
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7Harvest crops to gain gems."));
        lore.add(Utils.color("&7Use gems to upgrade your hoe"));
        lore.add(Utils.color("&7"));

        PersistentDataContainer dc = stack.getItemMeta().getPersistentDataContainer();
        double percentage =  ((double) (dc.get(main.getXpKey(), PersistentDataType.INTEGER)) / xpNeeded * 100);

        lore.add(Utils.color("&f&lProgress &7| "+Math.floor(percentage)+"%"));

        String greenBar = "", redBar = "";
        int bars = 50;
        int barPercentage = 100 / bars;

        while(percentage > barPercentage) { // This will execute the code below each time the entered percentage value (for example 60) is greater than the percentage for one bar.
            percentage -= barPercentage;
            greenBar += "|";
            bars -= 1;
        }

        while(bars > 0) {
            redBar += "|";
            bars -= 1;
        }

        lore.add(Utils.color("&a"+greenBar+"&c"+redBar+""));
        lore.add(Utils.color("&7"));
        for(String s : upgrades) {
            NamespacedKey upgradeKey = new NamespacedKey(main, s.replace(" ", ""));
            int level = dc.getOrDefault(upgradeKey, PersistentDataType.INTEGER, 0);
            if(level > 0) {
                lore.add(Utils.color("&7&l| &b" + StringUtils.capitalize(s) + " &7(" + level + ")"));
            }

        }
        lore.add("");
        lore.add(Utils.color("&7&o(( Right-click to upgrade ))"));

        return lore;
    }
}
