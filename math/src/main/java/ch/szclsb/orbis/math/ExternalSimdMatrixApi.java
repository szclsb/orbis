package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdMatrix;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class ExternalSimdMatrixApi<T extends FMatrix> implements IFMatrixApi<T> {
    private final VectorSpecies<Float> species;
    private final int lanes, th, size;

    public ExternalSimdMatrixApi(Class<T> tClass) {
        this(tClass, FloatVector.SPECIES_PREFERRED);
    }

    public ExternalSimdMatrixApi(Class<T> tClass, VectorSpecies<Float> species) {
        var rows = tClass.getAnnotation(SimdMatrix.class).rows();
        var columns = tClass.getAnnotation(SimdMatrix.class).columns();
        this.species = species;
        this.lanes = species.length();
        this.size = rows * columns;
        this.th = (size / lanes) * lanes;
    }

    @Override
    public void add(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.add(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] + b.data[i];
        }
    }

    @Override
    public void add(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.add(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] + s;
        }
    }

    @Override
    public void sub(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.sub(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] - b.data[i];
        }
    }

    @Override
    public void sub(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.sub(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] - s;
        }
    }

    @Override
    public <B extends FMatrix, R extends FMatrix> void mul(T a, B b, R r) {
        var rowSize = a.getRows();
        var columnSize = b.getColumns();
        var depthSize = a.getColumns();

        var columnVecs = columnSize / lanes;

        if (r.getRows() != rowSize || r.getColumns() != columnSize || b.getRows() != depthSize) {
            throw new UnsupportedOperationException("Invalid matrix dimensions");
        }

        // pre allocate b rows
        var bColumns = new FloatVector[columnVecs][depthSize];
        for (var v = 0; v < columnVecs; v += 1) {
            for (var k = 0; k < depthSize; k += 1) {
                bColumns[v][k] = FloatVector.fromArray(species, b.data, k * columnSize + v * lanes);
            }
        }

        // calculate result
        for (var u = 0; u < rowSize; u++) {
            var ac = u * depthSize;
            var rc = u * columnSize;
            var i = 0;
            for (var v = 0; v < columnVecs; v += 1) {
                var vc = FloatVector.zero(species);
                var bColumn = bColumns[v];
                for (var k = 0; k < depthSize; k += 1) {
                    vc = vc.add(bColumn[k].mul(a.data[ac + k]));
                }
                vc.intoArray(r.data, rc + i);
                i += lanes;
            }
            for (; i < columnSize; i += 1) {
                var sum = 0;
                for (var k = 0; k < depthSize; k += 1) {
                    sum += a.data[ac + k] * b.data[k * columnSize + i];
                }
                r.data[rc + i] = sum;
            }
        }
    }

//    public void mul(FMatrix4x4 a, FMatrix4x4 b, FMatrix4x4 r) {
//        for (var u = 0; u < r.)
//
//        var A1 = FloatVector.fromArray(species, a.data, 0);
//        var A2 = FloatVector.fromArray(species, a.data, 4);
//        var A3 = FloatVector.fromArray(species, a.data, 8);
//        var A4 = FloatVector.fromArray(species, a.data, 12);
//
//        var B1 = FloatVector.fromArray(species, b.data, 0);
//        var B2 = FloatVector.fromArray(species, b.data, 4);
//        var B3 = FloatVector.fromArray(species, b.data, 8);
//        var B4 = FloatVector.fromArray(species, b.data, 12);
//
//        var C1 = B1.mul(a.data[0])
//                .add(B2.mul(a.data[1]))
//                .add(B3.mul(a.data[2]))
//                .add(B4.mul(a.data[3]));
//        var C2 = B1.mul(a.data[4])
//                .add(B2.mul((a.data[5]))
//                .add(B3.mul((a.data[6]))
//                .add(B4.mul((a.data[7]));
//        var C3 = B1.mul(a.data[8])
//                .add(B2.mul(a.data[9]))
//                .add(B3.mul(a.data[10]))
//                .add(B4.mul(a.data[11]));
//        var C4 = B1.mul(a.data[12])
//                .add(B2.mul(a.data[13]))
//                .add(B3.mul(a.data[14]))
//                .add(B4.mul(a.data[15]));
//
//        C1.intoArray(r.data, 0);
//        C2.intoArray(r.data, 4);
//        C3.intoArray(r.data, 8);
//        C4.intoArray(r.data, 12);
//    }

    @Override
    public void mul(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.mul(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] * s;
        }
    }

    @Override
    public void _mul(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.mul(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] - b.data[i];
        }
    }

    @Override
    public boolean equals(T a, T b) {
        var result = true;
        var i = 0;
        for (; result && i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            result = va.sub(vb).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        for (; result && i < size; i += 1) {
            result = MathUtils.isFloatEquals(a.data[i], b.data[i]);
        }
        return result;
    }
}
