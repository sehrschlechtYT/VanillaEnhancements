package yt.sehrschlecht.vanillaenhancements.ticking;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TickService {
    long period();
    boolean executeNow() default false;
}
