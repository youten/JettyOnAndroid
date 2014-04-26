Jetty Try
====
Jetty on Android.

HelloJetty - Servlet by Jetty on Android Simple Sample.

- How re-archive jetty-x.x.x.jar
1. download binary tarball from http://download.eclipse.org/jetty/
2. extract
tar xvf jetty-distribution-8.1.15.v20140411.tar.gz
cd jetty-distribution-8.1.15.v20140411/lib/ext/
3. re-archive
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
4. copy jetty-x.x.x.jar to Android's Project libs/ folder.



