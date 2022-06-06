public interface Except {
    void InvalidDimensions(String reason);
    void MismatchingDimensions(String reason);
    void NotElementWiseable(String reason);
}
