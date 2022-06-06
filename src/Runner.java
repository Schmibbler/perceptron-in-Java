import java.util.Random;
public class Runner extends Matrix {

    public static void main (String [] args) {
        Random rand = new Random();
        NN nn = new NN();
        nn.add_layer(10);
        nn.add_layer(50);
        nn.add_layer(50);
        nn.add_layer(10);
        nn.display_architecture();
        Matrix input = new Matrix(1,10);
        input.randomize();

        Matrix x[] = Data.x_set(10, 10);
        Matrix y[] = Data.y_set(x);
        nn.fit(x, y, 300);

        Matrix output = nn.feed_forward(x[0]);
        System.out.println("X datapoint");
        x[0].display();
        System.out.println("Prediciton:");
        output.display();
    }

}

