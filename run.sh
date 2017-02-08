cd /root/gasoapi
java -jar /root/gasoapi/gasoapi-0.1.0-SNAPSHOT-standalone.jar

mv  /root/gasoapi/places.xml /usr/share/nginx/html/api/gasoapi/
mv  /root/gasoapi/prices.xml /usr/share/nginx/html/api/gasoapi/
