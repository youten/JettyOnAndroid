JettyOnAndroid
====
Jetty on Android.

## Projects

HelloJetty - Jetty on Android Sample, using WebSocket.

## How re-archive jetty-x.x.x.jar
- download binary tarball from http://download.eclipse.org/jetty/
- extract

```bash
tar xvf jetty-distribution-8.1.15.v20140411.tar.gz
cd jetty-distribution-8.1.15.v20140411/lib/ext/
```

- re-archive

```bash
cd jetty-distribution-8.1.15.v20140411/lib/ext/
jar xf ../jetty-continuation-*.jar
jar xf ../jetty-http-*.jar
jar xf ../jetty-io-*.jar
jar xf ../jetty-security-*.jar
jar xf ../jetty-server-*.jar
jar xf ../jetty-servlet-*.jar
jar xf ../jetty-util-*.jar
jar xf ../jetty-webapp-*.jar
jar xf ../jetty-websocket-*.jar
jar xf ../jetty-xml-*.jar
jar xf ../servlet-api-*.jar
rm -rf about*
jar cf ../jetty-8.1.15.jar *
cd ..
```

- copy jetty-x.x.x.jar to Android's Project libs/ folder.



