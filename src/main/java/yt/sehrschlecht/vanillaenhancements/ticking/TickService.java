package yt.sehrschlecht.vanillaenhancements.ticking;

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.reflect.Method;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public record TickService(VEModule moduleInstance, Tick tick, Method method) {

    public long period() {
        return tick.period();
    }

    public boolean shouldExecuteNow() {
        return tick.executeNow();
    }

    public void run() {
        try {
            method.invoke(moduleInstance);
        } catch (Exception e) {
            VanillaEnhancements.getPlugin().getLogger().severe("Failed to run tick service " + method.getName() + " of module " + moduleInstance.getName() + ":");
            e.printStackTrace();
        }
    }

}
