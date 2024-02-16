package yt.sehrschlecht.vanillaenhancements.ticking;

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.reflect.Method;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public final class TickService {
    private final VEModule moduleInstance;
    private final Tick tick;
    private final Method method;

    private boolean enabled;

    public TickService(VEModule moduleInstance, Tick tick, Method method) {
        this.moduleInstance = moduleInstance;
        this.tick = tick;
        this.method = method;
    }

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

    public VEModule moduleInstance() {
        return moduleInstance;
    }

    public Tick tick() {
        return tick;
    }

    public Method method() {
        return method;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
