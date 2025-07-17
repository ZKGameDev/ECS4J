# ECS4J - Entity Component System Framework for Java

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)

ECS4J 是一个专为游戏服务器设计开发的开源ECS框架，采用 Java 语言实现。该框架提供完整的 ECS 架构支持，并具备组件热加载、系统执行序控制、实体/组件的即装即用（on-the-fly）与延迟加载（deferred）等关键特性。

本框架针对游戏服务器场景设计。每个 EcsWorld 实例可对应一个游戏房间（Room）或场景（Scene）。各 EcsWorld 被设计为线程专有（thread-confined），仅限在创建它的线程内访问，不支持跨线程调用。

## 🌟 主要特性

### 核心功能
- **实体管理**: 高效的实体创建、销毁和生命周期管理
- **组件系统**: 支持动态添加/移除组件，组件类型安全
- **系统执行**: 灵活的系统更新机制，支持多种执行模式
- **实体原型**: 基于组件组合的实体原型系统

### 高级特性
- **系统分组**: 支持系统分组管理，便于组织复杂逻辑
- **执行顺序控制**: 通过注解精确控制系统的执行顺序
- **延迟命令**: 支持延迟执行的实体操作命令
- **实体工厂**: 工厂模式创建实体，简化实体实例化
- **自动扫描**: 基于包扫描自动发现和注册系统、组件、工厂


## 📋 系统要求

- **Java**: 21 或更高版本
- **Maven**: 3.6 或更高版本
- **依赖**: 
  - Log4j2 (2.24.3+)
  - JUnit 5 (测试)

## 🚀 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>org.kgame</groupId>
    <artifactId>kgame-lib-ecs</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2. 创建组件

```java
public class PositionComponent implements EcsComponent {
    public float x, y, z;
}

public class HealthComponent implements EcsComponent {
    public int currentHealth;
    public int maxHealth;
}
```

### 3. 创建系统

```java
@UpdateInGroup(GameSystemGroup.class)
public class MovementSystem extends EcsUpdateSystemOne<PositionComponent> {
    
    @Override
    protected void update(Entity entity, PositionComponent position) {
        // 更新位置逻辑
        position.x += 1.0f;
    }
}
```

### 4. 创建实体工厂

```java
@EntityFactoryAttribute
public class PlayerFactory implements EntityFactory {
    
    @Override
    public Entity create(EcsEntityManager entityManager) {
        Entity entity = entityManager.createEntity(typeId());
        entity.addComponent(new PositionComponent());
        entity.addComponent(new HealthComponent());
        entity.init();
        return entity;
    }
    
    @Override
    public int typeId() {
        return 1; // 工厂类型ID 同一EcsWorld内不可重复。
    }
}
```

### 5. 创建系统组

```java
public class GameSystemGroup extends EcsSystemGroup {
    // 系统组实现
}
```

### 6. 使用ECS世界

```java
public class Game {
    private EcsWorld world;
    
    public void init() {
        // 创建ECS世界，指定要扫描的包名
        world = EcsWorld.generateInstance("com.example.game");
        // 可以设置自定义上下文
        world.setContext(this);
    }
    
    public void update(long currentTime) {
        // 更新ECS世界
        world.update(currentTime);
    }
    
    public void createPlayer() {
        // 通过工厂创建玩家实体
        Entity player = world.createEntity(PlayerFactory.class);
    }
    
    public void cleanup() {
        world.close();
    }
}
```

## 📖 注解系统

ECS4J提供了丰富的注解来控制系统的行为：

### 系统控制注解

#### @UpdateInGroup
- **作用**: 标记EcsSystem在指定EcsSystemGroup中执行更新
- **可作用对象**: EcsSystem类
- **参数**: `Class<? extends EcsSystemGroup> value()` - 系统组类型
- **说明**: 被此注解标记的EcsSystem将在指定EcsSystemGroup中执行更新。未被此注解标记的EcsSystem，属于和EcsSystemGroup同级的顶层系统，由EcsWorld调度。**注意：此注解不能用于EcsSystemGroup类，目前不支持SystemGroup的嵌套。**

#### @UpdateIntervalTime
- **作用**: 标记系统更新间隔时间
- **可作用对象**: EcsSystem类
- **参数**: `float interval()` - 更新间隔时间（秒）
- **说明**: 被此注解标记的系统将在指定时间间隔后执行更新。未被此注解标记的系统，每次更新周期都会执行。

#### @AlwaysUpdate
- **作用**: 标记EcsSystem始终执行更新，无论是否有匹配的实体
- **可作用对象**: EcsSystem类
- **参数**: 无
- **说明**: 被此注解标记的EcsSystem将在每个更新周期中执行，即使没有实体包含该EcsSystem所需的组件。没有被此注解标记的EcsSystem，在每个更新周期中，只有在有实体包含该EcsSystem所需的组件时，才会执行更新。

#### @UpdateAfterSystem
- **作用**: 标记EcsSystem在指定EcsSystem之后执行更新
- **可作用对象**: EcsSystem类
- **参数**: `Class<? extends EcsSystem>[] systemTypes()` - 目标系统类型数组
- **说明**: 被此注解标记的EcsSystem将在指定EcsSystem执行完成之后执行更新。相同条件的EcsSystem，会按照字典序执行。可用于SystemGroup。

