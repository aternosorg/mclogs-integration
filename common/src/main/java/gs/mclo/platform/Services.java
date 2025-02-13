package gs.mclo.platform;

import gs.mclo.Constants;
import gs.mclo.platform.services.IPlatformHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ServiceLoader;

// Service loaders are a built-in Java feature that allow us to locate implementations of an interface that vary from one
// environment to another. In the context of MultiLoader we use this feature to access a mock API in the common code that
// is swapped out for the platform specific implementation at runtime.
public class Services {
    @Nullable
    private static ClassLoader CLASS_LOADER = null;

    // In this example we provide a platform helper which provides information about what platform the mod is running on.
    // For example this can be used to check if the code is running on Forge vs Fabric, or to ask the modloader if another
    // mod is loaded.
    @Nullable
    private static IPlatformHelper PLATFORM;

    public static IPlatformHelper platform() {
        if (PLATFORM == null) {
            PLATFORM = load(IPlatformHelper.class);
        }

        return PLATFORM;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        CLASS_LOADER = classLoader;
    }

    // This code is used to load a service for the current environment. Your implementation of the service must be defined
    // manually by including a text file in META-INF/services named with the fully qualified class name of the service.
    // Inside the file you should write the fully qualified class name of the implementation to load for the platform. For
    // example our file on Forge points to ForgePlatformHelper while Fabric points to FabricPlatformHelper.
    public static <T> T load(Class<T> clazz) {
        final T loadedService = getServiceLoader(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    public static <T> ServiceLoader<T> getServiceLoader(Class<T> clazz) {
        if (CLASS_LOADER == null) {
            return ServiceLoader.load(clazz);
        }

        return ServiceLoader.load(clazz, CLASS_LOADER);
    }
}
