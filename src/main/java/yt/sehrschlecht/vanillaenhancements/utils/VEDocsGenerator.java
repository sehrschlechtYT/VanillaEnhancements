package yt.sehrschlecht.vanillaenhancements.utils;

import com.google.common.io.Files;
import com.google.gson.annotations.Since;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.ApiStatus;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.*;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class VEDocsGenerator {
    private final List<VEModule> modules;

    public VEDocsGenerator(List<VEModule> modules) {
        this.modules = modules;
    }

    public void generate() throws IOException {
        //create docs folder
        File docsFolder = new File(VanillaEnhancements.getPlugin().getDataFolder(), "docs");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }
        for (VEModule module : modules) {
            String doc = generateDoc(module);
            File file = new File(VanillaEnhancements.getPlugin().getDataFolder(), "docs/" + module.getModuleKey().getKey() + ".md");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Files.write(doc, file, StandardCharsets.UTF_8);
        }
    }

    private String generateDoc(VEModule module) {
        StringBuilder builder = new StringBuilder();
        builder.append("# " + module.getName() + "\n\n");
        List<ConfigOption> configOptions = getConfigOptions(module);
        if (configOptions.size() > 0) {
            builder.append("## Configuration\n\n");
            builder.append("| Key | Description | Default Value | Type | Possible values |\n");
            builder.append("| --- | ----------- | ------------- | ---- | --------------- |\n");
            for (ConfigOption option : configOptions) {
                builder.append("| " + option.getKey() + " | <description> | `" + option.getDefaultValue() + "` | `" + option.getDefaultValue().getClass().getSimpleName() + "` | `" + getPossibleValues(option) + "` |\n");
            }
        } else {
            builder.append("## Configuration\n\n");
            builder.append("This module has no configuration options.\n");
        }
        builder.append("\n");
        builder.append("## Technical Information\n\n");
        builder.append("| Key | Value |\n");
        builder.append("| --- | ----- |\n");
        builder.append("| Full class name | `" + module.getClass().getName() + "` |\n");
        builder.append("| Namespace | `" + module.getModuleKey().getNamespace() + "` |\n");
        builder.append("| Key | `" + module.getModuleKey().getKey() + "` |\n");
        if(module.getClass().isAnnotationPresent(Since.class)) {
            builder.append("| Since | `" + module.getClass().getAnnotation(Since.class).value() + "` |\n");
        }

        return builder.toString();
    }

    private String getPossibleValues(ConfigOption option) {
        return switch(option.getDefaultValue().getClass().getSimpleName()) {
            case "String" -> "Any string";
            case "Boolean" -> "true/false";
            case "Integer", "Double", "Float" -> "Any number";
            default -> "Unknown";
        };
    }

    private List<ConfigOption> getConfigOptions(VEModule module) {
        return Config.getInstance().getOptions(module);
    }
}
