package yt.sehrschlecht.vanillaenhancements.utils.debugging;

import com.google.gson.annotations.Since;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.schlechteutils.data.Pair;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DebugCommand implements CommandExecutor, TabExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!debug().isEnabled()) {
            sender.sendMessage("§cDebugging is disabled!");
            return true;
        }
        // ToDo make pages such as recipes paginated
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage("§7§oWarning: This command can have unexpected side effects!" +
                        " Restart your server if you want to be sure that everything is working as expected.");
                sender.sendMessage("§aReloading the config...");
                debug().reload();
                sender.sendMessage("§aSuccessfully reloaded the config!");
                return true;
            } else if(args[0].equalsIgnoreCase("generate-docs")) {
                sender.sendMessage("§aGenerating docs...");
                try {
                    debug().generateDocs();
                    sender.sendMessage("§aSuccessfully generated docs!");
                    sender.sendMessage("§aOutput: " + debug().getDocsOutput());
                } catch (IOException e) {
                    sender.sendMessage("§cFailed to generate docs!");
                    throw new RuntimeException(e);
                }
                return true;
            } else if(args[0].equalsIgnoreCase("modules")) {
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§l§nModules:");
                for (VEModule module : VanillaEnhancements.getPlugin().getModuleRegistry().getRegisteredModules()) {
                    boolean enabled = module.isEnabled();
                    TextComponent component = new TextComponent("§7- " + (enabled ? "§a" : "§c") + module.getName());
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ve-debug module " + module.getModuleKey().toString()));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                            new TextComponent("§7Key: §e" + module.getModuleKey().toString() + "\n"),
                            new TextComponent("§9Click for more information")
                    }));
                    sender.spigot().sendMessage(component);
                }
                sender.sendMessage("-------------------------------------------------");
                return true;
            } else if(args[0].equalsIgnoreCase("tickservices")) {
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§l§nTick Services:");
                if(VanillaEnhancements.getPlugin().getTickServiceExecutor().getTickServices().isEmpty()) {
                    sender.sendMessage("§cNo tick services registered!");
                } else {
                    for (TickService tickService : VanillaEnhancements.getPlugin().getTickServiceExecutor().getTickServices()) {
                        boolean enabled = tickService.moduleInstance().isEnabled();
                        TextComponent component = new TextComponent("§7- " + (enabled ? "§a" : "§c") + tickService.method().getName());
                        String serviceKey = tickService.method().getDeclaringClass().getName() + "#" + tickService.method().getName();
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ve-debug tickservice " + serviceKey));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                                new TextComponent("§7Key: §e" + serviceKey + "\n"),
                                new TextComponent("§9Click for more information")
                        }));
                        sender.spigot().sendMessage(component);
                    }
                    sender.sendMessage("-------------------------------------------------");
                }
                return true;
            } else if(args[0].equalsIgnoreCase("recipes")) {
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§l§nRecipes:");
                List<Pair<NamespacedKey, VERecipe>> recipes = VanillaEnhancements.getPlugin().getRecipeManager().getRecipes();
                if(recipes.isEmpty()) {
                    sender.sendMessage("§cNo recipes registered!");
                } else {
                    for (Pair<NamespacedKey, VERecipe> pair : recipes) {
                        VERecipe recipe = pair.getSecond();
                        boolean registered = recipe.isRegistered();
                        TextComponent component = new TextComponent("§7- " + (registered ? "§a" : "§c") + recipe.key().getKey());
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ve-debug recipe " + recipe.key().toString()));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                                new TextComponent("§7Key: §e" + recipe.key().toString() + "\n"),
                                new TextComponent("§9Click for more information")
                        }));
                        sender.spigot().sendMessage(component);
                    }
                }
                sender.sendMessage("-------------------------------------------------");
                return true;
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("module")) {
                String moduleKey = args[1];
                NamespacedKey key = NamespacedKey.fromString(moduleKey);
                if(key == null) {
                    sender.sendMessage("§cInvalid module key!");
                    return true;
                }
                VEModule module = VanillaEnhancements.getPlugin().getModuleRegistry().getModule(key);
                if(module == null) {
                    sender.sendMessage("§cModule not found!");
                    return true;
                }
                boolean enabled = module.isEnabled();
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§l§nModule: " + module.getName());
                sender.sendMessage("§7Namespace: §e" + module.getModuleKey().getNamespace());
                sender.sendMessage("§7Key: §e" + module.getModuleKey().getKey());
                sender.sendMessage("§7Enabled: " + (enabled ? "§a" : "§c") + enabled);
                sender.sendMessage("§7Full class name: §e" + module.getClass().getName());
                if(module.getClass().isAnnotationPresent(Since.class)) {
                    Since since = module.getClass().getAnnotation(Since.class);
                    sender.sendMessage("§7Since: §e" + since.value());
                }
                Plugin plugin = Bukkit.getPluginManager().getPlugin(module.getModuleKey().getNamespace());
                if(plugin == null) {
                    sender.sendMessage("§7Plugin: §cNot found!");
                } else {
                    TextComponent component = new TextComponent("§7Plugin: §e" + plugin.getName());
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/ve-debug plugin " + plugin.getName()
                    ));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                            new TextComponent("§9Click for more information")
                    }));
                    sender.spigot().sendMessage(component);
                }
                List<TickService> tickServices = VanillaEnhancements.getPlugin().getTickServiceExecutor().getTickServicesForModule(module);
                if(tickServices.isEmpty()) {
                    sender.sendMessage("§7Tick services: §cNone");
                } else {
                    for (TickService tickService : tickServices) {
                        TextComponent component = new TextComponent("§7Tick service: §e" + tickService.method().getName());
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                "/ve-debug tickservice " + tickService.method().getDeclaringClass().getSimpleName() + "#" + tickService.method().getName()
                        ));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                                new TextComponent("§9Click for more information")
                        }));
                        sender.spigot().sendMessage(component);
                    }
                }
                sender.sendMessage("-------------------------------------------------");
                return true;
            } else if(args[0].equalsIgnoreCase("tickservice")) {
                String serviceKey = args[1];
                String className = serviceKey.split("#")[0];
                String methodName = serviceKey.split("#")[1];
                TickService service = VanillaEnhancements.getPlugin().getTickServiceExecutor().getTickServices().stream()
                        .filter(tickService -> tickService.method().getDeclaringClass().getSimpleName().equalsIgnoreCase(className))
                        .filter(tickService -> tickService.method().getName().equalsIgnoreCase(methodName))
                        .findFirst().orElse(null);
                if(service == null) {
                    sender.sendMessage("§cTick service not found!");
                    return true;
                }
                boolean running = service.moduleInstance().isEnabled();
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§7Tick service: §e" + service.method().getName());
                sender.sendMessage("§7Execution interval: §e" + service.period());
                sender.sendMessage("§7Execute now: §e" + service.shouldExecuteNow());
                sender.sendMessage("§7Running: " + (running ? "§a" : "§c") + running);
                sender.sendMessage("-------------------------------------------------");
                return true;
            } else if(args[0].equalsIgnoreCase("plugin")) {
                String pluginName = args[1];
                Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
                if(plugin == null) {
                    sender.sendMessage("§cPlugin not found!");
                    return true;
                }
                sender.sendMessage("-------------------------------------------------");
                PluginDescriptionFile description = plugin.getDescription();
                sender.sendMessage("§l§nPlugin: " + plugin.getName());
                sender.sendMessage("§7Version: §e" + description.getVersion());
                sender.sendMessage("§7Authors: §e" + String.join(", ", description.getAuthors()));
                sender.sendMessage("§7Website: §e" + (description.getWebsite() == null ? "Not set" : description.getWebsite()));
                sender.sendMessage("§7Description: §e" + (description.getDescription() == null ? "Not set" : description.getDescription()));
                sender.sendMessage("§7API Version: §e" + (description.getAPIVersion() == null ? "Not set" : description.getAPIVersion()));
                sender.sendMessage("§7Depends: §e" + String.join(", ", description.getDepend()));
                sender.sendMessage("§7Soft Depends: §e" + String.join(", ", description.getSoftDepend()));
                sender.sendMessage("§7Load Before: §e" + String.join(", ", description.getLoadBefore()));
                sender.sendMessage("§7Full class name: §e" + plugin.getClass().getName());
                sender.sendMessage("§7Enabled: " + (plugin.isEnabled() ? "§a" : "§c") + plugin.isEnabled());
                sender.sendMessage("-------------------------------------------------");
                return true;
            } else if(args[0].equalsIgnoreCase("recipe")) {
                String recipeKey = args[1];
                NamespacedKey key = NamespacedKey.fromString(recipeKey);
                if(key == null) {
                    sender.sendMessage("§cInvalid recipe key!");
                    return true;
                }
                Pair<NamespacedKey, VERecipe> entry = VanillaEnhancements.getPlugin().getRecipeManager().getRecipes().stream()
                        .filter(pair -> pair.getSecond().key().equals(key))
                        .findFirst().orElse(null);
                if(entry == null) {
                    sender.sendMessage("§cRecipe not found!");
                    return true;
                }
                VERecipe recipe = entry.getSecond();
                boolean registered = recipe.isRegistered();
                VEModule module = VanillaEnhancements.getPlugin().getModuleRegistry().getModule(entry.getFirst());
                sender.sendMessage("-------------------------------------------------");
                sender.sendMessage("§7Recipe: §e" + recipe.key().toString());
                sender.sendMessage("§7Registered: " + (registered ? "§a" : "§c") + registered);
                sender.sendMessage("§7DiscoverItem: §e" + (recipe.discoverItem() == null ? "Not set" : recipe.discoverItem().toString()));
                sender.sendMessage("§7Result: §e" + recipe.recipe().getResult().getType().name() + " x" + recipe.recipe().getResult().getAmount());
                if(module == null) {
                    sender.sendMessage("§7Module: §cNot found!");
                } else {
                    TextComponent moduleComponent = new TextComponent("§7Module: §e" + module.getName());
                    moduleComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/ve-debug module " + module.getModuleKey().toString()
                    ));
                    moduleComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                            new TextComponent("§9Click for more information")
                    }));
                    sender.spigot().sendMessage(moduleComponent);
                }
                sender.sendMessage("-------------------------------------------------");
                return true;
            }
        }
        sender.sendMessage("§cUsage: /ve-debug <reload/generate-docs/module [Module Key]/modules/tickservice [Class]#[Field]/plugin [Name]/tickservices/recipe [Key]/recipes>");
        return true;
    }

    public Debug debug() {
        return VanillaEnhancements.getPlugin().getDebug();
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!debug().isEnabled()) return null;
        if(args.length == 1) {
            return complete(args, 0, "reload", "generate-docs", "modules", "module", "tickservice", "plugin", "tickservices", "recipes", "recipe");
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("module")) {
                return complete(args, 1, VanillaEnhancements.getPlugin().getModuleRegistry().getRegisteredModules().stream().map(VEModule::getModuleKey).map(Object::toString).toArray(String[]::new));
            } else if(args[0].equalsIgnoreCase("tickservice")) {
                return complete(args, 1, VanillaEnhancements.getPlugin().getTickServiceExecutor().getTickServices().stream()
                        .map(TickService::method)
                        .map(method -> method.getDeclaringClass().getName() + "#" + method.getName())
                        .toArray(String[]::new));
            } else if(args[0].equalsIgnoreCase("plugin")) {
                return complete(args, 1, Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).toArray(String[]::new));
            } else if(args[0].equalsIgnoreCase("recipe")) {
                return complete(args, 1, VanillaEnhancements.getPlugin().getRecipeManager().getRecipes().stream()
                        .map(Pair::getSecond)
                        .map(VERecipe::key)
                        .map(NamespacedKey::toString)
                        .toArray(String[]::new));
            }
        }
        return null;
    }

    private List<String> complete(String[] args, int index, String... options) {
        return Stream.of(options).filter(s -> s.toLowerCase().startsWith(args[index].toLowerCase())).toList();
    }
}
