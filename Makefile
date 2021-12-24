#> javac @sources.txt
DESTINATION=build
SRC=sources.txt
CC=javac

compile:
	find -name "*.java" > sources.txt
	$(CC) -d $(DESTINATION) @$(SRC)

run:
	java --class-path $(DESTINATION):. main.OperatingSystem

clean:
	rm -rf $(DESTINATION)/*
