# Donut Client 2.0 — Fabric 1.21.11

A client-side utility mod with a dark purple ClickGUI, configurable HUD and saved preferences.

## Implemented features
- Right Shift opens/closes the ClickGUI.
- Coordinates HUD (XYZ).
- FPS HUD.
- Armor value HUD.
- Totem inventory reminder (checks inventory; does not move items or automate combat).
- GUI toggles update immediately.
- Settings persist to `config/donutclient.properties` and save when the GUI closes or Save is pressed.

## Not included
No combat automation, flight, hidden-base tracking, anti-cheat bypass, stealth or packet manipulation. The mod does not disable or evade server protections. It is a client-side HUD and convenience project.

## Build requirements
- JDK 21
- Minecraft 1.21.11
- Fabric Loader 0.18.1 or newer compatible release
- Fabric API 0.141.3+1.21.11
- Gradle compatible with Fabric Loom 1.17.13 (Gradle 9.2.1 recommended)

## Build
1. Extract the ZIP.
2. Install JDK 21.
3. Open the `DonutClient` folder in IntelliJ IDEA and import as a Gradle project, or open a terminal there.
4. Run `gradle build`.
5. The remapped mod JAR is created in `build/libs/` (normally `donut-client-2.0.0.jar`).

## Install
1. Install Fabric Loader for Minecraft 1.21.11.
2. Install Fabric API matching Minecraft 1.21.11.
3. Copy the JAR from `build/libs/` into `.minecraft/mods`.
4. Start the Fabric 1.21.11 profile.
5. Press Right Shift.

## Keybind
Options → Controls → Key Binds → Donut Client → Open Donut Client ClickGUI.

## Notes
This source project has not been compiled in this environment because Gradle and network access to the dependency repositories are unavailable here. Run the build locally; if Gradle prints an error, send the complete error text or a screenshot and I can help correct it. Fabric's official 1.21.11 guides and versioned API docs should be used if the toolchain versions need updating.
