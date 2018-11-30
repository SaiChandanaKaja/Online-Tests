import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class MaximumDistance {
    static int n, maxSides;
    static int[] len;
    static double[] x0, y0, x1, y1;
    static double maxDist,
            eps = -1E-3;
    public static void main(String[] args) throws Exception {
        if (args.length > 0) DEBUG = Integer.parseInt(args[0]);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        dprln(line, 1);
        Scanner in = new Scanner(line);
        n = in.nextInt();
        len = new int[n];
        for (int i = 0; i < n; i++)
            len[i] = in.nextInt();
        x0 = new double[n];
        y0 = new double[n];
        x1 = new double[n];
        y1 = new double[n];
        maxDist = 0;
        maxSides = n - (n+1)%2;
        double maxDistence = mix(0);
        if(maxDistence % 1 == 0){
            System.out.println((int) maxDistence);
        } else {
            System.out.printf("%.7f", maxDistence);
        }
        if(DEBUG == 1) System.err.println(bestData);
    }

    static void swap(int[] perm, int i, int j)
    {
        int temp = perm[i];
        perm[i] = perm[j];
        perm[j] = temp;
    }

    static double mix(int iPerm)
    {
        if (iPerm == maxSides) {
            debugData(iPerm);
            return maxDist;
        }
        for (int j = iPerm; j < n; j++) {
            swap(len, iPerm, j);
            if (iPerm%2 == 1)
                mix(iPerm+1);
            else if (iPerm == 0 ) {
                x1[0] = len[0];
                mix(1);
            }
            else {
                build(iPerm-1, iPerm-2);
                if (iPerm > 3)
                    build(iPerm-1, iPerm-3);
            }
            swap(len, iPerm, j);
        }
        return maxDist;
    }

    static void build(int i, int e)
    {
        double D = len[e];
        double r1 = len[i], r2 = len[i+1];
        double dx = x1[e] - x0[e], dy = y1[e] - y0[e];
        double E = (r1*r1 - r2*r2 + D*D)/(2*D);
        double inRadical = r1*r1 - E*E;
        if (inRadical <= 0) {
            debugData(i);
            return;
        }
        double F = sqrt(inRadical);

        for (int k = 0; k < 2; k++) {
            y1[i] = y0[i+1] = y0[e] + (F*dx + E*dy)/D;
            if (y1[i] <= eps)
                debugData(i);
            else {
                if (y1[i] > maxDist)
                    maxDist = y1[i];
                x1[i] = x0[i+1] = x0[e] + (E*dx - F*dy)/D;
                y0[i] = y0[e]; x0[i] = x0[e];
                x1[i+1] = x1[e]; y1[i+1] = y1[e];
                mix(i+2);
            }
            F = -F;
        }
    }

    static int DEBUG = 0;
    static double debugMaxDist = 0;
    static String bestData = "";

    static String segSeq(int nV)
    {
        String s = "" + len[0];
        for (int i = 1; i < nV; i += 2)
            s += "\n     " + x0[i] + " " + y0[i] + " " + x1[i] + " " + y1[i] +
                    " " + x1[i+1] + " " + y1[i+1];
        return s;
    }

    static void debugData(int nV)
    {
        if (DEBUG == 0) return;
        double thisTop = 0, thisMin = 200;
        for (int i = 1; i < nV; i += 2) {
            thisTop = max(thisTop, y1[i]);
        }
        if (thisTop > debugMaxDist) {
            debugMaxDist = thisTop;
            bestData = "Max: " + thisTop + " " + segSeq(nV);
            dprln(bestData, 2);
        }
    }

    static void dprln(String s, int d) {
        if (DEBUG >= d) System.err.println(s);
    }
}
