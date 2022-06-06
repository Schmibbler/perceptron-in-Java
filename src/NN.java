
public class NN {
    public Layer input_layer;
    public int layer_count;
    public double lr;

    public NN () {
        this.layer_count = 0;
        this.input_layer = null;
        this.lr = 0.01;
    }

    public void display_architecture() {
        if (this.layer_count == 0)
            return;
        Layer temp = this.input_layer;
        while (temp != null) {
            System.out.printf("%d -> ", temp.node_count);
            temp = temp.next;
        }
        System.out.println();
    }

    public Matrix feed_forward(Matrix input) {
        Layer temp = input_layer;
        Matrix output = new Matrix();
        while (temp.next != null) {
            output = temp.forward_prop(input);
            input = output;
            temp = temp.next;
        }
        return output;
    }

    public void fit (Matrix [] x, Matrix [] y, int epochs) {
        double err = 0;
        Matrix output;
        Matrix error;
        for (int i = 0; i < epochs; i++) {
            for (int j = 0; j < x.length; j++) {
                output = feed_forward(x[j]);
                err += mse(y[j], output);
                error = loss_prime(output, y[j]);
                back_propagate(error);
            }
            err /= x.length;
            System.out.printf("<-> Epoch: %d, Error: %f <->\n", i + 1, err);
            err = 0;
        }
    }

    public Matrix back_propagate(Matrix output_error) {
        Layer current_layer = input_layer;
        while (current_layer.next != null)
            current_layer = current_layer.next;
        Matrix input;
        while (current_layer.prev != null) {
            input = current_layer.back_prop(output_error);
            output_error = input;
            current_layer = current_layer.prev;
        }
        return output_error;
    }
    double mse(Matrix y_true, Matrix y_pred) {
        double mse = 0;
        for (int i = 0; i < y_pred.rows; i++)
            for (int j = 0; j < y_pred.cols; j++)
                mse += Math.pow(y_true.array[i][j] - y_pred.array[i][j], 2);
        return mse;
    }
    public Matrix loss_prime(Matrix y_true, Matrix y_pred) {
        Matrix loss = new Matrix(y_true.rows, y_true.cols);
        for (int i = 0; i < loss.rows; i++)
            for (int j = 0; j < loss.cols; j++)
                loss.array[i][j] = mse_prime(y_true.array[i][j], y_pred.array[i][j]);
        return loss;
    }

    public double mse_prime(double y_pred, double y_true) {
        return 2 * (y_pred - y_true);
    }
    public void add_layer(int node_count) {
        if (layer_count == 0) {
            input_layer = new Layer(node_count);
            this.layer_count++;
            return;
        }
        Layer temp = input_layer;
        while (temp.next != null)
            temp = temp.next;
        temp.next = new Layer(node_count);
        temp.connect_layers(temp.next);
    }

}
