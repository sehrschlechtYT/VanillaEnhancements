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
    private List<TickService> tickServices = new ArrayList<>();

    public void startTicking() {
        for (TickService tickService : tickServices) {
            VEModule instance = tickService.getModuleInstance();
            Method method = tickService.getMethod();
            long period = tickService.getPeriod();
            boolean executeNow = tickService.shouldExecuteNow();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(VanillaEnhancements.getPlugin(), () -> {
                try {
                    method.invoke(instance);
                } catch (Exception e) {
                    VanillaEnhancements.getPlugin().getLogger().severe("An error occurred while executing a tick service:");
                    e.printStackTrace();
                }
            }, executeNow ? 0 : period, period);
        }
    }

    public void addTickService(VEModule moduleInstance, Tick tickService, Method method) {
        tickServices.add(new TickService(moduleInstance, tickService, method));
    }
}
