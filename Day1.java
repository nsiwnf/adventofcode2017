public class Day1 {

    public static int part1(String input) {
        byte[] bytes = input.getBytes();
        byte first = bytes[0];
        byte prev = first;
        int result = 0;
        for (int i = 1; i < bytes.length; i++) {
            byte c = bytes[i];
            if(prev == c)
                result += c - '0';
            prev = c;
        }

        if(first == bytes[bytes.length-1]) {
            result += (first-'0');
        }

        return result;
    }

    public static int part2(String input) {
        byte[] bytes = input.getBytes();
        int result = 0;
        int half = bytes.length/2;
        for (int i = 0; i < half; i++) {
            byte c = bytes[i];
            byte next = bytes[i+half];
            if(next == c)
                result += c - '0';
        }

        return result*2;
    }

}
