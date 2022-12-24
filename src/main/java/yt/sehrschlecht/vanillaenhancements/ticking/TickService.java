package yt.sehrschlecht.vanillaenhancements.ticking;

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
}
