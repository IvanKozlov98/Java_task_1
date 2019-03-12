import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.lang.reflect.Array;

public class FileArchiever {

    private static final int BASE_NUMBER_SYSTEM = 255;

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
            long countOfByte = 1;
            //
            while((currentByte = bfs.read()) != -1)
            {
                if(currentByte == prevByte)
                    countOfByte++;
                else//write this
                {
                    //числа будем кодировать в 255-ой системе счисления => записывать коэффициенты при соответствующих степенях
                    //т.е если number = a*255^0 + b*255^1 + c*255^2 .. -> закодируем это в виде abc...255
                    //пишем в конце записи числа - 255
                    bws.write(prevByte);
                    do{
                        bws.write((int)(countOfByte % BASE_NUMBER_SYSTEM));
                        countOfByte/=BASE_NUMBER_SYSTEM;
                    }while(countOfByte != 0);
                    //to add mark
                    bws.write(BASE_NUMBER_SYSTEM);
                    //update values
                    countOfByte = 1;
                }
                prevByte = currentByte;
            }
            //at the end to write
            bws.write(prevByte);
            do{
                bws.write((int)(countOfByte % BASE_NUMBER_SYSTEM));
                countOfByte/=BASE_NUMBER_SYSTEM;
            }while(countOfByte != 0);
            //to add mark
            bws.write(BASE_NUMBER_SYSTEM);
        }
    }
}
