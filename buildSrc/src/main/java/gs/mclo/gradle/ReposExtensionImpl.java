package gs.mclo.gradle;

import org.gradle.api.Action;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;

public class ReposExtensionImpl implements ReposExtension {
    private final Action<MavenArtifactRepository> sponge = repo -> {
        repo.setName("Sponge");
        repo.setUrl("https://repo.spongepowered.org/repository/maven-public");
    };
    private final Action<MavenArtifactRepository> neoForge = repo -> {
        repo.setName("NeoForge");
        repo.setUrl("https://maven.neoforged.net/releases");
    };
    private final Action<MavenArtifactRepository> spigot = repo -> {
        repo.setName("SpigotMC");
        repo.setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots/");
    };
    private final Action<MavenArtifactRepository> paper = repo -> {
        repo.setName("PaperMC");
        repo.setUrl("https://repo.papermc.io/repository/maven-public/");
    };
    private final Action<MavenArtifactRepository> forge = repo -> {
        repo.setName("Forge");
        repo.setUrl("https://maven.minecraftforge.net");
    };
    private final Action<MavenArtifactRepository> minecraftLibs = repo -> {
        repo.setName("Minecraft Libraries");
        repo.setUrl("https://libraries.minecraft.net");
    };
    private final Action<MavenArtifactRepository> centralSnapshots = repo -> {
        repo.setName("Central Portal Snapshots");
        repo.setUrl("https://central.sonatype.com/repository/maven-snapshots/");
        repo.content(content -> {
            content.includeModule("gs.mclo", "api");
        });
    };

    public Action<MavenArtifactRepository> getSponge() {
        return sponge;
    }

    public Action<MavenArtifactRepository> getNeoForge() {
        return neoForge;
    }

    public Action<MavenArtifactRepository> getSpigot() {
        return spigot;
    }

    public Action<MavenArtifactRepository> getPaper() {
        return paper;
    }

    public Action<MavenArtifactRepository> getForge() {
        return forge;
    }

    public Action<MavenArtifactRepository> getMinecraftLibs() {
        return minecraftLibs;
    }

    public Action<MavenArtifactRepository> getCentralSnapshots() {
        return centralSnapshots;
    }
}
