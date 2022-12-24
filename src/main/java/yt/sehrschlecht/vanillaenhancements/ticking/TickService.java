package yt.sehrschlecht.vanillaenhancements.ticking;

import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.reflect.Method;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class TickService {
    private final VEModule moduleInstance;
    private final Tick tick;
    private final Method method;

    public TickService(VEModule moduleInstance, Tick tick, Method method) {
        this.moduleInstance = moduleInstance;
        this.tick = tick;
        this.method = method;
    }

    public VEModule getModuleInstance() {
        return moduleInstance;
    }

    public long getPeriod() {
        return tick.period();
    }

    public boolean shouldExecuteNow() {
        return tick.executeNow();
    }

    public Method getMethod() {
        return method;
    }
}
