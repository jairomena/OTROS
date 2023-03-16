package com.distribuida.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import spark.ModelAndView;

import java.util.Locale;
import java.util.Map;

public class ThymeleafTemplateEngine extends spark.TemplateEngine{

    private static final String DEFAULT_PREFIX = "templates/";
    private static final String DEFAULT_SUFFIX = ".html";
    private static final long DEFAULT_CACHE_TTL_MS = 3600000L;

    protected TemplateEngine templateEngine;

    /**
     * Constructs a default thymeleaf template engine.
     * Defaults prefix (template directory in resource path) to templates/ and suffix to .html
     */
    public ThymeleafTemplateEngine(){
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    /**
     * Constructs a thymeleaf template engine with specified prefix and suffix
     *
     * @param prefix the prefix (template directory in resource path)
     * @param suffix the suffix (e.g. .html)
     */
    public ThymeleafTemplateEngine(String prefix, String suffix) {
        ITemplateResolver defaultTemplateResolver = createDefaultTemplateResolver(prefix, suffix);
        initialize(defaultTemplateResolver);
    }

    /**
     * Constructs a thymeleaf template engine with a proprietary initialize
     *
     * @param templateResolver the template resolver.
     */
    public ThymeleafTemplateEngine(ITemplateResolver templateResolver) {
        initialize(templateResolver);
    }

    private static ITemplateResolver createDefaultTemplateResolver(String prefix, String suffix) {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateResolver.setPrefix(
                prefix != null ? prefix : DEFAULT_PREFIX
        );

        templateResolver.setSuffix(
                suffix != null ? suffix : DEFAULT_SUFFIX
        );

        templateResolver.setCacheTTLMs(DEFAULT_CACHE_TTL_MS);
        return templateResolver;
    }

    /**
     * Initializes and sets the template resolver
     */
    private void initialize(ITemplateResolver templateResolver) {
        templateEngine = new org.thymeleaf.TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Java8TimeDialect());
    }

    @Override
    public String render(ModelAndView modelAndView) {
        return render(modelAndView, Locale.getDefault());
    }

    public String render(ModelAndView modelAndView, Locale locale) {
        Object model = modelAndView.getModel();

        if (model instanceof Map) {
            Context context = new Context(locale);
            context.setVariables((Map<String, Object>) model);
            return templateEngine.process(modelAndView.getViewName(), context);
        } else {
            throw new IllegalArgumentException("modelAndView.getModel() must return a java.util.Map");
        }
    }
}
