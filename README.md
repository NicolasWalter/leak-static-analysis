# leak-static-analysis

Para analizar el codigo en "examples.main":

mvn clean install compile

java -cp target/leak-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar  Launcher -keep-line-number -f J -w -pp -cp target/classes/ examples.Main
