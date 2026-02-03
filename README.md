<div align="center">
    <a href="https://mclo.gs">
        <img src="https://mclo.gs/img/logo.svg" alt="Logo" width="260">
    </a>
    <h3>Server Plugin/Mod</h3>
    <p>
        <a href="https://modrinth.com/plugin/mclogs" target="_blank"><strong>« Download on Modrinth »</strong></a>
        <br />
        <a href="https://www.spigotmc.org/resources/mclo-gs.47502/" target="_blank">SpigotMC</a>
        ·
        <a href="https://www.curseforge.com/minecraft/bukkit-plugins/mclo-gs" target="_blank">CurseForge (Plugin)</a>
        ·
        <a href="https://www.curseforge.com/minecraft/mc-mods/mclo-gs" target="_blank">CurseForge (Mod)</a>
        ·
        <a href="https://hangar.papermc.io/Aternos/mclogs" target="_blank">Hangar</a>
    </p>
</div>


## About mclo.gs

<a href="https://mclo.gs" target="_blank">mclo.gs</a> allows you to quickly share and analyze logs for Minecraft servers and clients. It highlights different log levels, removes sensitive information like IP-Addresses and detects common problems as well as version information.

## Features
- Upload server logs with a single command
- Share logs with a short url
- Automatic on-device removal of sensitive information like IP addresses and system usernames
- Uploading other logs and reports
- Deleting uploaded logs

## Commands

The Plugin/Mod contains the following commands:

| Command       | Arguments     | Description                                                   | Permission Level | Permission Node |
|---------------|---------------|---------------------------------------------------------------|------------------|-----------------|
| mclogs        |               | Share the current server log                                  | 2                | mclogs          |
| mclogs list   |               | List server logs, crash- and network protocol error reports   | 2                | mclogs.list     |
| mclogs share  | \<file-name\> | Share a specific log, crash- or network protocol error report | 2                | mclogs.share    |
| mclogs delete | \<log-id\>    | Delete a log that was shared from the server                  | 2                | mclogs.delete   |

For special cases, the following commands are available:

| Scenario                               | Command                   | Behavior           |
|----------------------------------------|---------------------------|--------------------|
| Mod installed client-side              | `/mclogsc`                | Handle client logs |
| Plugin installed on a Velocity proxy   | `/mclogsp` and `/mclogsv` | Handle proxy logs  |
| Plugin installed on a BungeeCord proxy | `/mclogsp` and `/mclogsb` | Handle proxy logs  |

### Permissions

Commands are not available to players by default. You can give players access to the commands by giving them the 
required permissions. Plugin servers permissions use permission nodes for fine-grained access control. The `mclogs`
permission node is required for all other commands as well.

Modded servers use the Vanilla permission level system instead.

## Configuration
If you use a self-hosted instance of mclo.gs or want to use an alternative front-end, you can configure the plugin/mod to use your instance.
The config file is located in `plugins/mclogs/config.toml` for plugin servers and `config/mclogs.toml` for modded servers.

### Default configuration
```toml
# Base URL for the API used to upload logs
apiBaseUrl = "https://api.mclo.gs"
```

## Requirements
This project requires Java 11 or higher to run, even on Minecraft versions that run on Java 8.

## Contributing
This project is licensed as MIT. Contributions are welcome but if you plan some larger changes please
create an issue for discussion first, to avoid wasting time on something that might not be merged.

### Setting up the Development Environment
1. Clone the repository
2. Import the project in your IDE (IntelliJ IDEA is recommended)
3. Run `./gradlew :forge:genIntellijRuns :neoforge:createLaunchScripts` to generate the Forge/NeoForge runs

### Modules

| Module     | Description                                                                    | Parent Module |
|------------|--------------------------------------------------------------------------------|---------------|
| common     | Code shared between all platforms                                              |               |
| common-mc  | Code shared between all platforms that provide access to the Minecraft classes | common        |
| adventure  | Code shared between all platforms that use the adventure component library     | common        |
| bukkit     | Bukkit plugin implementation                                                   | adventure     |
| bungeecord | BungeeCord plugin implementation                                               | adventure     |
| velocity   | Velocity plugin implementation                                                 | adventure     |
| forge      | Forge mod implementation                                                       | common-mc     |
| fabric     | Fabric mod implementation                                                      | common-mc     |
| neoforge   | NeoForge mod implementation                                                    | common-mc     |

### Building
To build all modules run `./gradlew buildAll`. Unless specified below all other modules use the `build` task.

Modules with special build task:

| Module     | Task        |
|------------|-------------|
| bukkit     | `shadowJar` |
| bungeecord | `shadowJar` |
| velocity   | `shadowJar` |
| forge      | `jarJar`    |

### Running in development environments
If you're using IntelliJ IDEA you should already see run configurations for most platforms.
For other platforms or other IDEs run their respective gradle tasks:

| Platform   | Client Task                     | Server Task                           |
|------------|---------------------------------|---------------------------------------|
| Bukkit     |                                 | `./gradlew :bukkit:runServer`         |
| BungeeCord |                                 | `./gradlew :bungeecord:runBungeeCord` |
| Velocity   |                                 | `./gradlew :velocity:runVelocity`     |
| Forge      | `./gradlew :forge:Client`       | `./gradlew :forge:Server`             |
| Fabric     | `./gradlew :fabric:runClient`   | `./gradlew :fabric:runServer`         |
| NeoForge   | `./gradlew :neoforge:runClient` | `./gradlew :neoforge:runServer`       |
