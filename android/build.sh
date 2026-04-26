#!/bin/bash
# Build script for Tranzo Android App
# Works around gradle wrapper issues on Unix-like systems

# Set Java home if not already set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME="$(dirname $(dirname $(which java)))"
fi

# Check if Java exists
if [ ! -f "$JAVA_HOME/bin/java" ]; then
    echo "ERROR: Java not found at $JAVA_HOME"
    echo "Please install Java 19+ or set JAVA_HOME environment variable"
    exit 1
fi

# Run gradle build
echo "Building Tranzo Android App..."
"$JAVA_HOME/bin/java" -Xmx2048m -cp "gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"

exit $?
