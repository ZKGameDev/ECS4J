# ECS4J - Entity Component System Framework for Java

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)

<div align="center">

[English](README.md) | [中文](README_CN.md)

</div>

ECS4J is an open-source ECS framework designed and developed specifically for game servers, implemented in Java. This framework provides complete ECS architecture support, including runtime component addition/removal, system execution order control, on-the-fly and deferred loading of entities/components, and other key features.

This framework is designed for game server scenarios. A single process can create multiple EcsWorld instances, each corresponding to a game room (Room) or scene (Scene). Each EcsWorld is designed to be thread-confined, accessible only within the thread that created it, and does not support cross-thread calls.

## 🌟 Key Features

### Core Functionality
- **Entity Management**: Efficient entity creation, destruction, and lifecycle management
- **Component System**: Support for dynamic component addition/removal with type safety
- **System Execution**: Flexible system update mechanism with multiple execution modes
- **Entity Prototypes**: Component-based entity prototype system

### Advanced Features
- **System Groups**: Support for system group management, facilitating complex logic organization
- **Execution Order Control**: Precise control of system execution order through annotations
- **Deferred Commands**: Support for deferred execution of entity operation commands
- **Entity Factories**: Factory pattern for entity creation, simplifying entity instantiation
- **Auto-scanning**: Automatic discovery and registration of systems, components, and factories based on package scanning

## 📋 System Requirements

- **Java**: Version 21 or higher
- **Maven**: Version 3.6 or higher
- **Dependencies**: 
  - Log4j2 (2.24.3+)
  - JUnit 5 (for testing)

## 🚀 Quick Start

### 1. Add Dependency

```xml
<dependency>
    <groupId>org.kgame</groupId>
    <artifactId>kgame-lib-ecs</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2. Create Components

```java
public class PositionComponent implements EcsComponent {
    public float x, y, z;
}

public class HealthComponent implements EcsComponent {
    public int currentHealth;
    public int maxHealth;
}
```

### 3. Create Systems

```java
@UpdateInGroup(GameSystemGroup.class) 
// Systems without @UpdateInGroup annotation belong to top-level systems, 
// at the same level as SystemGroups, all scheduled directly by EcsWorld
public class MovementSystem extends EcsUpdateSystemOne<PositionComponent> {
    
    @Override
    protected void update(Entity entity, PositionComponent position) {
        // Update position logic
        position.x += 1.0f;
    }
}
```

### 4. Create Entity Factory

```java
@EntityFactoryAttribute
public class PlayerFactory extends BaseEntityFactory {

    @Override
    protected Collection<EcsComponent> generateComponent() {
      return List.of(new PositionComponent(), new HealthComponent());
    }
    
    @Override
    public int typeId() {
        return 1; // Factory type ID, must be unique within the same EcsWorld
    }
}
```

### 5. Create System Group

```java
public class GameSystemGroup extends EcsSystemGroup {
    // System group implementation
    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {
  
    }
}
```

### 6. Use ECS World

```java
public class Game {
    private EcsWorld world;
    
    public void init() {
        // Create ECS world, specify package names to scan
        world = EcsWorld.generateInstance("com.example.game");
        // Can set custom context
        world.setContext(this);
    }
    
    public void update(long currentTime) {
        // Update ECS world
        world.update(currentTime);
    }
    
    public void createPlayer() {
        // Create player entity through factory
        Entity player = world.createEntity(PlayerFactory.class);
    }
    
    public void cleanup() {
        world.close();
    }
}
```

### 7. Entity Operations

```java
// Get component
PositionComponent position = entity.getComponent(PositionComponent.class);

// Check component
if (entity.hasComponent(HealthComponent.class)) {
    // Handle logic
}

// Add component
entity.addComponent(new HealthComponent());

// Remove component
entity.removeComponent(PositionComponent.class);

