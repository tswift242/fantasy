JCC = javac
SRCDIR = football
BUILDDIR = build
CLASSDIR = $(BUILDDIR)/classes
JFLAGS = -g -Xlint -d $(CLASSDIR) #-cp $(CLASSDIR)
SRCS = $(shell find $(SRCDIR) -type f -name '*.java')
#SRCS = $(wildcard $(SRCDIR)/*.java)
#SRCS += $(wildcard $(SRCDIR)/**/*.java)
CLASSES = $(SRCS:.java=.class)

all: $(CLASSES)

$(CLASSES): $(SRCS)
	$(JCC) $(JFLAGS) $(SRCS)

clean:
	#$(shell shopt -s globstar)
	rm -f $(CLASSDIR)/**/*.class
	#$(shell shopt -u globstar)
