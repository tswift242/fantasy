JCC = javac
JAVA = java
JCCFLAGS = -g -Xlint
SRCDIR = football
BUILDDIR = build
CLASSDIR = $(BUILDDIR)/classes
RESULTSDIR = results
SRCS = $(shell find $(SRCDIR) -type f -name '*.java')
#SRCS = $(wildcard $(SRCDIR)/*.java)
#SRCS += $(wildcard $(SRCDIR)/**/*.java)
CLASSES = $(SRCS:.java=.class)
MKDIR = -mkdir -p

.PHONY: all directories run clean

all: directories $(CLASSES)

directories: $(CLASSDIR) $(RESULTSDIR)

# Create compiled source directories
$(CLASSDIR): $(BUILDDIR)
	$(MKDIR) $(CLASSDIR)

$(BUILDDIR):
	$(MKDIR) $(BUILDDIR)

$(RESULTSDIR):
	$(MKDIR) $(RESULTSDIR)

# Compile java files into class files
%.class: %.java
	$(JCC) -d $(CLASSDIR) $(JCCFLAGS) $<

# mode and coeffs passed in through cmd line
run:
	$(JAVA) -cp $(CLASSDIR) $(SRCDIR)/ff $(mode) $(coeffs)

clean:
	@$(RM) $(shell find $(CLASSDIR) -type f -name '*.class')