// Destroy entity
world.requestDestroyEntity(entity);
```

## 📖 Annotations

ECS4J provides rich annotations to control system behavior:

### System Control Annotations

#### @UpdateInGroup
- **Purpose**: Marks an EcsSystem to execute updates within a specified EcsSystemGroup
- **Target**: EcsSystem classes
- **Parameters**: `Class<? extends EcsSystemGroup> value()` - System group type
- **Description**: EcsSystem marked with this annotation will execute updates within the specified EcsSystemGroup. EcsSystem not marked with this annotation belong to top-level systems at the same level as EcsSystemGroup, scheduled by EcsWorld. **Note: This annotation cannot be used on EcsSystemGroup classes, and nested SystemGroups are not currently supported.**

#### @UpdateIntervalTime
- **Purpose**: Marks system update interval time
- **Target**: EcsSystem classes
- **Parameters**: `float interval()` - Update interval time (seconds)
- **Description**: Systems marked with this annotation will execute updates after the specified time interval. Systems not marked with this annotation will execute every update cycle.

#### @AlwaysUpdate
- **Purpose**: Marks EcsSystem to always execute updates, regardless of whether there are matching entities
- **Target**: EcsSystem classes
- **Parameters**: None
- **Description**: EcsSystem marked with this annotation will execute in every update cycle, even if no entities contain the components required by this EcsSystem. EcsSystem not marked with this annotation will only execute updates in each update cycle when there are entities containing the required components.

#### @UpdateAfterSystem
- **Purpose**: Marks EcsSystem to execute updates after specified EcsSystem within the same group
- **Target**: EcsSystem classes
- **Parameters**: `Class<? extends EcsSystem>[] systemTypes()` - Target system type array
- **Description**: EcsSystem marked with this annotation will execute updates after the specified EcsSystem completes. EcsSystem with the same conditions will execute in dictionary order. Can be used in SystemGroup.

#### @UpdateBeforeSystem
- **Purpose**: Marks EcsSystem to execute updates before specified EcsSystem within the same group
- **Target**: EcsSystem classes
- **Parameters**: `Class<? extends EcsSystem>[] systemTypes()` - Target system type array
- **Description**: EcsSystem marked with this annotation will execute updates before the specified EcsSystem. EcsSystem with the same conditions will execute in dictionary order. Can be used in SystemGroup.

### Entity Factory Annotations

#### @EntityFactoryAttribute
- **Purpose**: Marks entity factory classes for automatic scanning and registration
- **Target**: EntityFactory implementation classes
- **Parameters**: None
- **Description**: EntityFactory implementation classes marked with this annotation will be automatically scanned and registered to EcsWorld, and entities can be created through factory type ID or factory class.

## 🔧 Predefined System Types

ECS4J provides various predefined system base classes:

- `EcsUpdateSystemOne<T>`: System handling a single component
- `EcsUpdateSystemTwo<T1, T2>`: System handling two components
- `EcsUpdateSystemThree<T1, T2, T3>`: System handling three components
- `EcsUpdateSystemFour<T1, T2, T3, T4>`: System handling four components
- `EcsUpdateSystemFive<T1, T2, T3, T4, T5>`: System handling five components
- `EcsUpdateSystemSingle<T>`: System handling a single component (excluding other components)
- `EcsUpdateSystemExcludeOne<T, E>`: System handling component T but excluding component E
- `EcsInitializeSystem<T>`: Entity initialization system
- `EcsDestroySystem<T>`: Entity destruction system
- `EcsLogicSystem`: Logic system base class

## 📦 System Groups (EcsSystemGroup)

System Groups (EcsSystemGroup) are an important mechanism in ECS4J for organizing and managing system execution. A system group is itself a system that can contain multiple subsystems and execute them in a specific order.

### System Group Features

- **Automatic Management**: System groups automatically scan and manage all systems marked with the `@UpdateInGroup` annotation
- **Execution Order**: Systems within a system group execute according to the order defined by `@UpdateAfterSystem` and `@UpdateBeforeSystem` annotations
- **Lifecycle**: System groups have complete lifecycle management, including initialization, updates, and destruction
- **Dynamic Management**: Support for adding and removing Systems at runtime

### System Group Hierarchy

```
EcsWorld
├── Top-level Systems (without @UpdateInGroup annotation)
│   ├── SystemA
│   └── SystemB
└── System Groups
    ├── GameSystemGroup
    │   ├── InputSystem
    │   ├── LogicSystem
    │   └── RenderSystem
    └── PhysicsSystemGroup
        ├── CollisionSystem
        └── MovementSystem
