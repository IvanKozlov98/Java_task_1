import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.lang.reflect.Array;

public class FileArchiever {

    public void compressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        //
        try(
        BufferedInputStream bfs = new BufferedInputStream(istream);
        BufferedOutputStream bws = new BufferedOutputStream(ostream))
        {
            //init
            int currentByte = -1,prevByte;
            //if file is empty
            if((prevByte = bfs.read()) == -1)
                return;
            int countOfByte = 1;
            //
            while((currentByte = bfs.read()) != -1)
            {
                if(currentByte == prevByte)
                    countOfByte++;
                else//write this
                {
                    while(countOfByte - 9 > 0) {
                        bws.write(prevByte);
                        bws.write(9);
                        countOfByte-=9;
                    }
                    bws.write(prevByte);
                    bws.write(countOfByte);

                    //update values
                    countOfByte = 1;
                }
                prevByte = currentByte;
            }
            //at the end to write
            while(countOfByte - 9 > 0) {
                bws.write(prevByte);
                bws.write(9);
                countOfByte-=9;
            }
            bws.write(prevByte);
            bws.write(countOfByte);
        }
    }
}
