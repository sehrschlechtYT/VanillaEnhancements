package yt.sehrschlecht.vanillaenhancements.ticking;

import org.bukkit.Bukkit;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleRegistry;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.util.HashMap;
import java.util.Map;

public class TickServiceExecutor {
    private static Map<TickService, Long> tickServices = new HashMap<>();

    public static void startTicking() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VanillaEnhancements.getPlugin(), () -> {
            for (Map.Entry<TickService, Long> entry : tickServices.entrySet()) {
                TickService service = entry.getKey();
                if(ModuleRegistry.isEnabled(service.getKey())) {
                    long period = entry.getValue();
                    period--;
                    if(period == 0) {
                        service.getRunnable().run();
                        period = service.getPeriod();
                    }
                    tickServices.replace(service, period);
                }
            }
        }, 0L, 1L);
    }

    public static void addTickService(TickService service) {
        tickServices.put(service, service.getPeriod());
    }
}
