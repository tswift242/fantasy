JAVAC = javac
CCFLAGS = -Xlint
SRCS = $(wildcard *.java)
CLASSES = $(SRCS:.java=.class)

all: $(CLASSES)

$(CLASSES): $(SRCS)
	$(JAVAC) $(CCFLAGS) $(SRCS)

clean:
	rm -f *.class
