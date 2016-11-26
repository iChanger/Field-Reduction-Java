
import java.io.*;
import java.util.*;
class reduce {
    public static void main (String [] args) throws IOException {
        //Short Plan: We take the 3 highest and the 3 lowest points for both x and y, and we brute-force removing these points and checking the area. This makes sense, because removing a point that isn't the highest in any sort wouldn't affect the area, it would be inside the rectangle at all times. We brute-force by creating combinations yet ignoring the 4th lowest/highest coordinates so we can compute the area using those coordinates.
        BufferedReader f = new BufferedReader(new FileReader("reduce.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("reduce.out")));
        int loop = Integer.parseInt(f.readLine());
        long area = Long.MAX_VALUE;
        ArrayList<Point> coordinates = new ArrayList<Point>();
        ArrayList<Point> test = new ArrayList<Point>();
        
        for(int i = 0; i < loop; i++){ //Add all of the points into a custom Point class
            StringTokenizer st = new StringTokenizer(f.readLine());
            coordinates.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        coordinates = sortByCoordinate(coordinates, 'x');
        test = add3HighestLowest(test, coordinates, loop);
        test.add(0, (coordinates.get(3))); //We add these to provide FOUR coordinates, in case if we remove the first three coordinates. We plan to IGNORE the first four cases when we create the combinations, as they are reserved for the fourth lowest/highets coordinates which we do NOT touch
        test.add(1, (coordinates.get(loop - 1 - 3)));
        
        coordinates = sortByCoordinate(coordinates, 'y');
        test = add3HighestLowest(test, coordinates, loop);
        test.add(2, (coordinates.get(3)));
        test.add(3, (coordinates.get(loop - 1 - 3)));
        
        for(int i = 4; i < test.size(); i++){ //Generate all possible combinations of the three points to remove
            //We start at 4, because we do not wish to create combinations for the fourth lowest/highest points, only the third lowest/highest points. We reserved the first four indexes for these points
            for(int j = i + 1; j < test.size(); j++){
                for(int k = j + 1; k < test.size(); k++)
                    area = Math.min(area, getArea(test, i, j, k));
            }
        }
        out.println(area);
        out.close();                                  // close the output file
    }
    public static ArrayList<Point> sortByCoordinate(ArrayList<Point> coordinates, char dimension){
        //We sort the coordinates according to x-values or y-values
        if(dimension == 'x'){
            Collections.sort(coordinates,new Comparator<Point>() {
                
                public int compare(Point o1, Point o2) {
                    return Integer.compare(o1.getX(), o2.getX()); //Sorts by x-coordinates
                }
            });
        }
        else if(dimension == 'y'){
            Collections.sort(coordinates,new Comparator<Point>() {
                
                public int compare(Point o1, Point o2) {
                    return Integer.compare(o1.getY(), o2.getY()); //Sorts by y-coordinates
                }
            });
        }
        return coordinates;
    }
    public static ArrayList<Point> add3HighestLowest(ArrayList<Point> test, ArrayList<Point> coordinates, int loop){
        //Adds the points with the highest and the lowest 3 coordinates - MUST have a sorted coordinates array first according to x or y
        for(int i = 0; i < 6; i++){
            if(i > 2 && !test.contains(coordinates.get(loop + (i - 5) - 1)))
                test.add(coordinates.get(loop + (i - 5) - 1)); //Gets the three coordinates with the largest x-coordinate and puts them last
            else if( i <= 2 && !test.contains(coordinates.get(i)))
                test.add(coordinates.get(i)); //Gets the three coordinates with the smallest x-coordinates
        }
        return test;
    }
    public static long getArea(ArrayList<Point> test, int i, int j, int k){
        long area = Long.MAX_VALUE;
        long minx = Long.MAX_VALUE; 
        long maxx = Long.MIN_VALUE;
        long miny = Long.MAX_VALUE;
        long maxy = Long.MIN_VALUE;
        for(int inc = 0; inc < test.size(); inc++){
            if(inc == i || inc == j || inc == k)
                continue;
            minx = Math.min(minx, (long)test.get(inc).getX());
            maxx = Math.max(maxx, (long)test.get(inc).getX());
            miny = Math.min(miny, (long)test.get(inc).getY());
            maxy = Math.max(maxy, (long)test.get(inc).getY());
        }
        area = (maxx - minx) * (maxy - miny);
        return area;
    }
    static class Point{ //Custom class to allow us to store coordinates and sort it by the x or y coordinate
        private int x;
        private int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void setCoord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }
}
