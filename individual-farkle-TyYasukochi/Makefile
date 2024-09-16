# Really simple wrapper on Maven's command to build, test, & run Farkle
# Maven is a package builder and runner tool for Java
#  https://maven.apache.org/

JAR_TARGET = target/farkle-*.jar

# Find all Java files recursively in the specified directory and its subdirectories
JAVA_FILES = $(shell find src/main/java/edu/gonzaga/Farkle -name "*.java")


# The first target is the default one run by make
# By convention it's called 'all'
all: build

# Target for the actual jar file to build and how to build it
# Only rebuilds if you edit the *.java main/source files
$(JAR_TARGET): $(JAVA_FILES)
	@echo "Building Farkle package - results in target/ directory"
	mvn package -DskipTests

# Builds the project's jar file
build: $(JAR_TARGET)

# Removed the jar file, and then builds a new one
force-build: clean build

# Runs the JUnit unit tests in the project
test:
	@echo "Running all tests"
	mvn test

# Runs the project in the terminal using maven - which builds if needed
mvn-run:
	@echo "Running Farkle main - see pom.xml for arguments passed"
	mvn exec:java

# Just builds (if needed) and then runs by hand in the terminal
run: build
	@echo "Running Farkle main without maven overhead"
	java -jar target/farkle-*.jar

# We use codespell to spellcheck the comments
spellcheck:
	codespell src/

# This is here to enable installing dependencies easy for Linux CI/CD pipelines
setup-dependencies:
	@echo "Installing tools needed for CI/CD pipelines on Debian"
	apt update
	apt install -y maven codespell checkstyle

# Javadoc makes a web site package of documentation from the source
javadoc:
	@echo "Creating javadoc materials"
	@echo "These go into: target/reports/apidocs/"
	@echo "Load up target/reports/apidocs/index.html to read them"
	-mvn javadoc:javadoc

# Removes built items like jar files, javadoc, and code coverage reports
clean:
	@echo "Removing temporary build files and directories"
	mvn clean

# Checks your style, but we're not doing this right now in the CI
lint:
	@echo "Running spotless linter to check source files"
	-mvn spotless:check

# If you find this, it'll make linting easy, just run it and it's all fixed up!
lint-autofix:
	@echo "Autofixing linting errors"
	mvn spotless:apply

# Generate the code coverage reports using maven/jacoco
# Results are in target/site/jacoco/*
coverage:
	@echo "Making code coverage reports"
	@echo "Output is in target/site/jacoco/index.html"
	mvn clean
	mvn jacoco:prepare-agent
	mvn test
	mvn jacoco:report

# Opens your coverage report using a web browser (works on Linux)
view-coverage:
	xdg-open target/site/jacoco/index.html
