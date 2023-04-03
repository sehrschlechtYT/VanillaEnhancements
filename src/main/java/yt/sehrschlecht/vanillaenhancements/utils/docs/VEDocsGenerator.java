package yt.sehrschlecht.vanillaenhancements.utils.docs;

import com.google.common.io.Files;
import com.google.gson.annotations.Since;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Files.write(doc, file, StandardCharsets.UTF_8);
        }
    }

    // ToDo generate crafting table images for recipe modules
    private String generateDoc(VEModule module) {
        StringBuilder builder = new StringBuilder();
        builder.append("---\n");
        builder.append("title: ").append(module.getName()).append("\n");
        builder.append("---\n");
        if (module.getDescription() != null && !module.getDescription().isBlank()) {
            builder.append(module.getDescription()).append("\n");
        }
        if (module.getClass().isAnnotationPresent(Source.class)) {
            builder.append("Idea Source: ").append(module.getClass().getAnnotation(Source.class).value()).append("\n");
        }
        builder.append("# " + module.getName() + "\n\n");
        List<ConfigOption<?>> configOptions = getConfigOptions(module);
        builder.append("## Configuration\n\n");
        builder.append("| Key | Description | Default Value | Type | Possible values |\n");
        builder.append("| --- | ----------- | ------------- | ---- | --------------- |\n");
        builder.append("| `" + module.getModuleKey().getKey() + ".enabled` | Controls if the module is enabled | `false` | `Boolean` | `true/false` |\n");
        for (ConfigOption<?> option : configOptions) {
           builder.append(optionToString(option));
        }
        builder.append("\n");
        builder.append("## Technical Information\n\n");
        builder.append("| Key | Value |\n");
        builder.append("| --- | ----- |\n");
        builder.append("| Full class name | `" + module.getClass().getName() + "` |\n");
        builder.append("| Namespace | `" + module.getModuleKey().getNamespace() + "` |\n");
        builder.append("| Key | `" + module.getModuleKey().getKey() + "` |\n");

        String since = null;
        if (module.getSince() != null && !module.getSince().isBlank()) {
            since = module.getSince();
        } else if (module.getClass().isAnnotationPresent(Since.class)) {
            since = String.valueOf(module.getClass().getAnnotation(Since.class).value());
        }
        if (since != null) {
            builder.append("| Since | `" + module.getClass().getAnnotation(Since.class).value() + "` |\n");
        }

        return builder.toString();
    }

    private String optionToString(ConfigOption<?> option) {
        return "| `" + option.toPath() + "` | "
                + option.getDescription()
                + " | `" + option.getDefaultValue()
                + "` | `" + option.getClass().getSimpleName().replace("Option", "")
                + "` | " + option.getPossibleValues() + " |\n";
    }

    private List<ConfigOption<?>> getConfigOptions(VEModule module) {
        return Config.getInstance().getModuleOptions(module);
    }
}
