import java.util.Random;

public class Data extends Matrix{

    public static Matrix[] x_set(int num_samples, int sample_length) {
        Matrix x[] = new Matrix[num_samples];
        for (int i = 0; i < num_samples; i++)
            x[i] = x_gen(sample_length);
        return x;
    }
    public static Matrix[] y_set (Matrix [] x_set) {
        int num_samples = x_set.length;
        Matrix y[] = new Matrix[num_samples];
        for (int i = 0; i < num_samples; i++)
            y[i] = y_gen(x_set[i]);
        return y;
    }

    public static Matrix x_gen(int len) {
        Random rand = new Random();
        Matrix x = new Matrix(1, len);
        for (int i = 0; i < len; i++) {
            int num = rand.nextInt() % 2;
            x.array[0][i] = num;
        }
        return x;
    }

    public static Matrix y_gen(Matrix x) {
        Matrix y = new Matrix (x.rows, x.cols);
        for (int i = 0; i < x.cols; i++) {
            double test = x.array[0][i];
            if (test == 0) y.array[0][i] = 1;
            else y.array[0][i] = 0;
        }
        return y;
    }

}
