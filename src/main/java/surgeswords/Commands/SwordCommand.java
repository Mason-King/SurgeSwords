package surgeswords.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import surgeswords.Main;
import surgeswords.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SwordCommand implements CommandExecutor {

    private Main main;

    public SwordCommand(Main main) {
        this.main = main;

        main.getCommand("mobsword").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0) {
            //invalid usage
        } else {
            if(args[0].equalsIgnoreCase("give")) {
                Player target = p;
                if(args.length == 2) {
                    target = Bukkit.getPlayer(args[1]);
                }

                ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
                ItemMeta itemMeta = sword.getItemMeta();
                itemMeta.setDisplayName(Utils.color("&f&lMob Sword &7| (1)"));
                List<String> lore = new ArrayList<>();

                double percentage =  ((double) (0 / (int) (Math.pow(1, 2) * 100) * 100));

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
                lore.add(Utils.color("&7Kill mobs to gain gems."));
                lore.add(Utils.color("&7Use gems to upgrade your hoe"));
                lore.add(Utils.color("&7"));
                lore.add(Utils.color("&f&lProgress &7| "+Math.floor(percentage)+"%"));
                lore.add(Utils.color("&a"+greenBar+"&c"+redBar+""));
                lore.add(Utils.color("&7"));
                lore.add(Utils.color("&7&o(( Right-click to upgrade ))"));

                itemMeta.setLore(lore);
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

                //set the data in the pdc
                pdc.set(main.getSwordKey(), PersistentDataType.STRING, "true");
                pdc.set(main.getLevelKey(), PersistentDataType.INTEGER, 1);
                pdc.set(main.getXpKey(), PersistentDataType.INTEGER, 0);

                sword.setItemMeta(itemMeta);

                target.getInventory().addItem(sword);

            }
        }
        return false;
    }
}
