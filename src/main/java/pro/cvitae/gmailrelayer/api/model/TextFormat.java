package pro.cvitae.gmailrelayer.api.model;

public enum TextFormat {
    TEXT((short) 1), HTML((short) 2);

    Short type;

    private TextFormat(final Short type) {
        this.type = type;
    }

    public Short value() {
        return this.type;
    }

}
