## ShardingSphere 读写分离案例

---

### 目录

1. [项目简介](#项目简介)
2. [功能说明](#功能说明)
3. [技术栈](#技术栈)
4. [环境准备](#环境准备)
5. [快速开始](#快速开始)
6. [核心配置](#核心配置)
7. [验证结果](#验证结果)
8. [常见问题](#常见问题)

---

### 项目简介

本项目演示如何使用 ShardingSphere 配置读写分离功能，实现主库负责写操作，从库负责读操作，从而提升数据库性能，减轻主库压力。

---

### 功能说明

- **主从同步**：使用主库进行写操作，从库进行读操作。
- **负载均衡**：支持从库的负载均衡。
- **故障切换**：从库不可用时自动切换。

---

### 技术栈

- **Spring Boot**: 简化开发，提高效率。
- **ShardingSphere-JDBC**: 实现读写分离和数据库中间件功能。
- **MySQL**: 作为数据库服务，配置主从同步。
- **Docker**: 快速搭建主从数据库环境。

---

### 环境准备

1. **JDK**: 版本要求 8 或更高。
2. **Maven**: 用于依赖管理和项目构建。
3. **Docker**: 安装并确保可以运行 `docker-compose`。
4. **数据库镜像**: 主从同步环境搭建参考 : https://blog.csdn.net/weixin_40040107/article/details/143088921

---

### 快速开始

#### 1. 克隆代码仓库
```bash
git clone https://gitee.com/knightzz98/sharding-jdbc-samples-parent
cd sharding-jdbc-samples-parent
```

#### 2. 使用 Docker 启动主从数据库
```bash
docker-compose up -d
```

`docker-compose.yml` 文件已配置主从同步，确保主库和从库正常运行。

#### 3. 启动 Spring Boot 项目
```bash
mvn spring-boot:run
```

---

### 核心配置

#### 数据源配置

`application.yml`：
```yaml
spring:
  application:
    name: sharging-jdbc-demo
  shardingsphere:
    mode:
      type: Memory
    datasource:
      names: master,slave1
      master:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://192.168.100.130:5500/db_test
        username: postgres
        password: postgress@123
      slave1:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://192.168.100.130:5501/db_test
        username: postgres
        password: postgress@123
    rules:
      readwrite-splitting:
        data-sources:
          # 数据源的逻辑名称
          myds:
            type: Static
            props:
              # 写数据源的名称
              write-data-source-name: master
              # 读数据源的名称, 多个以,分割 slave1,slave2
              read-data-source-names: slave1
        # 当前负载均衡算法名称, 可以根据下面的load-balancers选择具体的负载均衡算法
        load-balancer-name: alg_round
        load-balancers:
          # 负载均衡算法类型 alg_round = 轮训
          # alg_round = 轮训
          alg_round:
            type: ROUND_ROBIN
          # 随机算法
          alg_random:
            type: RANDOM
          # 权重算法
          alg_weight:
            type: WEIGHT
            props:
              slave1: 1
logging:
  level:
    com.zaxxer.hikari: WARN

```

---

### 验证结果

1. **写操作测试**  
   发送 POST 请求写入数据，数据应存储在主库：
```bash
curl -X POST http://localhost:8080/api/write -d '{"uname": "test_user"}' -H "Content-Type: application/json"
```

2. **读操作测试**  
   发送 GET 请求读取数据，数据应从从库返回：
```bash
curl -X GET http://localhost:8080/api/read
```

3. **负载均衡验证**  
   多次调用读接口，观察从库之间的负载均衡。

---

### 常见问题

1. **主从同步异常**
    - 确保主从数据库的 `binlog` 已正确配置。
    - 检查 `docker-compose.yml` 文件中的主从参数。

2. **负载均衡不生效**
    - 确认 `load-balancer` 配置为 `ROUND_ROBIN` 或其他策略。

3. **连接异常**
    - 检查数据库连接信息是否正确，端口是否冲突。

---

如有其他问题，请在 [Issues](https://github.com/example/sharding-sphere-readwrite-demo/issues) 中提交反馈！  