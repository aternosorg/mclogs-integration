# 3.0.10
- Fix sharing client logs on Fabric clients

---

# 3.0.9
- Update to 1.21.9

---

# 3.0.8
- Update to 1.21.6/1.21.7/1.21.8

---

# 3.0.7
- Update to 1.21.5

---

# 3.0.6
- Fix logging on bukkit and bungeecord
- Fix error with night config on fabric
- Fix loading problems on fabric

---

# 3.0.5
- Prevent issues while migrating old config fields

---

# 3.0.4
- Remove old fields from the config during migration

---

# 3.0.1 / 3.0.2 / 3.0.3
- Fix publishing

---

# v3.0.0
## New Features
- Support NeoForge, Velocity and BungeeCord
- Allow using mod client-side with /mclogsc on all mod platforms
- Add config file for using custom mclogs instances or frontends on all platforms
- Automatically reload changes to the config file

## Notable Changes
Bukkit:
- The config file now uses toml and the keys have been renamed
- Minecraft 1.21 is now required
