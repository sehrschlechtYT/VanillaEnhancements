package yt.sehrschlecht.vanillaenhancements.ticking;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TickService {
    long period();
    boolean executeNow() default false;
}
