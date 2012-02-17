wget http://dev.umitproject.org/attachments/download/345/osmdroid-3.0.4.jar
wget http://dev.umitproject.org/attachments/download/346/maps-4_r1.jar
wget http://javamail-android.googlecode.com/files/additionnal.jar
wget http://javamail-android.googlecode.com/files/mail.jar
wget http://javamail-android.googlecode.com/files/activation.jar
mvn install:install-file  -Dfile=osmdroid-3.0.4.jar \
                          -DgroupId=org.osmdroid \
                          -DartifactId=osmdroid \
                          -Dversion=3.0.4 \
                          -Dpackaging=jar 


mvn install:install-file  -Dfile=maps-4_r1.jar \
                          -DgroupId=com.google.android.maps \
                          -DartifactId=maps \
                          -Dversion=4_r1 \
                          -Dpackaging=jar 


mvn install:install-file  -Dfile=mail.jar \
                          -DgroupId=javamail-android \
                          -DartifactId=mail \
                          -Dversion=1.0 \
                          -Dpackaging=jar 

mvn install:install-file  -Dfile=activation.jar \
                          -DgroupId=javamail-android \
                          -DartifactId=activation \
                          -Dversion=1.0\
                          -Dpackaging=jar 

mvn install:install-file  -Dfile=additionnal.jar\
                          -DgroupId=javamail-android\
                          -DartifactId=additional\
                          -Dversion=1.0\
                          -Dpackaging=jar 

