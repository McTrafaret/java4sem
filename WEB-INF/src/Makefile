JC = javac
JFLAGS =

CLASSES_DIR = ../classes/
ENTITIES = $(addprefix $(CLASSES_DIR),$(patsubst %.java,%.class,$(wildcard entities/*.java)))
DAO = $(addprefix $(CLASSES_DIR),$(patsubst %.java,%.class,$(wildcard dao/*.java)))
SESSION = $(addprefix $(CLASSES_DIR),$(patsubst %.java,%.class,$(wildcard session/*.java)))
WEBAPP = $(addprefix $(CLASSES_DIR),$(patsubst %.java,%.class,$(wildcard webapp/*.java)))
LIBS = /usr/share/tomcat9/lib/*


$(CLASSES_DIR)%.class : %.java
	$(JC) $(JFLAGS) -d $(CLASSES_DIR) $< -cp "$(CLASSES_DIR):$(LIBS)"

all : $(ENTITIES) ../classes/session/ConnectionManager.class $(DAO) $(SESSION) $(WEBAPP)
