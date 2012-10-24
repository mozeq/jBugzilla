classes:
	javac -d "bin" -classpath "bin"  "src/org/mozeq/bugzilla/BugzillaTicket.java"
	javac -d "bin" -classpath "bin:/usr/share/java/commons-httpclient.jar:jars/commons-logging-1.1.jar:jars/ws-commons-util-1.0.2.jar:jars/xmlrpc-client-3.1.3.jar:jars/xmlrpc-common-3.1.3.jar:jars/xmlrpc-server-3.1.3.jar" "src/org/mozeq/bugzilla/BugzillaProxy.java"

default: classes

jar: default
	jar cvf jBugzilla.jar -C bin org

test: classes
	javac -d "bin" -classpath "bin"  "src/org/mozeq/bugzilla/jBugzillaTest.java"
	sh ./runtest.sh

clean:
	rm -rf `find bin -name ".class"`
