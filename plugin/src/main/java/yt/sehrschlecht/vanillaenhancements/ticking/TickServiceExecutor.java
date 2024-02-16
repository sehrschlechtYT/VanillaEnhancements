package yt.sehrschlecht.vanillaenhancements.ticking;

import org.bukkit.Bukkit;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class TickServiceExecutor {
    private final List<TickService> tickServices = new ArrayList<>();

    public void startTicking() {
        for (TickService tickService : tickServices) {
            long period = tickService.period();
            boolean executeNow = tickService.shouldExecuteNow();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(VanillaEnhancements.getPlugin(), () -> {
                if (!tickService.isEnabled()) return;
                tickService.run();
            }, executeNow ? 0 : period, period);
        }
    }

    public void addTickService(VEModule moduleInstance, Tick tickService, Method method) {
        tickServices.add(new TickService(moduleInstance, tickService, method));
    }

    public List<TickService> getTickServicesForModule(VEModule module) {
        return tickServices.stream().filter(tickService -> tickService.moduleInstance().equals(module)).toList();
    }

    public List<TickService> getTickServices() {
        return tickServices;
    }
}
