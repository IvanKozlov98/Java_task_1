import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.lang.reflect.Array;

public class FileArchiever {

    public void compressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        try(
        BufferedInputStream bfs = new BufferedInputStream(istream);
        BufferedOutputStream bws = new BufferedOutputStream(ostream))
        {

            //init
            int currentByte = -1,prevByte = bfs.read();
            int countOfByte = 1;
            int[] numbers = new int[1024];
            int lenNumbers = 0;
            //
            while((currentByte = bfs.read()) != -1)
            {
                if(currentByte == prevByte)
                    countOfByte++;
                else//write this
                {
                    bws.write(prevByte);
                    while(countOfByte != 0) {
                        numbers[lenNumbers] = countOfByte % 10;//in this case i can do it(0..9)
                        lenNumbers++;
                        countOfByte/=10;
                    }
                    for(int i = lenNumbers - 1;i >= 0;--i)
                        bws.write(numbers[i]);
                    //update
                    countOfByte = 1;
                    lenNumbers = 0;
                }
                prevByte = currentByte;
            }
            //at the end to write
            bws.write(prevByte);
            while(countOfByte != 0) {
                numbers[lenNumbers] = countOfByte % 10;//in this case i can do it(0..9)
                lenNumbers++;
                countOfByte/=10;
            }
            for(int i = lenNumbers - 1;i >= 0;--i)
                bws.write(numbers[i]);
        }
    }
}
