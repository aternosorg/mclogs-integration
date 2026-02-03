package gs.mclo.gradle;

import org.gradle.api.Action;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;

public interface ReposExtension {
    Action<MavenArtifactRepository> getSponge();

    Action<MavenArtifactRepository> getNeoForge();

    Action<MavenArtifactRepository> getSpigot();

    Action<MavenArtifactRepository> getPaper();

    Action<MavenArtifactRepository> getForge();

    Action<MavenArtifactRepository> getMinecraftLibs();

    Action<MavenArtifactRepository> getCentralSnapshots();
}
