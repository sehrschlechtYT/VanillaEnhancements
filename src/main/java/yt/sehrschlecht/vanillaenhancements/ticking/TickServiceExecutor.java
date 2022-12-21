package yt.sehrschlecht.vanillaenhancements.ticking;

import org.bukkit.Bukkit;
import yt.sehrschlecht.schlechteutils.data.Pair;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class TickServiceExecutor {
    private List<Pair<TickService, Method>> tickServices = new ArrayList<>();

    public void startTicking() {
        for (Pair<TickService, Method> pair : tickServices) {
            TickService tickService = pair.getFirst();
            Method method = pair.getSecond();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(VanillaEnhancements.getPlugin(), () -> {
                try {
                    VEModule instance = VanillaEnhancements.getPlugin().getModuleRegistry().getInstance(method.getDeclaringClass());
                    if(instance == null) return;
                    method.invoke(instance);
                } catch (Exception e) {
                    VanillaEnhancements.getPlugin().getLogger().severe("An error occurred while executing a tick service:");
                    e.printStackTrace();
                }
            }, tickService.executeNow() ? 0 : tickService.period(), tickService.period());
        }
    }

    public void addTickService(TickService service, Method method) {
        tickServices.add(new Pair<>(service, method));
    }
}
