package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandInvoker {
    private final List<Command> history = new ArrayList<>();

    public void execute(Command command) {
        if (command == null) {
            return;
        }
        command.execute();
        history.add(command);
    }

    public List<Command> getHistory() {
        return Collections.unmodifiableList(history);
    }
}
