public class Layer extends Matrix {

    public Matrix biases;
    public Matrix weights;
    public int node_count;
    public Layer prev;
    public Layer next;
    public Matrix input;
    public Matrix output;

    public Layer(int nodes) {
        this.node_count = nodes;
        this.biases = new Matrix(nodes, 1);
    }
    public Layer() {
        this.node_count = 0;
        this.biases = null;
    }


    public void connect_layers(Layer next) {
        if (this.node_count == 0) return;
        if (next.node_count == 0) return;

        this.weights = new Matrix(this.node_count, next.node_count);
        this.weights.randomize();
        this.biases.randomize();
        this.next = next;
    }

    public Matrix forward_prop(Matrix input) {
        this.input = input;
        Matrix output = input.dot(this.weights);
        plus_biases(output, this.biases);
        relu(output);
        this.output = output;
        return this.output;
    }

    public Matrix back_prop(Matrix output_error) {

        Matrix input_error = output_error.dot(this.weights.T);
        input = new Matrix(this.prev.input.T);
        Matrix weights_error = input.dot(output_error);
        weights_error.multiply_by(.001);
        output_error.multiply_by(.001);
        this.weights.subtract(weights_error);
        this.biases.subtract(output_error);

        Matrix relu_output_error = input_error;
        Matrix relu_input = this.prev.input;
        relu_prime(relu_input);
        relu_input.multiply(relu_output_error);
        output_error = relu_input;
        return output_error;

    }
    public void relu(Matrix m) {
        for (int i = 0; i < m.rows; i++)
            for (int j = 0; j < m.cols; j++)
                if (m.array[i][j] <= 0)
                    m.array[i][j] = 0;
    }
    public void relu_prime(Matrix m) {
        for (int i = 0; i < m.rows; i++)
            for (int j = 0; j < m.cols; j++) {
                double num = m.array[i][j];
                if (num <= 0)
                    m.array[i][j] = 0;
                else
                    m.array[i][j] = 1;
            }

    }
    public void plus_biases(Matrix m, Matrix biases) {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                m.array[i][j] += biases.array[j][0];
    }



}
