package library;

public class Point3D {
    public double x;
    public double y;
    public double z;

    public Point3D(Point3D other) {
        setCoords(other);
    }

    public Point3D(double x, double y, double z) {
        setCoords(x, y, z);
    }

    public void setCoords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCoords(Point3D other) {
        setCoords(other.x, other.y, other.z);
    }

    public void setZero() {
        x = 0;
        y = 0;
        z = 0;
    }


    public double dotProduct(Point3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double sqrLength() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void add(Point3D other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public void add(Point3D p1, Point3D p2) {
        x = p1.x + p2.x;
        y = p1.y + p2.y;
        z = p1.z + p2.z;
    }

    public void sub(Point3D other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public void sub(Point3D p1, Point3D p2) {
        x = p1.x - p2.x;
        y = p1.y - p2.y;
        z = p1.z - p2.z;
    }

    public void scale(double f, Point3D other) {
        x = f * other.x;
        y = f * other.y;
        z = f * other.z;
    }

    public void mul(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
    }

    public Point3D cross(Point3D o) {
        return new Point3D(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x);
    }

    public static Point3D getPointHeightOnPlane(Point3D plane, Point3D planeOrigin, Point3D p1) {
        Point3D res = new Point3D(p1);
        res.x -= planeOrigin.x;
        res.y -= planeOrigin.y;
        res.z = -1 * (plane.x * res.x + plane.y * res.y);
        res.z /= plane.z;
        res.z += planeOrigin.z;
        res.x = p1.x;
        res.y = p1.y;
        return res;
    }

    public static boolean almostEqual(Point3D p1, Point3D p2, double delta) {
        Point3D p3 = new Point3D(p1);
        p3.sub(p2);
        p3.x = Math.abs(p3.x);
        p3.y = Math.abs(p3.y);
        p3.z = Math.abs(p3.z);
        return p3.x < delta && p3.y < delta && p3.z < delta;
    }
}