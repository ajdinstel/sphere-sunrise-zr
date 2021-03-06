package common.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import common.controllers.PageData;
import play.Configuration;
import play.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public final class HandlebarsTemplateService implements TemplateService {
    private final Handlebars handlebars;
    private final List<TemplateLoader> fallbackContexts;

    private HandlebarsTemplateService(final Handlebars handlebars, final List<TemplateLoader> fallbackContexts) {
        this.handlebars = handlebars;
        this.fallbackContexts = fallbackContexts;
    }

    @Override
    public String render(final String templateName, final PageData pageData, final List<Locale> locales) {
        final Template template = compileTemplate(templateName);
        final Context context = buildContext(pageData, templateName);
        context.data("locales", locales.stream().map(Locale::toLanguageTag).collect(toList()));
        try {
            Logger.debug("Rendering template " + templateName);
            return template.apply(context);
        } catch (IOException e) {
            throw new TemplateRenderException("Context could not be applied to template " + templateName, e);
        }
    }

    public static TemplateService of(final List<TemplateLoader> templateLoaders, final Configuration configuration) {
        return of(templateLoaders, emptyList(), configuration);
    }

    public static TemplateService of(final List<TemplateLoader> templateLoaders, final List<TemplateLoader> fallbackContexts, final Configuration configuration) {
        final TemplateLoader[] loaders = templateLoaders.toArray(new TemplateLoader[templateLoaders.size()]);
        final Handlebars handlebars = new Handlebars().with(loaders).infiniteLoops(true);
        final List<String> languages = configuration.getStringList("handlebars.i18n.langs", emptyList());
        final List<String> bundles = configuration.getStringList("handlebars.i18n.bundles", emptyList());
        handlebars.registerHelper("i18n", new CustomI18nHelper(languages, bundles));
        handlebars.registerHelper("json", new HandlebarsJsonHelper<>());
        return new HandlebarsTemplateService(handlebars, fallbackContexts);
    }

    private Template compileTemplate(final String templateName) {
        try {
            return handlebars.compile(templateName);
        } catch (IOException e) {
            throw new TemplateNotFoundException("Could not find the default template", e);
        }
    }

    private Context buildContext(final PageData pageData, final String templateName) {
        // TODO Use resolver with cache on production
        Context.Builder builder = Context.newBuilder(pageData)
                .resolver(NonCachedJavaBeanValueResolver.INSTANCE, MapValueResolver.INSTANCE);
        for (final TemplateLoader fallbackContext : fallbackContexts) {
            final Optional<Map<String, ?>> map = buildFallbackContext(fallbackContext, templateName);
            if (map.isPresent()) {
                builder = builder.combine(map.get());
            }
        }
        return builder.build();
    }

    private Optional<Map<String, ?>> buildFallbackContext(final TemplateLoader fallbackLoader, final String templateName) {
        return getResource(fallbackLoader, templateName).map(stream -> {
            try {
                return new ObjectMapper().readValue(stream, HashMap.class);
            } catch (IOException e) {
                Logger.error("Could not read fallback context", e);
                return null;
            }
        });
    }

    private Optional<InputStream> getResource(final TemplateLoader fallbackLoader, final String templateName) {
        final InputStream resource;
        if (fallbackLoader instanceof ClassPathTemplateLoader) {
            final String path = fallbackLoader.resolve(templateName).replaceAll("^/?(.*)\\.hbs$", "$1.json");
            resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        } else {
            throw new RuntimeException("Invalid fallback context type, only classpath supported");
        }
        return Optional.ofNullable(resource);
    }
}
