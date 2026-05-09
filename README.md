# ⭐原创开源中间件

### 工具名称：EasyDB（数据库中间件）

### 工具版本：v3.0.0（稳定版）

### 开发语言：Java（JDK-21.0.8）

### 开发时间：2025年10月01日 ~ 至今持续更新

### 开源地址（Github）：https://github.com/BProbie/EasyDB/

### 开源协议（MIT）：https://github.com/BProbie/EasyDB/raw/refs/heads/master/LICENSE/

### 下载地址：https://github.com/BProbie/EasyDB/releases/tag/v3.0.0/

### 依赖工具：Maven

### 依赖技术：

- ##### JUnit（JUnit5）



# ⭐快速开始

### ① 添加JitPack仓库

```xml
<repositories>

    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy> 
        </snapshots>
    </repository>
    
</repositories>
```

### ② 添加中间件依赖

```xml
<dependencies>
    
    <dependency>
        <groupId>com.github.BProbie</groupId>
        <artifactId>EasyDB</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
    
</dependencies>
```

###  

# ⭐使用教程

### 本地数据库（包外）

```java
// 创建本地数据库实例化对象
LocalDB localDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalDB();

// 可选: 更改数据库文件储存路径
localDB.setFullFilePath(System.getProperty("user.dir") + File.separator + "EasyDB.db");

// 可选: 更改数据库文件备注文本
localDB.setComment("This Is A Comment");

/* 
    可选: 更改数据库提交模式
    true 为自动实时同步,数据时效性更显著
    false 为手动同步,开销更低,效率更高
*/
localDB.setIsAutoCommit(false);

// 连接到数据库
if (localDB.connect()) {
    
    // 添加数据,支持一对一的键值数据
    localDB.set("key", "value");
    
    // 添加数据,支持一对多的数据包设计
    localDB.set("probie", new Data().put("name", "probie").put("age", 18));
    
    /* 
        可选: 提交数据到本地数据库文件中
        提交后数据将以文件的形式进行长期保存
        不提交的短期数据默认保存在本地缓存中
    */
    localDB.commit();
    
    // 获取数据并输出
    System.out.println(localDB.get("key"));
    System.out.println(new Data().deCode(localDB.get("probie")));

}
```

### 本地数据库（包内）

```java
// 创建本地数据库实例化对象
LocalDB localDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalDB();

// 连接到数据库
if (localDB.connect(ClassLoader.getSystemResourceAsStream("EasyDB.db"))) {
    
    // 获取数据并输出
    System.out.println(localDB.get("date"));
    System.out.println(new Data().deCode(localDB.get("probie")));

}
```

### 远程数据库（本地）

```java
// 创建远程数据库实例化对象
LocalRemoteDB localRemoteDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalRemoteDB("https://raw.githubusercontent.com/BProbie/EasyDB/refs/heads/master/EasyDB.db");

// 可选: 更改数据库文件储存路径
localRemoteDB.setFullFilePath(System.getProperty("user.dir") + File.separator + "EasyDB.db");

// 下载数据库到本地
localRemoteDB.downloadDatabase();

/* 
    可选: 更改数据库提交模式
    true 为自动实时同步,数据时效性更显著
    false 为手动同步,开销更低,效率更高
*/
localRemoteDB.setIsAutoCommit(false);

// 连接到数据库
if (localRemoteDB.connect()) {
    
    // 添加数据,支持一对一的键值数据
    localRemoteDB.set("key", "value");
    
    // 添加数据,支持一对多的数据包设计
    localRemoteDB.set("probie", new Data().put("name", "probie").put("age", 18));
    
    /* 
        可选: 提交数据到本地数据库文件中
        提交后数据将以文件的形式进行长期保存
        不提交的短期数据默认保存在本地缓存中
    */
    localRemoteDB.commit();

    // 获取数据并输出
    System.out.println(localDB.get("key"));
    System.out.println(new Data().deCode(localDB.get("probie")));

}
```

### 远程数据库（远程）