```

## ⚡ Deferred Command System

ECS4J provides a complete deferred command system that allows safe execution of entity and component operations during system execution. Deferred commands execute within specified scopes, ensuring atomicity and consistency of operations.

```java
public class MySystem extends EcsUpdateSystemOne<MyComponent> {
    
    @Override
    protected void update(Entity entity, MyComponent component) {
        // Add deferred command
        addDelayCommand(new SystemCommandAddComponent(entity, new NewComponent()), 
                      EcsCommandScope.SYSTEM);
    }
}
```

### Available Deferred Commands

ECS4J provides the following four deferred commands:

- **SystemCommandCreateEntity**: Deferred entity creation
- **SystemCommandDestroyEntity**: Deferred entity destruction
- **SystemCommandAddComponent**: Deferred component addition
- **SystemCommandRemoveComponent**: Deferred component removal

### Command Scopes

Deferred commands support three scopes that control command execution timing:

- **`SYSTEM`**: System scope, commands execute after the current System completes
- **`SYSTEM_GROUP`**: System group scope, commands execute after the current system group completes
- **`WORLD`**: World scope, commands execute after the current world update completes

## 🎮 Entity Operation Timing

ECS4J divides entity operations into **immediate effect** and **deferred effect** modes:

### Immediate Effect Operations
- **Entity Addition**: Through `ecsworld.createEntity()` calls
- **Component Addition/Removal**: Through direct calls to `entity.addComponent()` and `entity.removeComponent()`
- **Effect Timing**: Operations take effect immediately, accessible by other Systems after the current System completes

### Deferred Effect Operations

#### Entity Destruction
- **Operation Method**: Request destruction through `world.requestDestroyEntity()`
- **Effect Timing**: Executes after the current world update completes, ensuring all Systems can process the entity

#### Deferred Command Operations
- **All Operations**: Execute through the deferred command system (SystemCommandCreateEntity, SystemCommandDestroyEntity, SystemCommandAddComponent, SystemCommandRemoveComponent)
- **Effect Timing**: Refer to the [Deferred Command System](#-deferred-command-system) section

## 🧪 Test Examples

The project includes comprehensive test cases demonstrating various functionality usage:

- **Component Operation Tests**: Demonstrates component addition and removal operations (immediate and deferred)
- **Entity Operation Tests**: Demonstrates entity creation and destruction operations (immediate and deferred)
- **System Tests**: Demonstrates system execution order control, update interval functionality, and complex system combination usage
- **Resource Cleanup Tests**: Demonstrates ECS world destruction and resource cleanup functionality

## 📁 Project Structure

```
src/
├── main/java/org/kgame/lib/ecs/
│   ├── annotation/          # Annotation definitions
│   ├── command/            # Command system
│   ├── core/               # Core implementation
│   ├── exception/          # Exception definitions
│   ├── extensions/         # Extension functionality
│   └── tools/              # Utility classes
└── test/java/org/kgame/lib/ecstest/
    ├── component/          # Component tests
    │   ├── add/            # Component addition tests
    │   └── remove/         # Component removal tests
    ├── entity/             # Entity tests
    │   ├── add/            # Entity addition tests
    │   └── remove/         # Entity removal tests
    ├── system/             # System tests
    │   ├── interval/       # System interval tests
    │   ├── mixed/          # Mixed system tests
    │   └── order/          # System order tests
    │       ├── custom/     # Custom order tests
    │       └── def/        # Default order tests
    └── dispose/            # Resource cleanup tests
```

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 🔗 Related Links

- [Project Homepage](https://github.com/ZKGameDev/ECS4J)
- [Issue Reporting](https://github.com/ZKGameDev/ECS4J/issues)

## 📞 Contact

For questions or suggestions, please contact us through:

- Submit Issue: [GitHub Issues](https://github.com/ZKGameDev/ECS4J/issues)
- Email: chinazhangk@gmail.com

--- 