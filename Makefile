all: test

test:
	javac Driver.java LinkedSet.java Set.java SetFullException.java
	java -enableassertions Driver

test-soft:
	javac Driver.java LinkedSet.java Set.java SetFullException.java
	java Driver soft

clean:
	rm -f *.class

