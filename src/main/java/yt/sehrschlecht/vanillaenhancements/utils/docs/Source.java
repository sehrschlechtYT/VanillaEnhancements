package yt.sehrschlecht.vanillaenhancements.utils.docs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used from the docs generator to display the source of a module.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Source {
    String value();
}
