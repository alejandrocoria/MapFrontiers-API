# MapFrontiers API

MapFrontiers API is the public Java API for [MapFrontiers](https://github.com/alejandrocoria/MapFrontiers), the Minecraft mod that lets players create, update and observe frontiers and collections from client or server integrations.

This repository contains the standalone API artifact used by mods and plugins that integrate with MapFrontiers.

## API entry points

MapFrontiers exposes two API entry points:

- client API, for client-side actions such as creating or updating personal or global frontiers and collections
- server API, for authoritative server-side actions such as listing and creating global frontiers and collections

Plugins register through `MapFrontiersAPI` and receive an API instance in `initialize(...)`.

Both entry points expose:

- `frontiers()`
- `collections()`
- `events()`

## Add the dependency

The API uses the `games.alejandrocoria` group and is currently consumed as a `-SNAPSHOT` build.

If you want to consume a locally published build from this repository:

```groovy
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation "games.alejandrocoria:mapfrontiers-api:0.1.0-SNAPSHOT"
}
```

To publish this repository to your local Maven cache:

```bash
./gradlew publishToMavenLocal
```

If you want to consume snapshot builds published remotely, add the Central Portal snapshots repository and keep `mavenCentral()` for regular dependencies:

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
import games.alejandrocoria.mapfrontiers.api.client.ActionStatus;
import games.alejandrocoria.mapfrontiers.api.client.CollectionActionResult;
import games.alejandrocoria.mapfrontiers.api.client.FrontierActionResult;
import games.alejandrocoria.mapfrontiers.api.client.IMapFrontiersClientAPI;
import games.alejandrocoria.mapfrontiers.api.event.CollectionUpdatedEvent;
import games.alejandrocoria.mapfrontiers.api.event.EventBus;
import games.alejandrocoria.mapfrontiers.api.event.FrontierCreatedEvent;
import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionMutation;
import games.alejandrocoria.mapfrontiers.api.model.CollectionVisibilityFlag;
import games.alejandrocoria.mapfrontiers.api.model.CollectionVisibilitySettings;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierBanner;
import games.alejandrocoria.mapfrontiers.api.model.FrontierCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.FrontierShape;
import games.alejandrocoria.mapfrontiers.api.model.Point2i;
import games.alejandrocoria.mapfrontiers.api.plugin.IMapFrontiersClientPlugin;

import java.util.EnumSet;
import java.util.List;

public final class ExampleClientPlugin implements IMapFrontiersClientPlugin {
    private EventBus.Subscription createdSubscription;
    private EventBus.Subscription collectionSubscription;

    @Override
    public String getModId() {
        return "examplemod";
    }

    @Override
    public void initialize(IMapFrontiersClientAPI api) {
        createdSubscription = api.events().subscribe(FrontierCreatedEvent.class, this::onFrontierCreated);
        collectionSubscription = api.events().subscribe(CollectionUpdatedEvent.class, this::onCollectionUpdated);

        CollectionActionResult collectionResult = api.collections().createPersonalCollection(
                CollectionCreateRequest.builder()
                        .name("Routes")
                        .color(0x33AAFF)
                        .visibility(CollectionVisibilitySettings.builder()
                                .visible(true)
                                .fullscreenZoom(128)
                                .minimapZoom(64)
                                .webmapZoom(256)
                                .build())
                        .banner(new FrontierBanner(11, "[]", 0))
                        .build()
        );

        CollectionActionResult temporaryCollectionResult = api.collections().createTemporaryPersonalCollection(
                CollectionCreateRequest.builder()
                        .name("Temporary routes")
                        .color(0x2288CC)
                        .build()
        );

        CollectionCreateRequest configuredDefaultsCollection = CollectionCreateRequest.builder(
                        DefaultValuesProfile.CONFIGURED)
                .name("Configured Routes")
                .build();

        api.collections().createPersonalCollection(configuredDefaultsCollection);

        collectionResult.collectionId().ifPresent(collectionId -> {
            api.collections().updatePersonalCollection(
                    collectionId,
                    CollectionMutation.builder()
                            .addVisibility(EnumSet.of(CollectionVisibilityFlag.WebmapOwner))
                            .removeVisibility(EnumSet.of(CollectionVisibilityFlag.MinimapBanner))
                            .fullscreenZoom(512)
                            .banner(new FrontierBanner(14, "[]", 8))
                            .build()
            );

            FrontierCreateRequest request = FrontierCreateRequest.builder(
                            new DimensionId("minecraft:overworld"),
                            FrontierShape.vertex(List.of(
                                    new Point2i(0, 0),
                                    new Point2i(100, 0),
                                    new Point2i(100, 100),
                                    new Point2i(0, 100)
                            )))
                    .collection(collectionId)
                    .names("Spawn", "Area")
                    .color(0x55FF55)
                    .build();

            FrontierActionResult frontierResult = api.frontiers().createPersonalFrontier(request);

            frontierResult.frontierId().ifPresent(frontierId ->
                    api.frontiers().updatePersonalFrontier(frontierId, FrontierMutation.name1("Spawn Base"))
            );
        });

        FrontierCreateRequest temporaryRequest = FrontierCreateRequest.builder(
                        new DimensionId("minecraft:overworld"),
                        FrontierShape.path(List.of(
                                new Point2i(0, 0),
                                new Point2i(100, 0),
                                new Point2i(160, 40)
                        )))
                .name1("Temporary route")
                .build();

        FrontierActionResult temporaryResult = api.frontiers().createTemporaryPersonalFrontier(temporaryRequest);
        if (temporaryResult.status() == ActionStatus.REJECTED) {
            System.out.println("Temporary frontier request was rejected");
        }
        if (temporaryCollectionResult.status() == ActionStatus.REJECTED) {
            System.out.println("Temporary collection request was rejected");
        }

        FrontierCreateRequest configuredDefaultsRequest = FrontierCreateRequest.builder(
                        new DimensionId("minecraft:overworld"),
                        FrontierShape.vertex(List.of(
                                new Point2i(200, 200),
                                new Point2i(260, 200),
                                new Point2i(260, 260),
                                new Point2i(200, 260)
                        )),
                        DefaultValuesProfile.CONFIGURED)
                .name1("Configured Spawn")
                .build();

        api.frontiers().createPersonalFrontier(configuredDefaultsRequest);
    }

    @Override
    public void shutdown(IMapFrontiersClientAPI api) {
        if (createdSubscription != null) {
            createdSubscription.unsubscribe();
            createdSubscription = null;
        }
        if (collectionSubscription != null) {
            collectionSubscription.unsubscribe();
            collectionSubscription = null;
        }
    }

    private void onFrontierCreated(FrontierCreatedEvent event) {
        System.out.println("Frontier created: " + event.frontier().id().value());
    }

    private void onCollectionUpdated(CollectionUpdatedEvent event) {
        System.out.println("Collection updated: " + event.collection().id().value());
    }
}
```

Client-side notes:

- create uses request objects: `FrontierCreateRequest` for frontiers and `CollectionCreateRequest` for collections
- create requests use `DefaultValuesProfile.BUILTIN` when no profile is specified
- `DefaultValuesProfile.CONFIGURED` uses the local player's configured defaults as the base for omitted create fields
- omitting an optional create field, or passing `null` to an optional create setter, delegates that field to the selected default profile
- updates use mutations: `FrontierMutation` and `CollectionMutation`
- client frontier and collection service methods must be called from the Minecraft client thread; the API does not schedule calls from other threads
- `CollectionVisibilitySettings` exposes the authoritative collection visibility state returned by the API
- `CollectionVisibilityFlag` covers the boolean collection visibility toggles, while collection zoom thresholds use dedicated numeric fields
- `createTemporaryPersonalFrontier(request)` and `createTemporaryPersonalCollection(request)` create `SESSION_ONLY` entities immediately in local state and return `APPLIED_LOCAL` with the created id and snapshot when successful
- `FrontierCreateRequest.collectionId()` lets a plugin create a frontier already attached to a collection
- many client actions are asynchronous and return `ActionStatus.ACCEPTED_ASYNC`
- creating, updating, or deleting `SESSION_ONLY` personal entities is immediate, independent of server availability, and returns `ActionStatus.APPLIED_LOCAL` when successful
- `FrontierDataView` and `CollectionDataView` are snapshots, not live objects
- `FrontierDataView.lifetime()` and `CollectionDataView.lifetime()` expose whether an entity is `PERSISTENT` or `SESSION_ONLY`
- session-only frontiers and collections are personal-only, not persisted, and not sent to the server
- frontiers and collections must use the same lifetime when associated with each other
- the event bus is shared: frontier and collection events use the same `events()` stream
- collection visibility and banner metadata are authoritative collection state, not client-local override state
- collection visibility zoom helpers currently reflect the supported values of the underlying mod implementation
- `Visible` and the per-map zoom thresholds are independent in collection visibility settings

### Incremental geometry updates

Use the geometry methods on `FrontierMutation.Builder` when a small edit does not require replacing the whole shape:

```java
FrontierMutation pathEdit = FrontierMutation.builder()
        .removePathPointAt(0)
        .insertPathPointAt(1, new Point2i(120, 30))
        .insertPathPointAfterLast(new Point2i(180, 60))
        .build();

api.frontiers().updatePersonalFrontier(frontierId, pathEdit);
```

Operations execute in builder order and the complete mutation is atomic. Path, Vertex and Chunk operations cannot be
mixed with each other or with `shape(...)` in one mutation. Indexed inserts accept positions from zero through the
current size; indexed replacements and removals require an existing position. Those conditions are evaluated while
applying each operation, after preceding operations in the same mutation.

Automatic Path and Vertex insertion uses only X/Z distances and does not use GUI snapping or selection. Chunk additions
and removals are idempotent. `geometryEdits()` exposes an immutable sequence for inspection; operations are constructed
through the mutation builder.

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
import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionVisibilitySettings;
import games.alejandrocoria.mapfrontiers.api.model.DefaultValuesProfile;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierBanner;
import games.alejandrocoria.mapfrontiers.api.model.FrontierCreateRequest;
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
        UserRef owner = new UserRef(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "ServerAdmin"
        );

        CollectionId collectionId = api.collections().createGlobalCollection(
                owner,
                CollectionCreateRequest.builder()
                        .name("Protected areas")
                        .color(0xFFAA33)
                        .visibility(CollectionVisibilitySettings.builder()
                                .visible(true)
                                .fullscreenZoom(256)
                                .minimapZoom(128)
                                .webmapZoom(512)
                                .build())
                        .banner(new FrontierBanner(1, "[]", 0))
                        .build()
        ).id();

        api.frontiers().createGlobalFrontier(
                owner,
                FrontierCreateRequest.builder(
                                new DimensionId("minecraft:overworld"),
                                FrontierShape.vertex(List.of(
                                        new Point2i(-50, -50),
                                        new Point2i(50, -50),
                                        new Point2i(50, 50),
                                        new Point2i(-50, 50)
                                )))
                        .collection(collectionId)
                        .names("Spawn", "Protection")
                        .color(0xFF5555)
                        .build()
        );

        int globalFrontierCount = api.frontiers().listGlobalFrontiers(new DimensionId("minecraft:overworld")).size();
        int globalCollectionCount = api.collections().listGlobalCollections().size();

        System.out.println("Global frontiers in overworld: " + globalFrontierCount);
        System.out.println("Global collections: " + globalCollectionCount);
    }

    @Override
    public void shutdown(IMapFrontiersServerAPI api) {
    }
}
```

Server-side notes:

- the server API works on authoritative server state immediately
- server frontier and collection service methods must be called from the main server thread; the API does not schedule calls from other threads
- the server API currently exposes only global frontiers and global collections
- global frontier creation requires `UserRef owner` plus a `FrontierCreateRequest`
- global collection creation requires `UserRef owner` plus a `CollectionCreateRequest`
- create requests use `DefaultValuesProfile.BUILTIN` when no profile is specified
- `DefaultValuesProfile.CONFIGURED` is currently unsupported in the server API and should be treated as invalid there
- `updateGlobalFrontier(...)` returns an empty `Optional` when the target is unavailable or the mutation cannot be applied

## Basic concepts

- `FrontierCreateRequest` and `CollectionCreateRequest` are used for create operations
- `DefaultValuesProfile` selects which default base is used for omitted create fields
- `FrontierMutation` and `CollectionMutation` are used for partial updates
- `CollectionVisibilitySettings` represents authoritative collection visibility configuration
- `CollectionVisibilityFlag` represents the boolean subset of collection visibility toggles
- `ActionStatus` describes the outcome category of a client request
- `FrontierActionResult` and `CollectionActionResult` wrap client request results
- `CollectionDataView` is the read-only view returned for collections
- `FrontierDataView.collectionId()` exposes the collection membership of a frontier when present
- `FrontierShape` describes frontier geometry: vertex shapes are closed polygons, chunk shapes are sets of chunk coordinates, and path shapes are open paths based on ordered points
- `EntityLifetime` describes whether an already created frontier or collection is `PERSISTENT` or `SESSION_ONLY`
- `FrontierVisibilityFlag` defines the available display and announcement toggles for frontiers
- collection visibility zoom thresholds reject unsupported values with `IllegalArgumentException`
- `EventBus` lets client and server plugins react to created, updated and deleted frontiers and collections

## Current limitations

- each frontier name field and each collection name are currently limited to 48 characters
- temporary support currently exists only for personal frontiers and personal collections created from the client API
- session-only frontiers and collections are never part of the authoritative server lifecycle
- client-side sharing methods require MapFrontiers to be present on the server, and session-only frontiers are always rejected
- the API reflects the emission semantics used by the underlying mod; it does not fabricate extra public events

## Further reading

After this overview, the best next step is to inspect the Javadoc in the main API interfaces and models:

- `MapFrontiersAPI`
- `IMapFrontiersClientAPI`
- `IMapFrontiersServerAPI`
- `ClientFrontierService`
- `ClientCollectionService`
- `ServerFrontierService`
- `ServerCollectionService`
- `FrontierCreateRequest`
- `CollectionCreateRequest`
- `EntityLifetime`
- `FrontierMutation`
- `CollectionMutation`
- `FrontierShape`
- `FrontierDataView`
- `CollectionDataView`