```java
// 创建远程数据库实例化对象
RemoteDB remoteDB = EasyDB.getInstance().getRemoteDatabaseFactory().buildRemoteDB();

// 设置 Url
remoteDB.setUrl("jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?user=root.hgxarfmxttbwcfgcmxps&password=123456");

// 设置 UserName
remoteDB.setUserName("root");

// 设置 Password
remoteDB.setPassword("123456");

// 可选: 设置 Connection
remoteDB.setConnection(null);

// 可选: 设置 Driver
remoteDB.setDriver(null);

/* 
    可选: 更改数据库提交模式
    true 为自动实时同步,数据时效性更显著
    false 为手动同步,开销更低,效率更高
*/
remoteDB.setIsAutoCommit(false);

// 连接到数据库
if (remoteDB.connect()) {
    
    // 执行 SQL 语句
    remoteDB.runPreparedStatementExecute("");
    
    // 提交数据到远程数据库中
    remoteDB.commit();
}

// 同理,支持 Supabase 数据库
Supabase supabase = EasyDB.getInstance().getRemoteDatabaseFactory().buildSupabase();
```



# ⭐更多动能

### 回滚数据库

```java
database.rollback();
```

### 清理数据库

```java
database.clear();
```



# ⭐项目结构

```markdown
EasyDB
├── .github
├── .gitignore
├── .idea # 已在仓库删减
├── .mvn # 已在仓库删除
├── LICENSE
├── META-INF
├── out # 已在仓库删除
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── probie
│   │               └── easydb
│   │                   ├── Main.java
│   │                   ├── database
│   │                   │   ├── local
│   │                   │   │   ├── api
│   │                   │   │   │   ├── ILocalDatabase.java
│   │                   │   │   │   ├── ILocalDatabaseFactory.java
│   │                   │   │   │   ├── ILocalDB.java
│   │                   │   │   │   ├── ILocalDBComputer.java
│   │                   │   │   │   ├── ILocalDBFile.java
│   │                   │   │   │   ├── ILocalDBProperties.java
│   │                   │   │   │   ├── ILocalDBRemote.java
│   │                   │   │   │   └── ILocalRemoteDB.java
│   │                   │   │   ├── LocalDatabase.java
│   │                   │   │   ├── LocalDatabaseFactory.java
│   │                   │   │   ├── LocalDB.java
│   │                   │   │   ├── LocalDBComputer.java
│   │                   │   │   ├── LocalDBFile.java
│   │                   │   │   ├── LocalDBProperties.java
│   │                   │   │   ├── LocalDBRemote.java
│   │                   │   │   └── LocalRemoteDB.java
│   │                   │   └── remote
│   │                   │       ├── api
│   │                   │       │   ├── IRemoteDatabase.java
│   │                   │       │   ├── IRemoteDatabaseFactory.java
│   │                   │       │   ├── IRemoteDB.java
│   │                   │       │   └── ISupabase.java
│   │                   │       ├── RemoteDatabase.java
│   │                   │       ├── RemoteDatabaseFactory.java
│   │                   │       ├── RemoteDB.java
│   │                   │       └── Supabase.java
│   │                   ├── datapacket
│   │                   │   ├── api
│   │                   │   │   ├── IData.java
│   │                   │   │   ├── IDataPacket.java
│   │                   │   │   └── ISerializeBase64.java
│   │                   │   ├── Data.java
│   │                   │   ├── DataPacket.java
│   │                   │   └── SerializeBase64.java
│   │                   └── easydb
│   │                       ├── api
│   │                       │   └── IEasyDB.java
│   │                       └── EasyDB.java
│   └── test
│       └── java
│           └── com
│               └── probie
│                   └── easydb
│                       └── MainTest.java
└── target
```



# ⭐技术细节

### ① 支持基础的高并发需求

### ② 支持基础的分布式需求



# ⭐作者介绍

### 作者：probie

### 贡献：\[probie, probie, probie]



# ⭐疑问交流联系

### 如有疑问请通过提交Issue阐述，作者能看到且会经常查看！



# ❤❤❤