package iloveyouboss;


enum Bool {
    False(0),
    True(1);

    private int value;

    Bool(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
