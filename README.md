# lastfm-search-api

Last.fm search tracks, artists, albums API wrapper in java

## Usage
### Install
```bash
git clone https://github.com/codedrinker/lastfm-search-api.git
mvn install -DskipTests
```
### Dependency
```xml
<dependencies>
    <dependency>
        <groupId>com.github.codedrinker</groupId>
        <artifactId>lastfm-search-api</artifactId>
        <version>1.0.1</version>
    </dependency>
</dependencies>
```
### Get Instance
#### Spring
```xml
<bean id="lastfmFacade" class="com.github.codedrinker.LastfmFacade">
    <constructor-arg>
        <bean class="com.github.codedrinker.config.Configuration">
            <property name="lastfmAppKey" value="${lastfm.appkey}"></property>
            <property name="lastfmYoutubeAppName" value="${lastfm.appName}"></property>
            <property name="lastfmYoutubeAppKey" value="${lastfm.youtubeKey}"></property>
        </bean>
    </constructor-arg>
</bean>
```
#### Java
Make sure `lastfm.properties` in classpath. `LastfmFacade` will auto load configuration.
```java
LastfmFacade lastfmFacade = new LastfmFacade();
```

#### Search Tracks
```java
LastfmFacade lastfmFacade = new LastfmFacade();
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setTrack("Hello");
queryDTO.setLimit(3);
LastfmResult tracks = lastfmFacade.searchTrack(queryDTO);
```

### Search Albums
```java
LastfmFacade lastfmFacade = new LastfmFacade();
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setAlbum("Hello");
queryDTO.setLimit(3);
LastfmResult albums = lastfmFacade.searchAlbum(queryDTO);
```

### Search Artists
```java
LastfmFacade lastfmFacade = new LastfmFacade();
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setArtist("Hello");
queryDTO.setLimit(3);
LastfmResult artists = lastfmFacade.searchArtist(queryDTO);

```
# post-dispatcher
