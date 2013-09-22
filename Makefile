JCC = javac
JFLAGS = -g -Xlint -d $(CLASSDIR)
SRCDIR = football
BUILDDIR = build
CLASSDIR = $(BUILDDIR)/classes
SRCS = $(shell find $(SRCDIR) -type f -name '*.java')
#SRCS = $(wildcard $(SRCDIR)/*.java)
#SRCS += $(wildcard $(SRCDIR)/**/*.java)
CLASSES = $(SRCS:.java=.class)
MKDIR = -mkdir -p

.PHONY: all directories clean

all: directories $(CLASSES)

directories: $(CLASSDIR)

# Create compiled source directories
$(CLASSDIR): $(BUILDDIR)
	$(MKDIR) $(CLASSDIR)

$(BUILDDIR):
	$(MKDIR) $(BUILDDIR)

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@$(RM) $(shell find $(CLASSDIR) -type f -name '*.class')
