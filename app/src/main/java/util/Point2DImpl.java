package util;

import org.apache.commons.lang3.tuple.Pair;

public class Point2DImpl<X, Y> extends Pair<X, Y> implements Point2D<X, Y> {

    /**
     * 
     */
    private static final long serialVersionUID = -8856165215379951494L;
    private X left;
    private Y right;

    public Point2DImpl(final X left, final Y right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Y setValue(final Y value) {
        this.right = value;
        return this.right;
    }

    @Override
    public X getLeft() {
        return this.left;
    }

    @Override
    public Y getRight() {
        return this.right;
    }
}
