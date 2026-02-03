package gs.mclo.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.plugins.ExtensionAware;

import javax.inject.Inject;

public class MultiLoaderPlugin implements Plugin<ExtensionAware> {
    @Inject
    public MultiLoaderPlugin() {

    }

    @Override
    public void apply(ExtensionAware target) {
        target.getExtensions().create(ReposExtension.class, "repos", ReposExtensionImpl.class);
    }
}
