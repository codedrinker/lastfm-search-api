# lastfm-search-api

Last.fm search tracks, artists, albums API wrapper in java

### Usage
```bash
git clone https://github.com/codedrinker/lastfm-search-api.git
mvn install 
```
```xml
<dependencies>
    <dependency>
        <groupId>com.github.codedrinker</groupId>
        <artifactId>lastfm-search-api</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```
### Search Tracks
```java
LastfmAuthorization authorization = new LastfmAuthorization();
authorization.setApiKey("app key");
LastfmFacade lastfmFacade = new LastfmFacade(authorization);
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setTrack("Hello");
queryDTO.setLimit(3);
LastfmResult lastfmResult = lastfmFacade.searchTrack(queryDTO);

```

### Search Albums
```java
LastfmAuthorization authorization = new LastfmAuthorization();
authorization.setApiKey("app key");
LastfmFacade lastfmFacade = new LastfmFacade(authorization);
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setAlbum("Hello");
queryDTO.setLimit(3);
LastfmResult lastfmResult = lastfmFacade.searchAlbum(queryDTO);
```

### Search Artists
```java
LastfmAuthorization authorization = new LastfmAuthorization();
authorization.setApiKey("app key");
LastfmFacade lastfmFacade = new LastfmFacade(authorization);
LastfmQuery queryDTO = new LastfmQuery();
queryDTO.setArtist("Hello");
queryDTO.setLimit(3);
LastfmResult lastfmResult = lastfmFacade.searchArtist(queryDTO);

```
