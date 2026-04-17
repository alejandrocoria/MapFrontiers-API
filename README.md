# MapFrontiers API

MapFrontiers API is the public Java API for [MapFrontiers](https://github.com/alejandrocoria/MapFrontiers), the Minecraft mod that lets players create, update and observe frontiers from client or server integrations.

This repository contains the standalone API artifact used by mods and plugins that integrate with MapFrontiers.

## API entry points

MapFrontiers exposes two API entry points:

- client API, for client-side actions such as creating or updating frontiers from a mod running on the client
- server API, for server-side actions such as listing and creating global frontiers

Plugins register through `MapFrontiersAPI` and receive an API instance in `initialize(...)`.

## Add the dependency

The API uses the `games.alejandrocoria` group and is currently consumed as a `-SNAPSHOT` build. While snapshots are being used, add the Central Portal snapshots repository and keep `mavenCentral()` for regular dependencies:

```groovy
repositories {
    maven {
        name = "Central Portal Snapshots"
        url = "https://central.sonatype.com/repository/maven-snapshots/"

        content {
            includeModule "games.alejandrocoria", "mapfrontiers-api"
        }
    }
    mavenCentral()
}

dependencies {
    implementation "games.alejandrocoria:mapfrontiers-api:0.1.0-SNAPSHOT"
}
```

The artifact is intentionally lightweight and independent of Minecraft and modloader runtime classes.

## Register a client plugin

Register your plugin once from a client-side entry point during mod initialization:

```java
import games.alejandrocoria.mapfrontiers.api.MapFrontiersAPI;

public final class ExampleClientModEntrypoint {
    public static void register() {
        MapFrontiersAPI.registerClientPlugin(new ExampleClientPlugin());
    }
}
```

Then implement the plugin:

```java
import games.alejandrocoria.mapfrontiers.api.client.FrontierActionResult;
import games.alejandrocoria.mapfrontiers.api.client.IMapFrontiersClientAPI;
import games.alejandrocoria.mapfrontiers.api.event.EventBus;
import games.alejandrocoria.mapfrontiers.api.event.FrontierCreatedEvent;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierLifetime;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.FrontierShape;
import games.alejandrocoria.mapfrontiers.api.model.PathMarkerId;
import games.alejandrocoria.mapfrontiers.api.model.PathStyle;
import games.alejandrocoria.mapfrontiers.api.model.Point2i;
import games.alejandrocoria.mapfrontiers.api.plugin.IMapFrontiersClientPlugin;

import java.util.List;

public final class ExampleClientPlugin implements IMapFrontiersClientPlugin {
    private EventBus.Subscription createdSubscription;

    @Override
    public String getModId() {
        return "examplemod";
    }

    @Override
    public void initialize(IMapFrontiersClientAPI api) {
        createdSubscription = api.events().subscribe(FrontierCreatedEvent.class, this::onFrontierCreated);

        FrontierActionResult result = api.frontiers().createPersonalFrontier(
                new DimensionId("minecraft:overworld"),
                FrontierShape.vertex(List.of(
                        new Point2i(0, 0),
                        new Point2i(100, 0),
                        new Point2i(100, 100),
                        new Point2i(0, 100)
                )),
                FrontierLifetime.SESSION_ONLY
        );

        result.frontierId().ifPresent(frontierId ->
                api.frontiers().updatePersonalFrontier(frontierId, FrontierMutation.name1("Spawn"))
        );

        FrontierShape pathShape = FrontierShape.path(List.of(
                new Point2i(0, 0),
                new Point2i(100, 0),
                new Point2i(160, 40)
        ));

        FrontierMutation styleMutation = FrontierMutation.pathStyle(new PathStyle(
                PathMarkerId.ARROW,
                PathMarkerId.SMALL_DOT,
                PathMarkerId.BIG_DOT,
                PathMarkerId.CHEVRON,
                true,
                false,
                true
        ));
    }

    @Override
    public void shutdown(IMapFrontiersClientAPI api) {
        if (createdSubscription != null) {
            createdSubscription.unsubscribe();
            createdSubscription = null;
        }
    }

    private void onFrontierCreated(FrontierCreatedEvent event) {
        System.out.println("Frontier created: " + event.frontier().id().value());
    }
}
```

Client-side notes:

- many client actions are asynchronous and return `ACCEPTED_ASYNC`
- in singleplayer, requests still go through the logical server
- `createPersonalFrontier(dimension, shape)` still creates a `PERSISTENT` frontier
- `createPersonalFrontier(dimension, shape, FrontierLifetime.SESSION_ONLY)` creates a local-only frontier for the current client session
- `FrontierDataView` is a snapshot, not a live object
- `FrontierDataView.lifetime()` exposes whether a frontier is `PERSISTENT` or `SESSION_ONLY`
- `FrontierDataView.pathStyle()` is present only for Path frontiers
- `FrontierMutation.pathStyle(...)` only applies to Path frontiers; implementations reject it for Vertex or Chunk frontiers
- session-only frontiers are personal-only, not persisted, not sent to the server, and are not shareable
- sharing from the client API requires MapFrontiers to be present on the server for persistent personal frontiers

## Register a server plugin

Register your plugin from a server-side entry point:

```java
import games.alejandrocoria.mapfrontiers.api.MapFrontiersAPI;

public final class ExampleServerModEntrypoint {
    public static void register() {
        MapFrontiersAPI.registerServerPlugin(new ExampleServerPlugin());
    }
}
```

Then implement the plugin:

```java
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierShape;
import games.alejandrocoria.mapfrontiers.api.model.Point2i;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;
import games.alejandrocoria.mapfrontiers.api.plugin.IMapFrontiersServerPlugin;
import games.alejandrocoria.mapfrontiers.api.server.IMapFrontiersServerAPI;

import java.util.List;
import java.util.UUID;

public final class ExampleServerPlugin implements IMapFrontiersServerPlugin {
    @Override
    public String getModId() {
        return "examplemod";
    }

    @Override
    public void initialize(IMapFrontiersServerAPI api) {
        api.frontiers().createGlobalFrontier(
                new UserRef(UUID.fromString("11111111-1111-1111-1111-111111111111"), "ServerAdmin"),
                new DimensionId("minecraft:overworld"),
                FrontierShape.vertex(List.of(
                        new Point2i(-50, -50),
                        new Point2i(50, -50),
                        new Point2i(50, 50),
                        new Point2i(-50, 50)
                ))
        );

        int globalCount = api.frontiers().listGlobalFrontiers(new DimensionId("minecraft:overworld")).size();
        System.out.println("Global frontiers in overworld: " + globalCount);
    }

    @Override
    public void shutdown(IMapFrontiersServerAPI api) {
    }
}
```

Server-side notes:

- the server API works on authoritative server state immediately
- it is currently focused on global frontiers
- global frontier creation requires an explicit owner

## Basic concepts

- `FrontierShape` describes frontier geometry: vertex shapes are closed polygons, chunk shapes are sets of chunk coordinates, and Path shapes are open paths based on ordered points
- `FrontierLifetime` describes whether a frontier is `PERSISTENT` or `SESSION_ONLY`
- `PathStyle` describes per-frontier Path markers and label placement
- `PathMarkerId` constants are built-in convenience IDs, not a closed set
- creating a Path without an explicit style uses the MapFrontiers default Path style
- `FrontierMutation` is used for partial updates
- `FrontierDataView` is the read-only view returned by the API
- `EventBus` lets client and server plugins react to created, updated and deleted frontiers

## Current limitations

- each frontier name field is currently limited to 48 characters
- session-only support currently exists only for personal frontiers created from the client API
- client-side sharing methods require MapFrontiers to be present on the server, and session-only frontiers are always rejected
- when MapFrontiers is not present on the server, the GUI can send a copy of a frontier by chat, but that flow is not available through the API

## Further reading

After this overview, the best next step is to inspect the Javadoc in the main API interfaces and models:

- `MapFrontiersAPI`
- `IMapFrontiersClientAPI`
- `IMapFrontiersServerAPI`
- `ClientFrontierService`
- `ServerFrontierService`
- `FrontierLifetime`
- `FrontierMutation`
- `FrontierShape`
- `FrontierDataView`
- `PathMarkerId`
- `PathStyle`
