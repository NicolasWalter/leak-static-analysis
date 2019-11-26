
import java.util.Map;

import analysis.Analysis;
import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Transform;

public class Launcher {
    public static void main(String[] args) {
        PackManager.v().getPack("jtp").add(new Transform("jtp.Analysis", new BodyTransformer() {
            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                Analysis analysis = Analysis.newWithBody(body);
            }
        }));
        soot.Main.main(args);
    }
}
