import java.io.File;
import java.util.Arrays;

public class VarLenRWayTree {
    static final String alphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String radix = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    static final File basePath = new File("C:\\Users\\Andrew\\Desktop\\testing");

    /**
     * creates R-way-like structure of directories, with first layer consisting of <code>alphas</code> and
     * the following - <code>radix</code>
     * @param depth - number of layers in the tree
     */
    static void build(int depth) {
        StringBuilder pathTemplateBuilder = new StringBuilder(3 * depth);
        for (int i = 0; i < depth; i++)
            pathTemplateBuilder.append("/%c");
        String pathTemplate = pathTemplateBuilder.toString();
        pathTemplateBuilder.setLength(0);
        final Character[] pathArgs = new Character[depth]; // hint to compiler to optimise 'length - 1'
        final char radixMin = radix.charAt(0);
        final char radixMax = radix.charAt(radix.length() - 1);
        Arrays.fill(pathArgs, radixMin);
        int[] pathArgsIndices = new int[depth];

        for (int i = 0, currInd = depth - 1; i < alphas.length(); i++, currInd = depth - 1) {
            pathArgs[0] = alphas.charAt(i);
            new File(basePath, String.format(pathTemplate, (Object[]) pathArgs)).mkdirs();

            while (currInd > 0) {
                if (pathArgsIndices[currInd] == radix.length() - 1) {
                    pathArgsIndices[currInd] = 0;
                    pathArgs[currInd] = radixMin;

                    currInd--;
                    continue;
                }
                pathArgs[currInd] = radix.charAt(++pathArgsIndices[currInd]);
                currInd = pathArgs.length - 1;

                new File(basePath, String.format(pathTemplate, (Object[]) pathArgs)).mkdirs();
            }
        }
    }
}
