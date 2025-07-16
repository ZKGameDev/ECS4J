# ECS4J - Entity Component System Framework for Java

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)

ECS4J 是一个用Java语言开发的实体组件系统（Entity Component System）框架，专为游戏开发和模拟系统设计。该框架提供了高性能、易扩展的ECS架构实现，支持组件动态添加/移除、系统执行顺序控制、实体工厂模式等特性。

## 🌟 主要特性

### 核心功能
- **实体管理**: 高效的实体创建、销毁和生命周期管理
- **组件系统**: 支持动态添加/移除组件，组件类型安全
- **系统执行**: 灵活的系统更新机制，支持多种执行模式
- **实体原型**: 基于组件组合的实体原型系统，提高性能

### 高级特性
- **系统分组**: 支持系统分组管理，便于组织复杂逻辑
- **执行顺序控制**: 通过注解精确控制系统的执行顺序
- **延迟命令**: 支持延迟执行的实体操作命令
- **实体工厂**: 工厂模式创建实体，简化实体实例化
- **自动扫描**: 基于包扫描自动发现和注册系统、组件、工厂

### 性能优化
- **内存池**: 高效的实体和组件内存管理
- **批量操作**: 支持批量实体操作，减少性能开销
- **类型缓存**: 组件类型索引缓存，提高查询效率

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
    <version>1.0.0-SNAPSHOT</version>
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
    protected void onInit() {
        // 配置实体过滤器
        configEntityFilter(ComponentTypeQuery.of(PositionComponent.class));
    }
    
    @Override
    protected void onStart() {
        System.out.println("MovementSystem started");
    }
    
    @Override
    protected void onUpdate() {
        Collection<Entity> entities = getAllMatchEntity();
        for (Entity entity : entities) {
            PositionComponent position = entity.getComponent(PositionComponent.class);
            // 更新位置逻辑
            position.x += 1.0f;
        }
    }
    
    @Override
    protected void onStop() {
        System.out.println("MovementSystem stopped");
    }
    
    @Override
    protected void onDestroy() {
        System.out.println("MovementSystem destroyed");
    }
}
```

### 4. 创建实体工厂

```java
@EntityFactoryAttribute
public class PlayerFactory implements EntityFactory {
    
    @Override
    public Entity create(EcsEntityManager entityManager) {
        Entity entity = entityManager.createEntity(1); // 1是工厂类型ID
        entity.addComponent(new PositionComponent());
        entity.addComponent(new HealthComponent());
        entity.init();
        return entity;
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

## 📖 详细文档

### 注解系统

ECS4J提供了丰富的注解来控制系统的行为：

#### 系统执行顺序控制

```java
@UpdateInGroup(GameSystemGroup.class)
@UpdateAfterSystem(systemTypes = {InputSystem.class})
@UpdateBeforeSystem(systemTypes = {RenderSystem.class})
public class LogicSystem extends EcsUpdateSystemOne<LogicComponent> {
    // 系统实现
}
```

#### 系统更新间隔

```java
@UpdateIntervalTime(interval = 0.016f) // 60 FPS
public class FixedUpdateSystem extends EcsUpdateSystemOne<FixedComponent> {
    // 系统实现
}
```

#### 始终更新系统

```java
@AlwaysUpdate
public class GlobalSystem extends EcsUpdateSystemOne<GlobalComponent> {
    // 系统实现
}
```

### 系统类型

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

### 延迟命令系统

```java
public class MySystem extends EcsUpdateSystemOne<MyComponent> {
    
    @Override
    protected void onUpdate() {
        Collection<Entity> entities = getAllMatchEntity();
        for (Entity entity : entities) {
            // 添加延迟命令
            addDelayCommand(new SystemCommandAddComponent(entity, new NewComponent()), 
                          EcsCommandScope.SYSTEM);
        }
    }
}
```

### 实体操作

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

- **组件操作测试**: 演示组件的添加、移除操作
- **系统顺序测试**: 展示系统执行顺序控制
- **系统间隔测试**: 演示系统更新间隔功能
- **混合系统测试**: 展示复杂系统组合的使用

运行测试：
```bash
mvn test
```

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
    ├── dispose/           # 销毁测试
    └── system/            # 系统测试
```

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 Apache License 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🔗 相关链接

- [项目主页](https://github.com/ZKGameDev/ECS4J)
- [问题反馈](https://github.com/ZKGameDev/ECS4J/issues)
- [Wiki文档](https://github.com/ZKGameDev/ECS4J/wiki)

## 📞 联系方式

如有问题或建议，请通过以下方式联系：

- 提交 Issue: [GitHub Issues](https://github.com/ZKGameDev/ECS4J/issues)
- 邮箱: [项目维护者邮箱]

---

**注意**: 本框架为非线程安全设计，只能在单线程环境中使用。如需多线程支持，请确保在适当的同步机制下使用。 