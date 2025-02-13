package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.MclogsCommon;
import gs.mclo.api.MclogsClient;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

public class MclogsCommand<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > extends Command<ComponentType, StyleType, ClickEventType> {
    public MclogsCommand(
            MclogsClient apiClient,
            MclogsCommon mclogs,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        super(apiClient, mclogs, componentFactory);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T, ComponentType> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        if (buildContext.environment == CommandEnvironment.DEDICATED_SERVER) {
            builder = builder.requires(source -> buildContext.mapSource(source).hasPermission(Permission.BASE));
        }

        return builder.executes(context -> execute(context, buildContext));
    }

    @Override
    public <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext) {
        return share(context, buildContext, "latest.log");
    }
}
