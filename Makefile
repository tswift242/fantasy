JCC = javac
JFLAGS = -g -Xlint
#SRCS = $(shell find . -type f -name '*.java')
SRCS = $(wildcard *.java)
SRCS += $(wildcard **/*.java)
CLASSES = $(SRCS:.java=.class)
CLASSDIR = classes

#vpath %.java players

all: $(CLASSES)

$(CLASSES): $(SRCS)
	@echo $(SRCS)
	#@echo $(CLASSES)
	#$(JCC) $(JFLAGS) $(SRCS)

clean:
	rm -f *.class