#### @UpdateBeforeSystem
- **作用**: 标记EcsSystem在指定EcsSystem之前执行更新
- **可作用对象**: EcsSystem类
- **参数**: `Class<? extends EcsSystem>[] systemTypes()` - 目标系统类型数组
- **说明**: 被此注解标记的EcsSystem将在指定EcsSystem执行之前执行更新。相同条件的EcsSystem，会按照字典序执行。可用于SystemGroup。

### 实体工厂注解

#### @EntityFactoryAttribute
- **作用**: 标记实体工厂类，用于自动扫描和注册
- **可作用对象**: EntityFactory实现类
- **参数**: 无
- **说明**: 被此注解标记的EntityFactory实现类会被自动扫描和注册到EcsWorld中，可以通过工厂类型ID或工厂类创建实体。

## 🔧 系统类型

ECS4J提供了多种预定义的系统基类：

- `EcsUpdateSystemOne<T>`: 处理单个组件的系统
- `EcsUpdateSystemTwo<T1, T2>`: 处理两个组件的系统
- `EcsUpdateSystemThree<T1, T2, T3>`: 处理三个组件的系统
- `EcsUpdateSystemFour<T1, T2, T3, T4>`: 处理四个组件的系统
- `EcsUpdateSystemFive<T1, T2, T3, T4, T5>`: 处理五个组件的系统
- `EcsUpdateSystemSingle<T>`: 处理单个组件的系统（排除其他组件）
- `EcsUpdateSystemExcludeOne<T, E>`: 处理组件T但排除组件E的系统
- `EcsInitializeSystem<T>`: 实体初始化系统
- `EcsDestroySystem<T>`: 实体销毁系统
- `EcsLogicSystem`: 逻辑系统基类

## 📦 系统组

系统组（EcsSystemGroup）是ECS4J中用于组织和管理系统执行的重要机制。系统组本身也是一个系统，可以包含多个子系统，并按照特定的顺序执行它们。

### 系统组特性

- **自动管理**: 系统组会自动扫描并管理所有使用`@UpdateInGroup`注解标记的系统
- **执行顺序**: 系统组内的系统会按照`@UpdateAfterSystem`和`@UpdateBeforeSystem`注解定义的顺序执行
- **生命周期**: 系统组具有完整的生命周期管理，包括初始化、更新和销毁
- **动态管理**: 支持在运行时添加和移除系统

### 系统组层次结构

```
EcsWorld
├── 顶层系统 (未使用@UpdateInGroup注解)
│   ├── SystemA
│   └── SystemB
└── 系统组
    ├── GameSystemGroup
    │   ├── InputSystem
    │   ├── LogicSystem
    │   └── RenderSystem
    └── PhysicsSystemGroup
        ├── CollisionSystem
        └── MovementSystem
```

## ⚡ 延迟命令系统

```java
public class MySystem extends EcsUpdateSystemOne<MyComponent> {
    
    @Override
    protected void update(Entity entity, MyComponent component) {
        // 添加延迟命令
        addDelayCommand(new SystemCommandAddComponent(entity, new NewComponent()), 
                      EcsCommandScope.SYSTEM);
    }
}
```

### 命令作用域

- `SYSTEM`: 系统作用域，命令在当前系统执行完成后执行
- `SYSTEM_GROUP`: 系统组作用域，命令在当前系统组执行完成后执行
- `WORLD`: 世界作用域，命令在本次世界更新完成后执行

## 🎮 实体操作

```java
// 获取组件
PositionComponent position = entity.getComponent(PositionComponent.class);

// 检查组件
if (entity.hasComponent(HealthComponent.class)) {
    // 处理逻辑
}

// 添加组件
entity.addComponent(new HealthComponent());

// 移除组件
entity.removeComponent(PositionComponent.class);

// 销毁实体
world.requestDestroyEntity(entity);
```

## 🧪 测试示例

项目包含丰富的测试用例，展示了各种功能的使用方法：

- **组件操作测试**: 演示组件的添加、移除操作（立即和延迟）
- **实体操作测试**: 演示实体的创建、销毁操作（立即和延迟）
- **系统测试**: 演示系统执行顺序控制、更新间隔功能和复杂系统组合的使用
- **资源清理测试**: 演示ECS世界销毁和资源清理功能


## 📁 项目结构

```
src/
├── main/java/org/kgame/lib/ecs/
│   ├── annotation/          # 注解定义
│   ├── command/            # 命令系统
│   ├── core/               # 核心实现
│   ├── exception/          # 异常定义
│   ├── extensions/         # 扩展功能
│   └── tools/              # 工具类
└── test/java/org/kgame/lib/ecstest/
    ├── component/          # 组件测试
    │   ├── add/            # 组件添加测试
    │   └── remove/         # 组件移除测试
    ├── entity/             # 实体测试
    │   ├── add/            # 实体添加测试
    │   └── remove/         # 实体移除测试
    ├── system/             # 系统测试
    │   ├── interval/       # 系统间隔测试
    │   ├── mixed/          # 混合系统测试
    │   └── order/          # 系统顺序测试
    │       ├── custom/     # 自定义顺序测试
    │       └── def/        # 默认顺序测试
    └── dispose/            # 资源清理测试
```


## 📄 许可证

本项目采用 Apache License 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🔗 相关链接

- [项目主页](https://github.com/ZKGameDev/ECS4J)
- [问题反馈](https://github.com/ZKGameDev/ECS4J/issues)

## 📞 联系方式

如有问题或建议，请通过以下方式联系：

- 提交 Issue: [GitHub Issues](https://github.com/ZKGameDev/ECS4J/issues)
- 邮箱: chinazhangk@gmail.com

---