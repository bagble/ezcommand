package me.kingcjy.ezcommand.context;

import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import me.kingcjy.ezcommand.beans.scanner.AnnotationBeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.BeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.ClassPathBeanDefinitionScanner;
import me.kingcjy.ezcommand.command.Handler.AnnotationCommandHandlerMapping;
import me.kingcjy.ezcommand.command.Handler.HandlerMethodCreator;
import me.kingcjy.ezcommand.command.Handler.HandlerMethodCreatorComposite;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinition;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.executor.AnnocationCommandExecutor;
import me.kingcjy.ezcommand.command.registry.AnnotationCommandRegistry;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolver;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.utils.BeanUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.Set;

public class EzApplicationContext implements ApplicationContext {

    private String serverName;
    private String serverVersion;
    private LocalDateTime startDateTime;

    private BeanFactory beanFactory;

    private JavaPlugin javaPlugin;

    public EzApplicationContext(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.startDateTime = LocalDateTime.now();

        String basePackage = javaPlugin.getClass().getPackage().getName();

        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();

        BeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner();
        classPathBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        classPathBeanDefinitionScanner.scan(basePackage);

        BeanDefinitionScanner annotationBeanDefinitionScanner = new AnnotationBeanDefinitionScanner();
        annotationBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        annotationBeanDefinitionScanner.scan(basePackage);
        this.beanFactory = defaultBeanFactory.initialize();

        Set<CommandTypeDefinition> commandTypeDefinitions = BeanUtils.findSubTypeOf(beanFactory, CommandTypeDefinition.class);
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = this.beanFactory.getBean(CommandTypeDefinitionComposite.class);
        commandTypeDefinitionComposite.addCommandTypeDefinitions(commandTypeDefinitions);

        Set<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = BeanUtils.findSubTypeOf(beanFactory, HandlerMethodArgumentResolver.class);
        HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite = new HandlerMethodArgumentResolverComposite();
        handlerMethodArgumentResolverComposite.addResolver(handlerMethodArgumentResolvers);

        HandlerMethodCreator handlerMethodCreator = new HandlerMethodCreatorComposite(beanFactory, handlerMethodArgumentResolverComposite);

        AnnotationCommandHandlerMapping annotationCommandHandlerMapping = new AnnotationCommandHandlerMapping(beanFactory, handlerMethodCreator);
        AnnocationCommandExecutor annocationCommandExecutor = new AnnocationCommandExecutor(annotationCommandHandlerMapping);
        AnnotationCommandRegistry annotationCommandRegistry = new AnnotationCommandRegistry(javaPlugin, annocationCommandExecutor, annotationCommandHandlerMapping);
        annotationCommandRegistry.registerCommands();
    }

    @Override
    public String getServerName() {
        return "Ezframework";
    }

    @Override
    public String getServerVersion() {
        return "0.0.1";
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public Object getBean(String name) {
        return this.beanFactory.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return this.beanFactory.getBean(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) {
        return this.beanFactory.getBean(name, type);
    }

    @Override
    public Object[] getBeans() {
        return this.beanFactory.getBeans();
    }
}