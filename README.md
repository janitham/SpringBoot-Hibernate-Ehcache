### Hibernate Second Level caching example with Redis

<table>
    <tr>
        <td><img src="https://sdtimes.com/wp-content/uploads/2018/03/spring-boot-490x257.png" width="300" height="100" /></td>
        <td><img src="http://hibernate.org/images/hibernate-logo.svg" width="300" height="200" /></td>
        <td><img src="https://www.tutorialspoint.com/h2_database/images/h2-database-mini-logo.jpg" width="300" height="200" /></td>
        <td><img src="https://upload.wikimedia.org/wikipedia/en/thumb/6/6b/Redis_Logo.svg/1200px-Redis_Logo.svg.png" width="300" height="100" /></td>
        <td><img src="https://pluralsight2.imgix.net/paths/images/docker-copy-3135ce60d0.png" width="300" height="200" /></td>
    </tr>
</table>

This example uses third party hibernate cache provider which implements redis caching as hibernate secound level caching.
```
<dependency>
    <groupId>com.github.debop</groupId>
    <artifactId>hibernate-redis</artifactId>
    <version>2.5.0-SNAPSHOT</version>
</dependency>
```

#### Configuration

pom.xml
```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.debop</groupId>
            <artifactId>hibernate-redis</artifactId>
            <version>2.5.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>de.ruedigermoeller</groupId>
            <artifactId>fst</artifactId>
            <version>2.57</version>
        </dependency>
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
            <version>3.6.5</version>
        </dependency>
    </dependencies>
```

application.properties
```
# Caching
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.use_query_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.redis.hibernate5.SingletonRedisRegionFactory
spring.jpa.properties.hibernate.cache.region_prefix=hibernate
spring.jpa.properties.hibernate.cache.provider_configuration_file_resource_path=hibernate-redis.properties
```

hibernate-redis.properties
```
# redisson configuraiton file path
redisson-config=conf/redisson.yaml

# default expiration time for non-listed region names
redis.expiryInSeconds.default=5

# default local cache settings for non-listed region names

# if value is `ON_CHANGE`, `ON_CHANGE_WITH_CLEAR_ON_RECONNECT` or `ON_CHANGE_WITH_LOAD_ON_RECONNECT`
# corresponding map entry is removed from cache across all Hibernate Local Cache instances
# during every invalidation message sent on each map entry update/remove operation
redis.localCache.invalidationPolicy.default=ON_CHANGE_WITH_CLEAR_ON_RECONNECT

# if cache size is 0 then local cache is unbounded
redis.localCache.cacheSize.default=10000

# LFU, LRU, SOFT, WEAK and NONE policies are available
redis.localCache.evictionPolicy.default=LFU

# time to live for each map entry in local cache
redis.localCache.timeToLiveInMillis.default=3

# max idle time for each map entry in local cache
redis.localCache.maxIdleInMillis.default=3

# expiration time for 'book' region name
redis.expiryInSeconds.hibernate.book=4
```

redisson.yaml
```
singleServerConfig:
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  connectTimeout: 1000
  timeout: 1000
  retryAttempts: 1
  retryInterval: 1000
  reconnectionTimeout: 3000
  failedAttempts: 1
  password: null
  subscriptionsPerConnection: 5
  clientName: null
  address: "redis://127.0.0.1:6379"
  subscriptionConnectionMinimumIdleSize: 1
  subscriptionConnectionPoolSize: 25
  connectionMinimumIdleSize: 5
  connectionPoolSize: 100
  database: 0
  dnsMonitoring: false
  dnsMonitoringInterval: 5000
threads: 0
# Codec
codec: !<org.redisson.codec.SnappyCodec> {}
useLinuxNativeEpoll: false
eventLoopGroup: null
```

#### How to run

1. clone get git repository ```git clone https://github.com/janitham/SpringBoot-Hibernate-Ehcache.git```
2. run redis on docker. ```docker-compose up -d```
3. run the application ```mvn spring-boot:run``` and observe the console

#####Note: 
The https://github.com/debop/hibernate-redis master has build locally to get changes, until 2.5.0 get released.