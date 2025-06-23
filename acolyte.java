import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class acolyte {

    public static final String JAR_NAME = "Acolyte-1.0-SNAPSHOT.jar";

    public static void main(String[] args) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");

        List<String> command = new ArrayList<>();
        if (isWindows) {
            command.add("cmd.exe");
            command.add("/c");
        } else {
            command.add("sh");
            command.add("-c");
        }

        StringBuilder fullCommand = new StringBuilder("java -jar " + JAR_NAME);
        if (args.length > 0) {
            fullCommand.append(" ").append(String.join(" ", args));
        }

        command.add(fullCommand.toString());

        ProcessBuilder builder = new ProcessBuilder(command);

        builder.directory(new File(System.getProperty("user.dir")));
        builder.redirectErrorStream(true);

        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
    }
}
