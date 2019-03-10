import java.io.*;

public class FileArchiever {

    public void compressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        //
        try(
        BufferedInputStream bfs = new BufferedInputStream(istream);
        BufferedOutputStream bws = new BufferedOutputStream(ostream))
        {
            //init
            int currentByte = -1,prevByte = bfs.read();
            int countOfByte = 1;
            //
            while((currentByte = bfs.read()) != -1)
            {
                if(currentByte == prevByte)
                    countOfByte++;
                else//write this
                {
                    bws.write(prevByte);
                    while(countOfByte != 0) {
                        bws.write(countOfByte % 10);
                        countOfByte/=10;
                    }
                    //update
                    countOfByte = 1;
                }
                prevByte = currentByte;
            }
            //at the end to write
            bws.write(prevByte);
            while(countOfByte != 0) {
                bws.write(countOfByte % 10);
                countOfByte/=10;
            }
        }
    }
}
